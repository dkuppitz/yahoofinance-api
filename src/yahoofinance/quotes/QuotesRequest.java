package yahoofinance.quotes;

import com.csvreader.CsvReader;
import yahoofinance.Utils;
import yahoofinance.YahooFinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @param <T> Type of object that can contain the retrieved information from a
 *            quotes request
 * @author Stijn Strickx
 */
public abstract class QuotesRequest<T> {

    protected final String query;
    protected List<QuotesProperty> properties;

    public QuotesRequest(String query, List<QuotesProperty> properties) {
        this.query = query;
        this.properties = properties;
    }

    public String getQuery() {
        return query;
    }

    public List<QuotesProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<QuotesProperty> properties) {
        this.properties = properties;
    }

    protected abstract T load(final String[] values);

    private String getFieldsString() {
        StringBuilder result = new StringBuilder();
        for (QuotesProperty property : this.properties) {
            result.append(property.getTag());
        }
        return result.toString();
    }

    public T getSingleResult() throws IOException {
        List<T> results = this.getResult();
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * Sends the request to Yahoo Finance and parses the result
     *
     * @return List of parsed objects resulting from the Yahoo Finance request
     * @throws java.io.IOException when there's a connection problem or the request is incorrect
     */
    public List<T> getResult() throws IOException {
        List<T> result = new ArrayList<T>();

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("s", this.query);
        params.put("f", this.getFieldsString());
        params.put("e", ".csv");

        String url = YahooFinance.QUOTES_BASE_URL + "?" + Utils.getURLParameters(params);

        // Get CSV from Yahoo
        YahooFinance.logger.log(Level.INFO, ("Sending request: " + url));

        URL request = new URL(url);
        URLConnection connection = request.openConnection();
        try (InputStreamReader is = new InputStreamReader(connection.getInputStream())) {
            final BufferedReader br = new BufferedReader(is);
            final CsvReader reader = new CsvReader(br);
            while (reader.readRecord()) {
                if ("Missing Symbols List.".equals(reader.get(0))) {
                    YahooFinance.logger.log(Level.SEVERE, "The requested symbol was not recognized by Yahoo Finance");
                    break;
                } else {
                    T data = this.load(reader.getValues());
                    result.add(data);
                }
            }
        }
        return result;
    }

}
