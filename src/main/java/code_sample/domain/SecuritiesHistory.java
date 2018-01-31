package code_sample.domain;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import code_sample.api.ColumnDescription;
import code_sample.api.Datatable;
import code_sample.api.DateData;
import code_sample.api.UnexpectedClientResponseException;

public class SecuritiesHistory {

	private final String security;
	private final Map<LocalDate,SecuritiesMonthlyHistory> historyMap = new HashMap<>();

	private DailyValue dailyMaxProfit;
	private BigDecimal totalVolume = ZERO;
	private final List<DailyValue> volumeByDay = new ArrayList<>();
	private int losingDays;
	private int daysWithData;

	public SecuritiesHistory(String security, Datatable datatable) {
		this.security = security;

		validateColumnOrder(datatable.columns);
		for(final DateData dailyHistory : datatable.data) {
			parse(dailyHistory);
		}
	}

	public String security() {
		return this.security;
	}

	public Collection<SecuritiesMonthlyHistory> monthlyHistory() {
		return this.historyMap.values();
	}

	private void parse(DateData dailyHistory) {
		final LocalDate firstOfMonth = dailyHistory.date.withDayOfMonth(1);
		SecuritiesMonthlyHistory hist = this.historyMap.get(firstOfMonth);
		if(hist == null) {
			hist = new SecuritiesMonthlyHistory(firstOfMonth);
			this.historyMap.put(firstOfMonth, hist);
		}
		this.daysWithData++;
		addDay(dailyHistory.date, dailyHistory.open, dailyHistory.close, dailyHistory.high, dailyHistory.low, dailyHistory.volume);
		hist.addDay(dailyHistory.open, dailyHistory.close);
	}

	private void addDay(LocalDate date, BigDecimal open, BigDecimal close, BigDecimal high, BigDecimal low, BigDecimal volume) {
		final BigDecimal profit = high.subtract(low);
		if(this.dailyMaxProfit == null || profit.compareTo(this.dailyMaxProfit.getValue()) > 0) {
			this.dailyMaxProfit = new DailyValue(date, profit);
		}
		if(close.compareTo(open) < 1) {
			this.losingDays++;
		}

		this.totalVolume = this.totalVolume.add(volume);
		this.volumeByDay.add(new DailyValue(date, volume));
	}

	public DailyValue dailyMaxProfit() {
		return this.dailyMaxProfit;
	}

	public int losingDays() {
		return this.losingDays;
	}

	public BigDecimal averageVolume() {
		return this.totalVolume.divide(new BigDecimal(this.daysWithData), HALF_UP);
	}

	public List<DailyValue> busiestDays() {
		final BigDecimal busiestCutoff = averageVolume().multiply(new BigDecimal(1.1));
		return this.volumeByDay.stream()
				.filter(v -> v.getValue().compareTo(busiestCutoff) > 0)
				.collect(Collectors.toList());
	}

	/**
	 * Validating the order of the columns is likely unnecessary, but since I'm unfamiliar
	 * with the API I've used these to protect myself from returning inaccurate data.
	 *
	 * I.e., fail early rather than have a customer tell me that my numbers are wrong later.
	 */
	private static void validateColumnOrder(List<ColumnDescription> columns) {
		if(columns.size() != 6) {
			throw new UnexpectedClientResponseException("Client response designates incorrect number of columns, expected 6, found " + columns.size());
		}
		validateExpectedColumn("date", columns.get(0));
		validateExpectedColumn("open", columns.get(1));
		validateExpectedColumn("close", columns.get(2));
		validateExpectedColumn("high", columns.get(3));
		validateExpectedColumn("low", columns.get(4));
		validateExpectedColumn("volume", columns.get(5));
	}

	private static void validateExpectedColumn(String expected, ColumnDescription columnDescription) {
		if(!expected.equals(columnDescription.name)) {
			throw new UnexpectedClientResponseException("Client response columns out of order, expected " + expected + ", found " + columnDescription.name);
		}
	}

}
