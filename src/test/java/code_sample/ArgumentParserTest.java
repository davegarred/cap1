package code_sample;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class ArgumentParserTest {

	private static final String API_KEY = "api_key";
	private static final LocalDate DEFAULT_START_DATE = LocalDate.of(2017, 1, 1);
	private static final LocalDate DEFAULT_END_DATE = LocalDate.of(2017, 6, 30);

	@Test
	public void testNoArguments() {
		final ArgumentParser parser = new ArgumentParser(new String[] {});
		assertEquals(DEFAULT_START_DATE, parser.getStartDate());
		assertEquals(DEFAULT_END_DATE, parser.getEndDate());
		assertTrue(parser.getTickers().isEmpty());
	}

	@Test
	public void testNoFlags() {
		final ArgumentParser parser = new ArgumentParser(new String[] {API_KEY, "GOOGL","COF"});
		assertEquals(DEFAULT_START_DATE, parser.getStartDate());
		assertEquals(DEFAULT_END_DATE, parser.getEndDate());
		assertEquals(asList("GOOGL","COF"),parser.getTickers());
	}

	@Test
	public void testDatesModified() {
		final ArgumentParser parser = new ArgumentParser(new String[] {"-start","2017-03-01","-end","2017-04-30"});
		assertEquals(LocalDate.of(2017, 3, 1), parser.getStartDate());
		assertEquals(LocalDate.of(2017, 4, 30), parser.getEndDate());
	}

	// parse error should not blow up the applications
	@Test
	public void testDatesModified_badDate() {
		new ArgumentParser(new String[] {"-start","2017-03-0","-end","2017-04-32"});
	}

	@Test
	public void testHelp() {
		final ArgumentParser parser = new ArgumentParser(new String[] {"-help", API_KEY, "GOOGL","COF"});
		assertTrue(parser.getTickers().isEmpty());
	}

	@Test
	public void testMaxProfit() {
		final ArgumentParser parser = new ArgumentParser(new String[] {"-profit", API_KEY, "GOOGL", "COF"});
		assertEquals(asList("GOOGL","COF"),parser.getTickers());
		assertTrue(parser.showMaxProfit());
	}

	@Test
	public void testBusy() {
		final ArgumentParser parser = new ArgumentParser(new String[] {"-busy", API_KEY, "GOOGL", "COF"});
		assertEquals(asList("GOOGL","COF"),parser.getTickers());
		assertTrue(parser.showBusiestDay());
	}
	@Test
	public void testBiggestLoser() {
		final ArgumentParser parser = new ArgumentParser(new String[] {"-loser", API_KEY, "GOOGL", "COF"});
		assertEquals(asList("GOOGL","COF"),parser.getTickers());
		assertTrue(parser.showBiggestLoser());
	}
}
