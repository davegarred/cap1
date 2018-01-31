package code_sample;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Parse and hold command line arguments, show help,
 * cleanly return if bad input is used.
 */
public class ArgumentParser {

	private LocalDate startDate = LocalDate.of(2017, 1, 1);
	private LocalDate endDate = LocalDate.of(2017, 6, 30);
	private final List<String> tickers;

	private boolean profit;
	private boolean busy;
	private boolean loser;

	public ArgumentParser(String[] args) {
		this.tickers = parseArgs(args);
	}

	private List<String> parseArgs(String[] args) {
		final Options options = buildOptions();
		try {
			final CommandLine line = new DefaultParser().parse(options, args);
			if(line.hasOption("help")) {
				return showHelp(options);
			}
			if(line.hasOption("start")) {
				this.startDate = parseDate(line.getOptionValue("start"));
			}
			if(line.hasOption("end")) {
				this.endDate = parseDate(line.getOptionValue("end"));
			}
			if(line.hasOption("profit")) {
				this.profit = true;
			}
			if(line.hasOption("busy")) {
				this.busy = true;
			}
			if(line.hasOption("loser")) {
				this.loser = true;
			}
			return line.getArgList();
		} catch (final ParseException e) {
			System.out.println("Parse error - " + e.getMessage());
			return showHelp(options);
		}
	}

	private static LocalDate parseDate(String dateString) throws ParseException {
		try {
			return LocalDate.parse(dateString);
		} catch (final DateTimeParseException e) {
			throw new ParseException("Date must be in yyyy-MM-dd format, unable to parse: " + dateString);
		}
	}

	private static Options buildOptions() {
		final Options options = new Options();
		options.addOption("help", "Print this message");
		options.addOption("start", true, "Start date (format: yyyy-mm-dd), default: Jan 1, 2017");
		options.addOption("end", true, "End date (format: yyyy-mm-dd), default: Jun 30, 2017");
		options.addOption("profit", "For each security, show the day with the largest profit between daily high and low");
		options.addOption("busy", "For each security, show the days where the volume was more than 10% above the average");
		options.addOption("loser", "Select the security with the largest number of losing days");
		return options;
	}

	private static List<String> showHelp(final Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar <jarfile> [options] ticker...", options);
		return emptyList();
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public List<String> getTickers() {
		return this.tickers;
	}

	public boolean showMaxProfit() {
		return this.profit;
	}

	public boolean showBusiestDay() {
		return this.busy;
	}

	public boolean showBiggestLoser() {
		return this.loser;
	}

}
