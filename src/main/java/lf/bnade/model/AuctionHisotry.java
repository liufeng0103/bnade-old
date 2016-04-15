package lf.bnade.model;

public class AuctionHisotry {
	private int id;
	private int itemId;
	private int realmId;
	private long minBid;
	private String minBidOwner;
	private long minBuyout;
	private String minBuyoutOwner;
	private int totalQuantity;
	private long lastModified;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public long getMinBid() {
		return minBid;
	}

	public void setMinBid(long minBid) {
		this.minBid = minBid;
	}

	public String getMinBidOwner() {
		return minBidOwner;
	}

	public void setMinBidOwner(String minBidOwner) {
		this.minBidOwner = minBidOwner;
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

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

}
