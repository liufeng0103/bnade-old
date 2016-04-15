package lf.bnade.vo;

public class RealmAuctionPet {
	private String realm;
	private long minBuyout;
	private String minBuyoutOwner;
	private int totalQuantity;
	private long lastModified;
	private int petLevel;

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

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public int getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int petLevel) {
		this.petLevel = petLevel;
	}

}
