package lf.bnade.model;

public class TaskHistory {
	
	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_FAILED = 1;

	private int id;
	private int type;
	private int realmId;
	private long processTime;
	private int status;
	private String message;

	public TaskHistory() {}
	
	public TaskHistory(int type, int realmId, long processTime, int status ) {
		this.type = type;
		this.realmId = realmId;
		this.processTime = processTime;
		this.status = status;
	}
		
	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(long processTime) {
		this.processTime = processTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
