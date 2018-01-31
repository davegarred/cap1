package code_sample.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ColumnDescription extends AbstractDto {

	public final String name;
	public final String type;

	@JsonCreator
	public ColumnDescription(
			@JsonProperty("name") String name,
			@JsonProperty("type") String type) {
		this.name = name;
		this.type = type;
	}


}
