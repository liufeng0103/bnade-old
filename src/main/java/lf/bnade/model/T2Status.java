package lf.bnade.model;

public class T2Status {
	private String realm;
	private long dataTime;

	public T2Status(String realm, long dataTime) {
		this.realm = realm;
		this.dataTime = dataTime;
	}
	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public long getDataTime() {
		return dataTime;
	}

	public void setDataTime(long dataTime) {
		this.dataTime = dataTime;
	}

}
