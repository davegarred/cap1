package code_sample.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import code_sample.api.TickerPriceDto;
import code_sample.client.SecuritiesClient;
import code_sample.domain.SecuritiesHistory;

public class SecuritiesService {

	private final SecuritiesClient client;

	public SecuritiesService(SecuritiesClient client) {
		this.client = client;
	}

	public Collection<SecuritiesHistory> getData(LocalDate startDate, LocalDate endDate, List<String> tickers) {
		return tickers.stream()
				.map(t -> history(t, startDate, endDate))
				.collect(Collectors.toList());
	}

	private SecuritiesHistory history(String ticker, LocalDate startDate, LocalDate endDate) {
		final TickerPriceDto history = this.client.getHistory(ticker, startDate, endDate);
		return new SecuritiesHistory(ticker, history.datatable);
	}

}
