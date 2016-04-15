package lf.bnade.model;

public class TopAuction {
	private int itemId;
	private long minBid;
	private String minBidOwner;
	private long minBuyout;
	private String minBuyoutOwner;
	private int totalQuantity;
	private String realm;
	// item name
	private String name;
	private long lastModified;
	
	private int count;

	public TopAuction() {
	}

	public TopAuction(int itemId, long minBid, String minBidOwner, long minBuyout, String minBuyoutOwner,
			int totalQuantity, String realm, long lastModified) {
		this.itemId = itemId;
		this.minBid = minBid;
		this.minBidOwner = minBidOwner;
		this.minBuyout = minBuyout;
		this.minBuyoutOwner = minBuyoutOwner;
		this.totalQuantity = totalQuantity;
		this.realm = realm;
		this.lastModified = lastModified;
	}
	public TopAuction(int itemId, long minBid, String minBidOwner, long minBuyout, String minBuyoutOwner,
			int totalQuantity, String realm, long lastModified, int count) {
		this.itemId = itemId;
		this.minBid = minBid;
		this.minBidOwner = minBidOwner;
		this.minBuyout = minBuyout;
		this.minBuyoutOwner = minBuyoutOwner;
		this.totalQuantity = totalQuantity;
		this.realm = realm;
		this.lastModified = lastModified;
		this.count = count;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public long getMinBid() {
		return minBid;
	}

	public void setMinBid(long minBid) {
		this.minBid = minBid;
	}

	public long getMinBuyout() {
		return minBuyout;
	}

	public void setMinBuyout(long minBuyout) {
		this.minBuyout = minBuyout;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public String getMinBidOwner() {
		return minBidOwner;
	}

	public void setMinBidOwner(String minBidOwner) {
		this.minBidOwner = minBidOwner;
	}

	public String getMinBuyoutOwner() {
		return minBuyoutOwner;
	}

	public void setMinBuyoutOwner(String minBuyoutOwner) {
		this.minBuyoutOwner = minBuyoutOwner;
	}

	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		return itemId + " " + name + " " + convert(minBid) + " "
				+ convert(minBuyout) + " " + totalQuantity + " " + realm + " " + lastModified +"\n";
	}

	static String convert(Long l) {
		String s = String.valueOf(l);
		int length = s.length();
		int jin = length - 4;
		String value = "";
		if (jin > 0) {
			value = s.substring(0, jin) + "G";
		} else {
			value = "<1G";
		}
		return value;
	}
}
