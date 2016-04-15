package lf.bnade.model;

public class DailyAuction {
	private int itemId;
	private String name;
	private long avgMinBid;
	private long avgMinBuyout;
	private int avgTotalQuantity;
	private String realm;
	private long dataTime;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAvgMinBid() {
		return avgMinBid;
	}

	public void setAvgMinBid(long avgMinBid) {
		this.avgMinBid = avgMinBid;
	}

	public long getAvgMinBuyout() {
		return avgMinBuyout;
	}

	public void setAvgMinBuyout(long avgMinBuyout) {
		this.avgMinBuyout = avgMinBuyout;
	}

	public int getAvgTotalQuantity() {
		return avgTotalQuantity;
	}

	public void setAvgTotalQuantity(int avgTotalQuantity) {
		this.avgTotalQuantity = avgTotalQuantity;
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
