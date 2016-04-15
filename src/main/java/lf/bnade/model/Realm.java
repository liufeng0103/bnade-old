package lf.bnade.model;

public class Realm {
	private int id;
	private String name;
	private String historyTableName;
	private String periodHistoryTableName;
	private String dailyHistoryTableName;
	
	public Realm(String name, String historyTableName, String periodHistoryTableName, String dailyHistoryTableName) {
		this.name = name;
		this.historyTableName = historyTableName;
		this.periodHistoryTableName = periodHistoryTableName;
		this.dailyHistoryTableName = dailyHistoryTableName;
	}
	
	public Realm() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHistoryTableName() {
		return historyTableName;
	}

	public void setHistoryTableName(String historyTableName) {
		this.historyTableName = historyTableName;
	}

	public String getPeriodHistoryTableName() {
		return periodHistoryTableName;
	}

	public void setPeriodHistoryTableName(String periodHistoryTableName) {
		this.periodHistoryTableName = periodHistoryTableName;
	}

	public String getDailyHistoryTableName() {
		return dailyHistoryTableName;
	}

	public void setDailyHistoryTableName(String dailyHistoryTableName) {
		this.dailyHistoryTableName = dailyHistoryTableName;
	}

}
