package code_sample;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import code_sample.api.UnexpectedClientResponseException;
import code_sample.client.SecuritiesClient;
import code_sample.domain.DailyValue;
import code_sample.domain.SecuritiesHistory;
import code_sample.domain.SecuritiesMonthlyAverage;
import code_sample.service.SecuritiesService;

/**
 * Entry point of the application as well code
 * that is generally untestable (i.e., system out)
 *
 */
public class CapOne {

	private static final DateTimeFormatter STANDARD_DATE = DateTimeFormatter.ofPattern("MMM dd, yyyy");
	private static final DateTimeFormatter MONTH_DATE = DateTimeFormatter.ofPattern("MMM yyyy");

	public static void main(String[] args) {
		final ArgumentParser argumentParser = new ArgumentParser(args);
		final List<String> tickers = argumentParser.getTickers();
		if(tickers.isEmpty()) {
			return;
		}
		final SecuritiesService securityService = new SecuritiesService(new SecuritiesClient(argumentParser.getApiKey()));
		try {
			final Collection<SecuritiesHistory> securityData = securityService.getData(argumentParser.getStartDate(), argumentParser.getEndDate(), tickers);
			showAllRequested(argumentParser, securityData);
		} catch(final UnexpectedClientResponseException e) {
			System.out.println("Unexpected response from the API - " + e.getMessage());
			System.out.println("Please verify that your API key is correct");
		}
	}

	private static void showAllRequested(final ArgumentParser argumentParser,
			final Collection<SecuritiesHistory> securityData) {
		showAverages(securityData, argumentParser.getStartDate(), argumentParser.getEndDate());

		if(argumentParser.showMaxProfit()) {
			showMaxProfit(securityData);
		}

		if(argumentParser.showBusiestDay()) {
			showBusiestDay(securityData);
		}

		if(argumentParser.showBiggestLoser()) {
			showBiggestLoser(securityData);
		}
	}

	private static void showAverages(Collection<SecuritiesHistory> securityData, LocalDate start, LocalDate end) {
		System.out.println();
		System.out.println(format("Monthly stock averages, %s - %s",start,end));
		for(final SecuritiesHistory hist : securityData) {
			System.out.println(hist.getSecurityTicker());
			if(hist.getMonthlyHistory().isEmpty()) {
				System.out.println("  No data found");
			}
			for(final SecuritiesMonthlyAverage month : hist.getMonthlyHistory()) {
				System.out.println("  " + month.getMonth().format(MONTH_DATE) + " - open:  " + month.getAverageOpen() + "  close: " + month.getAverageClose());
			}
		}
	}

	private static void showMaxProfit(Collection<SecuritiesHistory> securityData) {
		System.out.println();
		System.out.println("Maximum daily profit for each security");
		for(final SecuritiesHistory hist : securityData) {
			final DailyValue maxProfit = hist.getDailyMaxProfit();
			if(maxProfit == null) {
				System.out.println("  " + hist.getSecurityTicker() + ": No data found");
				return;
			}
			System.out.println("  " + hist.getSecurityTicker() + ": " + maxProfit.getDate().format(STANDARD_DATE) + " - " + maxProfit.getValue());
		}
	}

	private static void showBusiestDay(Collection<SecuritiesHistory> securityData) {
		System.out.println();
		System.out.println("Busiest days for each security (includes days with volume more than 10% above average)");
		for(final SecuritiesHistory hist : securityData) {
			final BigDecimal averageVolume = hist.getAverageVolume();
			if(BigDecimal.ZERO.equals(averageVolume)) {
				System.out.println(hist.getSecurityTicker() + " No data found");
				return;
			}
			System.out.println(hist.getSecurityTicker() +  " (average volume: " + averageVolume.setScale(0, HALF_UP) + ")");
			for(final DailyValue value: hist.getBusiestDays()) {
				System.out.println("  " + value.getDate().format(STANDARD_DATE) + " - " + value.getValue().setScale(0, HALF_UP));
			}
		}
	}

	private static void showBiggestLoser(Collection<SecuritiesHistory> securityData) {
		System.out.println();
		System.out.println("The biggest loser");
		String loser = "";
		int maxLosses = 0;
		for(final SecuritiesHistory hist : securityData) {
			System.out.println(format("  %s had %d losing days.", hist.getSecurityTicker(), hist.getLosingDays()));
			if(hist.getLosingDays() > maxLosses) {
				loser = hist.getSecurityTicker();
				maxLosses = hist.getLosingDays();
			}
		}
		System.out.println(String.format("%s took the most number of losses at %d", loser, maxLosses));
	}

}
