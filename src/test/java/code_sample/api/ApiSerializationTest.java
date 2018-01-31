package code_sample.api;

import static code_sample.api.ApiTestUtils.dateData;
import static code_sample.api.ApiTestUtils.expectedColumns;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

public class ApiSerializationTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testSerialization() throws IOException {
		final List<DateData> data = new ArrayList<>();
		data.add(dateData("2017-01-03", new BigDecimal("800.62"), new BigDecimal("808.01"), new BigDecimal("811.435"), new BigDecimal("796.89"), new BigDecimal("1959033")));
		data.add(dateData("2017-01-04", new BigDecimal("809.89"), new BigDecimal("807.77"), new BigDecimal("813.43"), new BigDecimal("804.11"), new BigDecimal("1515339")));
		final TickerPriceDto tickerPriceDto = new TickerPriceDto(new Datatable(data, expectedColumns()));

		final TickerPriceDto storedObject = this.objectMapper.readValue(new ClassPathResource("json/TickerPriceDto.json").getInputStream(), TickerPriceDto.class);
		assertEquals(tickerPriceDto, storedObject);
	}

	@Test(expected = InvalidDefinitionException.class)
	public void testSerialization_badDateFormat() throws IOException {
		this.objectMapper.readValue(new ClassPathResource("json/TickerPriceDto_badDate.json").getInputStream(), TickerPriceDto.class);
	}

	@Test(expected = InvalidDefinitionException.class)
	public void testSerialization_badNumberFormat() throws IOException {
		this.objectMapper.readValue(new ClassPathResource("json/TickerPriceDto_badNumber.json").getInputStream(), TickerPriceDto.class);
	}

}
