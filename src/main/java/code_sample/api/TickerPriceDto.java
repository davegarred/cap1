package code_sample.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TickerPriceDto extends AbstractDto {

	public final Datatable datatable;

	@JsonCreator
	public TickerPriceDto(@JsonProperty("datatable") Datatable datatable) {
		this.datatable = datatable;
	}

}
