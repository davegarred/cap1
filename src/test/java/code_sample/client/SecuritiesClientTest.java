package code_sample.client;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import code_sample.api.TickerPriceDto;

/**
 * Integration test to probe the operation of the API
 */
public class SecuritiesClientTest {

	private static final String API_KEY = "s-GMZ_xkw6CrkGYUWs1p";
	private final SecuritiesClient client = new SecuritiesClient(API_KEY);

	@Test
	public void testClient() {
		final LocalDate startDate = LocalDate.of(2017, 1, 1);
		final LocalDate endDate = LocalDate.of(2017, 6, 30);

		final TickerPriceDto result = this.client.getHistory("GOOGL", startDate, endDate);
		assertEquals(6, result.datatable.columns.size());
		assertEquals(125, result.datatable.data.size());
	}
}
