package code_sample.service;
import static code_sample.api.ApiTestUtils.dateData;
import static code_sample.api.ApiTestUtils.expectedColumns;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import code_sample.api.Datatable;
import code_sample.api.DateData;
import code_sample.api.TickerPriceDto;
import code_sample.client.SecuritiesClient;
import code_sample.domain.SecuritiesHistory;

public class SecuritiesServiceTest {

	private static final String TICKER_GOOGLE = "GOOGL";
	private static final LocalDate JANUARY_1 = LocalDate.of(2017, 1, 1);
	private static final LocalDate JANUARY_31 = LocalDate.of(2017, 1, 31);

	private final SecuritiesClient client = Mockito.mock(SecuritiesClient.class);
	private SecuritiesService tickerService;

	@Before
	public void setup() {
		this.tickerService = new SecuritiesService(this.client);
	}

	@Test
	public void test() {
		final List<DateData> data = new ArrayList<>();
		data.add(dateData("2017-01-03", new BigDecimal("800.62"), new BigDecimal("808.01"), new BigDecimal("811.435"), new BigDecimal("796.89"), new BigDecimal("1959033")));
		data.add(dateData("2017-01-04", new BigDecimal("809.89"), new BigDecimal("807.77"), new BigDecimal("813.43"), new BigDecimal("804.11"), new BigDecimal("1515339")));
		Mockito.when(this.client.getHistory(TICKER_GOOGLE, JANUARY_1, JANUARY_31))
			.thenReturn(new TickerPriceDto(new Datatable(data, expectedColumns())));

		final Collection<SecuritiesHistory> histories = this.tickerService.getData(JANUARY_1, JANUARY_31, asList(TICKER_GOOGLE));

		assertEquals(1, histories.size());
		assertEquals(1, histories.iterator().next().getMonthlyHistory().size());

	}
}
