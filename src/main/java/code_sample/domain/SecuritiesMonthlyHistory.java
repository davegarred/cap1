package code_sample.domain;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SecuritiesMonthlyHistory {

	private final LocalDate firstDayOfMonth;

	private BigDecimal sumOpen = ZERO;
	private BigDecimal sumClose = ZERO;
	private int daysWithData;

	public SecuritiesMonthlyHistory(LocalDate firstDayOfMonth) {
		this.firstDayOfMonth = firstDayOfMonth;
	}

	public void addDay(BigDecimal open, BigDecimal close) {
		this.sumOpen = this.sumOpen.add(open);
		this.sumClose = this.sumClose.add(close);
		this.daysWithData++;
	}

	public LocalDate getMonth() {
		return this.firstDayOfMonth;
	}

	public BigDecimal getAverageOpen() {
		return this.sumOpen.divide(new BigDecimal(this.daysWithData), HALF_UP);
	}

	public BigDecimal getAverageClose() {
		return this.sumClose.divide(new BigDecimal(this.daysWithData), HALF_UP);
	}

}
