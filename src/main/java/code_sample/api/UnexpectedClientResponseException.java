package code_sample.api;

@SuppressWarnings("serial")
public class UnexpectedClientResponseException extends RuntimeException {

	public UnexpectedClientResponseException(String message) {
		super(message);
	}

}
