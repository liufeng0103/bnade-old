package lf.bnade.model;

public class Auction {
	private int itemId;
	private String name;
	private int realmId;
	private String realm;
	private long minBid;
	private String minBidOwner;
	private long minBuyout;
	private String minBuyoutOwner;
	private int totalQuantity;
	private String timeLeft;
	private long lastModified;
	private int petSpeciesId;
	private int petLevel;
	private int petBreedId;
	private int context;
	private String bonusList;

	public Auction() {
	}

	public Auction(int itemId, int realmId, long minBid, String minBidOwner,
			long minBuyout, String minBuyoutOwner, int totalQuantity, String timeLeft,
			long lastModified, int petSpeciesId, int petLevel, int petBreedId,
			int context, String bonusList) {
		this.itemId = itemId;
		this.realmId = realmId;
		this.minBid = minBid;
		this.minBidOwner = minBidOwner;
		this.minBuyout = minBuyout;
		this.minBuyoutOwner = minBuyoutOwner;
		this.totalQuantity = totalQuantity;
		this.timeLeft = timeLeft;
		this.lastModified = lastModified;
		this.petSpeciesId = petSpeciesId;
		this.petLevel = petLevel;
		this.petBreedId = petBreedId;
		this.context = context;
		this.bonusList = bonusList;
	}

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

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
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

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public int getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int petLevel) {
		this.petLevel = petLevel;
	}

	public int getPetBreedId() {
		return petBreedId;
	}

	public void setPetBreedId(int petBreedId) {
		this.petBreedId = petBreedId;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public String getBonusList() {
		return bonusList;
	}

	public void setBonusList(String bonusList) {
		this.bonusList = bonusList;
	}

	public String toString() {
		return itemId + name + minBuyout;
	}
}
