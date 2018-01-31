package code_sample.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Datatable extends AbstractDto {

	public final List<DateData> data;
	public final List<ColumnDescription> columns;

	@JsonCreator
	public Datatable(
			@JsonProperty("data") List<DateData> data,
			@JsonProperty("columns") List<ColumnDescription> columns) {
		this.data = data;
		this.columns = columns;
	}

}
