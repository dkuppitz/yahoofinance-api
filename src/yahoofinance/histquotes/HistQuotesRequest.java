package yahoofinance.histquotes;

import com.csvreader.CsvReader;
import yahoofinance.Utils;
import yahoofinance.YahooFinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author Stijn Strickx
 */
public class HistQuotesRequest {

    private final String symbol;

    private final Calendar from;
    private final Calendar to;

    private final Interval interval;

    public static final Calendar DEFAULT_FROM = Calendar.getInstance();

    static {
        DEFAULT_FROM.add(Calendar.YEAR, -1);
    }

    public static final Calendar DEFAULT_TO = Calendar.getInstance();
    public static final Interval DEFAULT_INTERVAL = Interval.MONTHLY;

    public HistQuotesRequest(String symbol) {
        this(symbol, DEFAULT_INTERVAL);
    }

    public HistQuotesRequest(String symbol, Interval interval) {
        this.symbol = symbol;
        this.interval = interval;

        this.from = DEFAULT_FROM;
        this.to = DEFAULT_TO;
    }

    public HistQuotesRequest(String symbol, Calendar from, Calendar to) {
        this(symbol, from, to, DEFAULT_INTERVAL);
    }

    public HistQuotesRequest(String symbol, Calendar from, Calendar to, Interval interval) {
        this.symbol = symbol;
        this.from = from;
        this.to = to;
        this.interval = interval;
    }

    public HistQuotesRequest(String symbol, Date from, Date to) {
        this(symbol, from, to, DEFAULT_INTERVAL);
    }

    public HistQuotesRequest(String symbol, Date from, Date to, Interval interval) {
        this(symbol, interval);
        this.from.setTime(from);
        this.to.setTime(to);
    }

    public List<HistoricalQuote> getResult() throws IOException {

        List<HistoricalQuote> result = new ArrayList<HistoricalQuote>();

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("s", this.symbol);

        params.put("a", String.valueOf(this.from.get(Calendar.MONTH)));
        params.put("b", String.valueOf(this.from.get(Calendar.DAY_OF_MONTH)));
        params.put("c", String.valueOf(this.from.get(Calendar.YEAR)));

        params.put("d", String.valueOf(this.to.get(Calendar.MONTH)));
        params.put("e", String.valueOf(this.to.get(Calendar.DAY_OF_MONTH)));
        params.put("f", String.valueOf(this.to.get(Calendar.YEAR)));

        params.put("g", this.interval.getTag());

        params.put("ignore", ".csv");

        String url = YahooFinance.HISTQUOTES_BASE_URL + "?" + Utils.getURLParameters(params);

        // Get CSV from Yahoo
        YahooFinance.logger.log(Level.INFO, ("Sending request: " + url));

        URL request = new URL(url);
        URLConnection connection = request.openConnection();
        try (final InputStreamReader is = new InputStreamReader(connection.getInputStream())) {
            final BufferedReader br = new BufferedReader(is);
            final CsvReader reader = new CsvReader(br);
            if (reader.readHeaders()) {
                while (reader.readRecord()) {
                    final HistoricalQuote quote = new HistoricalQuote(this.symbol,
                            Utils.parseHistDate(reader.get(0)),
                            Utils.getBigDecimal(reader.get(1)),
                            Utils.getBigDecimal(reader.get(3)),
                            Utils.getBigDecimal(reader.get(2)),
                            Utils.getBigDecimal(reader.get(4)),
                            Utils.getBigDecimal(reader.get(6)),
                            Utils.getLong(reader.get(5)));
                    result.add(quote);
                }
            }
        }
        return result;
    }
}
