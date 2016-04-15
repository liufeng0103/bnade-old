package lf.bnade.jmodel;

public class Result {

	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	
	public Result(String status, String errorMessage) {
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	public Result(int code, String status, String errorMessage) {
		this.code = code;
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	public static Result SUCCESS(String errorMessage) {
		return new Result(200, SUCCESS, errorMessage);
	}
	
	public static Result FAILED(String errorMessage) {
		return new Result(201, FAILED, errorMessage);
	}
	
	public static Result FAILED(int code, String errorMessage) {
		return new Result(code, FAILED, errorMessage);
	}
	
	private int code;
	private String status;
	private String errorMessage;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
