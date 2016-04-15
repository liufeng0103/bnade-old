package lf.bnade.model;

public class AuctionHouseStatistic {
	private int realmId;
	private int realmCount;
	private int auctionQuantity;
	private int ownerQuantity;
	private long updated;

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public int getRealmCount() {
		return realmCount;
	}

	public void setRealmCount(int realmCount) {
		this.realmCount = realmCount;
	}

	public int getAuctionQuantity() {
		return auctionQuantity;
	}

	public void setAuctionQuantity(int auctionQuantity) {
		this.auctionQuantity = auctionQuantity;
	}

	public int getOwnerQuantity() {
		return ownerQuantity;
	}

	public void setOwnerQuantity(int ownerQuantity) {
		this.ownerQuantity = ownerQuantity;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

}
