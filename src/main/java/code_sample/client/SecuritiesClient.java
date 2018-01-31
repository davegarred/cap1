package code_sample.client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.client.RestTemplate;

import code_sample.api.DateData;
import code_sample.api.TickerPriceDto;

public class SecuritiesClient {

	private static final DateTimeFormatter URL_DATE_PATTERN = DateTimeFormatter.ofPattern("yyyyMMdd");

	// Column order is controlled in the 'DateData' class to keep it near where it is being parsed
	private static final String URL = "https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?ticker={ticker}"
			+ "&qopts.columns=" + DateData.COLUMNS
			+ "&date.gte={start_date}"
			+ "&date.lte={last_date}"
			+ "&api_key=s-GMZ_xkw6CrkGYUWs1p";

	private final RestTemplate restTemplate = new RestTemplate();

	public TickerPriceDto getHistory(String ticker, LocalDate startDate, LocalDate endDate) {
		final String startDateStr = startDate.format(URL_DATE_PATTERN);
		final String endDateStr = endDate.format(URL_DATE_PATTERN);
		return this.restTemplate.getForObject(URL, TickerPriceDto.class, ticker, startDateStr, endDateStr);
	}
}
