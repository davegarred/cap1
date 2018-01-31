package code_sample.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyValue {

	private final LocalDate date;
	private final BigDecimal profit;

	public DailyValue(LocalDate date, BigDecimal profit) {
		super();
		this.date = date;
		this.profit = profit;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public BigDecimal getValue() {
		return this.profit;
	}

}
