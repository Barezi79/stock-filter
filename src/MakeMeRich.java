
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class MakeMeRich {
	public static final List<String> symbols = Arrays.asList("AMD", "HPQ", "IBM", "TXN", "VMW", "XRX", "AAPL", "ADBE",
			"AMZN", "CRAY", "CSCO", "SNE", "GOOG", "INTC", "INTU", "MSFT", "ORCL", "TIBX", "VRSN", "YHOO");
	
	private static String rootURL = "https://www.alphavantage.co/query";
	private static String API_KEY = "LN9GE2EHPSRVTYL8";

	
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

	}
}
