
package yahoofinance.quotes.fx;

import yahoofinance.Utils;
import yahoofinance.quotes.QuotesProperty;
import yahoofinance.quotes.QuotesRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stijn Strickx
 */
public class FxQuotesRequest extends QuotesRequest<FxQuote> {

    public static final List<QuotesProperty> DEFAULT_PROPERTIES = new ArrayList<QuotesProperty>();

    static {
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.LastTradePriceOnly);
    }

    public FxQuotesRequest(String query) {
        super(query, FxQuotesRequest.DEFAULT_PROPERTIES);
    }

    @Override
    protected FxQuote load(final String[] values) {
        if (values.length >= 2) {
            return new FxQuote(values[0], Utils.getBigDecimal(values[1]));
        }
        return null;
    }

}
