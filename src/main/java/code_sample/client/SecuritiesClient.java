package code_sample.client;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import code_sample.api.DateData;
import code_sample.api.TickerPriceDto;
import code_sample.api.UnexpectedClientResponseException;

/**
 * Communicate with the API and return the expected DTO
 */
public class SecuritiesClient {

	private static final DateTimeFormatter URL_DATE_PATTERN = DateTimeFormatter.ofPattern("yyyyMMdd");

	// Column order is controlled in the 'DateData' class to keep it near where it is being parsed
	private static final String URL = "https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?ticker={ticker}"
			+ "&qopts.columns=" + DateData.COLUMNS
			+ "&date.gte={start_date}"
			+ "&date.lte={last_date}"
			+ "&api_key={api_key}";

	private final RestTemplate restTemplate = new RestTemplate();
	private final String apiKey;

	public SecuritiesClient(String apiKey) {
		this.apiKey = apiKey;
	}

	public TickerPriceDto getHistory(String ticker, LocalDate startDate, LocalDate endDate) {
		final String startDateStr = startDate.format(URL_DATE_PATTERN);
		final String endDateStr = endDate.format(URL_DATE_PATTERN);
		try {
			return this.restTemplate.getForObject(URL, TickerPriceDto.class, ticker, startDateStr, endDateStr, this.apiKey);
		} catch(final HttpClientErrorException e) {
			if(BAD_REQUEST.equals(e.getStatusCode())) {
				throw new UnexpectedClientResponseException(e.getMessage());
			}
			// In an application with a logging file available we would log this, here we'll pass it up to the command line
			throw e;
		}
	}
}
