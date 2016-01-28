package yahoofinance.quotes.stock;

import yahoofinance.quotes.QuotesProperty;
import yahoofinance.quotes.QuotesRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stijn Strickx
 */
public class StockQuotesRequest extends QuotesRequest<StockQuotesData> {

    /**
     * Yahoo Finance is responding with formatted numbers in some cases. Because
     * of this, those number may contain commas. This will screw up the CSV
     * file.
     * <p>
     * It's not possible to choose a different delimiter for the CSV or to
     * disable the number formatting
     * <p>
     * To work around this, we surround the vulnerable values by the stock
     * symbol. This forces us to do manual parsing of the CSV lines instead of
     * using the easy String.split
     */
    public static final List<QuotesProperty> DEFAULT_PROPERTIES = new ArrayList<QuotesProperty>();

    static {

        // Always keep the name and symbol in first and second place respectively!
        DEFAULT_PROPERTIES.add(QuotesProperty.Name);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);

        DEFAULT_PROPERTIES.add(QuotesProperty.Currency);
        DEFAULT_PROPERTIES.add(QuotesProperty.StockExchange);

        DEFAULT_PROPERTIES.add(QuotesProperty.Ask);
        DEFAULT_PROPERTIES.add(QuotesProperty.AskRealtime);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.AskSize);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.Bid);
        DEFAULT_PROPERTIES.add(QuotesProperty.BidRealtime);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.BidSize);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);

        DEFAULT_PROPERTIES.add(QuotesProperty.LastTradePriceOnly);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.LastTradeSize);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.LastTradeDate);
        DEFAULT_PROPERTIES.add(QuotesProperty.LastTradeTime);

        DEFAULT_PROPERTIES.add(QuotesProperty.Open);
        DEFAULT_PROPERTIES.add(QuotesProperty.PreviousClose);
        DEFAULT_PROPERTIES.add(QuotesProperty.DaysLow);
        DEFAULT_PROPERTIES.add(QuotesProperty.DaysHigh);

        DEFAULT_PROPERTIES.add(QuotesProperty.Volume);
        DEFAULT_PROPERTIES.add(QuotesProperty.AverageDailyVolume);

        DEFAULT_PROPERTIES.add(QuotesProperty.YearHigh);
        DEFAULT_PROPERTIES.add(QuotesProperty.YearLow);

        DEFAULT_PROPERTIES.add(QuotesProperty.FiftydayMovingAverage);
        DEFAULT_PROPERTIES.add(QuotesProperty.TwoHundreddayMovingAverage);

        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.SharesOutstanding);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.SharesOwned);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.MarketCapitalization);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);
        DEFAULT_PROPERTIES.add(QuotesProperty.SharesFloat);
        DEFAULT_PROPERTIES.add(QuotesProperty.Symbol);

        DEFAULT_PROPERTIES.add(QuotesProperty.DividendPayDate);
        DEFAULT_PROPERTIES.add(QuotesProperty.ExDividendDate);
        DEFAULT_PROPERTIES.add(QuotesProperty.TrailingAnnualDividendYield);
        DEFAULT_PROPERTIES.add(QuotesProperty.TrailingAnnualDividendYieldInPercent);

        DEFAULT_PROPERTIES.add(QuotesProperty.DilutedEPS);
        DEFAULT_PROPERTIES.add(QuotesProperty.EPSEstimateCurrentYear);
        DEFAULT_PROPERTIES.add(QuotesProperty.EPSEstimateNextQuarter);
        DEFAULT_PROPERTIES.add(QuotesProperty.EPSEstimateNextYear);
        DEFAULT_PROPERTIES.add(QuotesProperty.PERatio);
        DEFAULT_PROPERTIES.add(QuotesProperty.PEGRatio);

        DEFAULT_PROPERTIES.add(QuotesProperty.PriceBook);
        DEFAULT_PROPERTIES.add(QuotesProperty.PriceSales);
        DEFAULT_PROPERTIES.add(QuotesProperty.BookValuePerShare);

        DEFAULT_PROPERTIES.add(QuotesProperty.Revenue);
        DEFAULT_PROPERTIES.add(QuotesProperty.EBITDA);
        DEFAULT_PROPERTIES.add(QuotesProperty.OneyrTargetPrice);

    }

    public StockQuotesRequest(String query) {
        super(query, StockQuotesRequest.DEFAULT_PROPERTIES);
    }

    @Override
    protected StockQuotesData load(final String[] values) {
        return new StockQuotesData(values);
    }

}
