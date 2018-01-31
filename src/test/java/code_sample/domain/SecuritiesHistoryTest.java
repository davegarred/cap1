package code_sample.domain;

import static code_sample.api.ApiTestUtils.dateData;
import static code_sample.api.ApiTestUtils.expectedColumns;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import code_sample.api.ColumnDescription;
import code_sample.api.Datatable;
import code_sample.api.DateData;
import code_sample.api.UnexpectedClientResponseException;

public class SecuritiesHistoryTest {


	@Test
	public void testBuilder() {
		final List<DateData> data = new ArrayList<>();
		data.add(dateData("2017-01-03", new BigDecimal("800.62"), new BigDecimal("808.01"), new BigDecimal("811.435"), new BigDecimal("796.89"), new BigDecimal("1959033")));
		data.add(dateData("2017-01-04", new BigDecimal("809.89"), new BigDecimal("807.77"), new BigDecimal("813.43"), new BigDecimal("804.11"), new BigDecimal("1515339")));
		data.add(dateData("2017-02-01", new BigDecimal("824"), new BigDecimal("815.24"), new BigDecimal("824"), new BigDecimal("812.25"), new BigDecimal("2251047")));
		final Datatable datatable = new Datatable(data, expectedColumns());

		final SecuritiesHistory history = new SecuritiesHistory("GOOGL", datatable);
		assertEquals(2, history.getMonthlyHistory().size());

		final Iterator<SecuritiesMonthlyAverage> resultIterator = history.getMonthlyHistory().iterator();

		final SecuritiesMonthlyAverage january = resultIterator.next();
		assertEquals(LocalDate.of(2017, 1, 1), january.getMonth());

		final SecuritiesMonthlyAverage february = resultIterator.next();
		assertEquals(LocalDate.of(2017, 2, 1), february.getMonth());
	}

	@Test(expected = UnexpectedClientResponseException.class)
	public void testBuilder_missingData() {
		final List<DateData> data = singletonList(dateData("2017-01-03", //missing one field
				new BigDecimal("808.01"), new BigDecimal("811.435"), new BigDecimal("796.89"), new BigDecimal("1959033")));
		final Datatable datatable = new Datatable(data, expectedColumns());

		new SecuritiesHistory("GOOGL", datatable);
	}

	@Test(expected = UnexpectedClientResponseException.class)
	public void testBuilder_columnsMissing() {
		final List<DateData> data = singletonList(dateData("2017-01-03", new BigDecimal("800.62"), new BigDecimal("808.01"), new BigDecimal("811.435"), new BigDecimal("796.89"), new BigDecimal("1959033")));
		final List<ColumnDescription> columns = new ArrayList<>();
		columns.add(new ColumnDescription("date","Date"));
		// open column missing
//		columns.add(new ColumnDescription("open","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("close","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("high","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("low","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("volume","BigDecimal(37,15)"));
		final Datatable datatable = new Datatable(data, columns);

		new SecuritiesHistory("GOOGL", datatable);
	}

	@Test(expected = UnexpectedClientResponseException.class)
	public void testBuilder_columnsOutOfOrder() {
		final List<DateData> data = singletonList(dateData("2017-01-03", new BigDecimal("800.62"), new BigDecimal("808.01"), new BigDecimal("811.435"), new BigDecimal("796.89"), new BigDecimal("1959033")));
		final List<ColumnDescription> columns = new ArrayList<>();
		columns.add(new ColumnDescription("date","Date"));
		// close and open are reversed
		columns.add(new ColumnDescription("close","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("open","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("high","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("low","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("volume","BigDecimal(37,15)"));
		final Datatable datatable = new Datatable(data, columns);

		new SecuritiesHistory("GOOGL", datatable);
	}

}
