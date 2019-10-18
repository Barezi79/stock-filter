
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class MakeMeRich {
	public static final List<String> symbols = Arrays.asList("AMD", "HPQ", "IBM", "TXN", "VMW", "XRX", "AAPL", "ADBE",
			"AMZN", "CRAY", "CSCO", "SNE", "GOOG", "INTC", "INTU", "MSFT", "ORCL", "TIBX", "VRSN", "YHOO");
	
	private static String rootURL = "https://www.alphavantage.co/query";
	private static String API_KEY = "57C1X6777MJP79OF";

	
	public static void main(String[] args) throws IOException {

		// 1. Print these symbols using a Java 8 for-each and lambdas
		symbols.stream().forEach(System.out::println);
		System.out.println();

		// 2. Use the StockUtil class to print the price of Bitcoin
		System.out.println(StockUtil.getPrice("BTC-USD"));
		System.out.println();

		// 3. Create a new List of StockInfo that includes the stock price
		List<StockInfo> listStockInfo = StockUtil.prices.entrySet().stream()
				.map(stock -> new StockInfo(stock.getKey(), stock.getValue())).collect(Collectors.toList());
		listStockInfo.forEach(System.out::println);

		// 4. Find the highest-priced stock under $500
		StockInfo highestPricedStock = listStockInfo.stream().filter(StockUtil.isPriceLessThan(500))
				.reduce(StockUtil::pickHigh).get();
		System.out.println(highestPricedStock + "is the highest priced stock under $500!");
		
		// 5. Connecting stock-filter to live data
	
		System.out.println();
		System.out.println();
		System.out.println("-----------------------------------------------------------");
		System.out.println(randomLivePrice(symbols));

	}
	public static String randomLivePrice(List symbols) throws IOException {
		
		String rootURL = "https://www.alphavantage.co/query";
		String API_KEY = "57C1X6777MJP79OF";
		
		String symbol = symbols.get((int)(Math.random()*(symbols.size() + 1))).toString();
		
		URL request = new URL(rootURL+"?function=TIME_SERIES_INTRADAY" + "&symbol=" + symbol + "&interval=1min" + "&apikey=" + API_KEY );
		InputStream input = request.openStream();
		String response = IOUtils.toString(input);
		
		JSONObject responseObject = new JSONObject(response);
		JSONObject dailyTimeSeries = (JSONObject)responseObject.get("Time Series (1min)");
		JSONObject newestEntry = (JSONObject)dailyTimeSeries.get(responseObject.getJSONObject("Meta Data").getString("3. Last Refreshed"));
		String currentHigh = newestEntry.getString("2. high");
			
			
		return "Current high price for " + symbol + " is " + currentHigh;
	}
}
