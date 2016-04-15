package lf.bnade.vo;

public class RealmAuctionQuantity {
	private String realm;
	private int quantity;
	private int ownerQuantity;
	private int itemQuantity;
	private long lastUpdateTime;

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOwnerQuantity() {
		return ownerQuantity;
	}

	public void setOwnerQuantity(int ownerQuantity) {
		this.ownerQuantity = ownerQuantity;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
