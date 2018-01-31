package code_sample.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DateData extends AbstractDto {

	public static final String COLUMNS = "date,open,close,high,low,volume";

	public final LocalDate date;
	public final BigDecimal open;
	public final BigDecimal close;
	public final BigDecimal high;
	public final BigDecimal low;
	public final BigDecimal volume;

	@JsonCreator
	public DateData(List<Object> rawObjects) {
		if(rawObjects.size() != 6) {
			throw new UnexpectedClientResponseException("Number of values received incorrect, expected 6, found " + rawObjects.size());
		}
		this.date = parseLocalDate(rawObjects.get(0));
		this.open = parseBigDecimal(rawObjects.get(1));
		this.close = parseBigDecimal(rawObjects.get(2));
		this.high = parseBigDecimal(rawObjects.get(3));
		this.low = parseBigDecimal(rawObjects.get(4));
		this.volume = parseBigDecimal(rawObjects.get(5));
	}

	private static LocalDate parseLocalDate(Object object) {
		try {
			return LocalDate.parse(object.toString());
		} catch (final DateTimeParseException e) {
			throw new UnexpectedClientResponseException("Format of data incorrect, expected a date but value is '" + object.toString() + "'");
		}
	}
	private static BigDecimal parseBigDecimal(Object object) {
		try {
			return new BigDecimal(object.toString());
		} catch (final NumberFormatException e) {
			throw new UnexpectedClientResponseException("Format of data incorrect, expected BigDecimal but value is '" + object.toString() + "'");
		}
	}


}
