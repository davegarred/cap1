package code_sample.api;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class ApiTestUtils {

	public static List<ColumnDescription> expectedColumns() {
		final List<ColumnDescription> columns = new ArrayList<>();
		columns.add(new ColumnDescription("date","Date"));
		columns.add(new ColumnDescription("open","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("close","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("high","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("low","BigDecimal(34,12)"));
		columns.add(new ColumnDescription("volume","BigDecimal(37,15)"));
		return columns;
	}

	public static DateData dateData(Object...objects) {
		return new DateData(asList(objects));
	}

}
