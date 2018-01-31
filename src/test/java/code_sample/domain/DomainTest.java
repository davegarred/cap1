package code_sample.domain;

import static code_sample.api.ApiTestUtils.dateData;
import static code_sample.api.ApiTestUtils.expectedColumns;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import code_sample.api.Datatable;
import code_sample.api.DateData;

public class DomainTest {

	private static final LocalDate JAN_5 = LocalDate.of(2017,1,5);
	private static final LocalDate JAN_6 = LocalDate.of(2017,1,6);
	private static final LocalDate JAN_7 = LocalDate.of(2017,1,7);

	@Test
	public void testAverages() {
		final List<DateData> data = new ArrayList<>();
		data.add(dateData(JAN_5, new BigDecimal("216"), new BigDecimal("218"), new BigDecimal("220"), new BigDecimal("214"), new BigDecimal("199205")));
		data.add(dateData(JAN_6, new BigDecimal("218"), new BigDecimal("210"), new BigDecimal("221"), new BigDecimal("210"), new BigDecimal("100253")));
		data.add(dateData(JAN_7, new BigDecimal("211"), new BigDecimal("220"), new BigDecimal("220"), new BigDecimal("210"), new BigDecimal("208564")));
		final SecuritiesHistory hist = new SecuritiesHistory("GOOGL", new Datatable(data, expectedColumns()));

		final SecuritiesMonthlyAverage january = hist.getMonthlyHistory().iterator().next();
		assertEquals(new BigDecimal("215"), january.getAverageOpen());
		assertEquals(new BigDecimal("216"), january.getAverageClose());

		final DailyValue max = hist.getDailyMaxProfit();
		assertEquals(JAN_6, max.getDate());
		assertEquals(new BigDecimal("11"), max.getValue());

		final List<DailyValue> busiestDays = hist.getBusiestDays();
		assertEquals(2, busiestDays.size());
		assertEquals(JAN_5, busiestDays.get(0).getDate());
		assertEquals(new BigDecimal("199205"), busiestDays.get(0).getValue());
		assertEquals(JAN_7, busiestDays.get(1).getDate());
		assertEquals(new BigDecimal("208564"), busiestDays.get(1).getValue());

		assertEquals(1, hist.getLosingDays());
	}

}
