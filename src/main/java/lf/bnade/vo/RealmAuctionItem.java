package lf.bnade.vo;

public class RealmAuctionItem {
	private String realm;
	private long minBuyout;
	private String minBuyoutOwner;
	private int totalQuantity;
	private String timeLeft;
	private long lastModified;

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public long getMinBuyout() {
		return minBuyout;
	}

	public void setMinBuyout(long minBuyout) {
		this.minBuyout = minBuyout;
	}

	public String getMinBuyoutOwner() {
		return minBuyoutOwner;
	}

	public void setMinBuyoutOwner(String minBuyoutOwner) {
		this.minBuyoutOwner = minBuyoutOwner;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

}
