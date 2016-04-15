package lf.bnade.model;

public class AuctionFile {
	private int id;
	private String url;
	private long lastModified;
	private String realm;
	private int maxAucId;
	private int quantity;
	private int ownerQuantity;
	private int itemQuantity;
	private long createTime;
	private long lastUpdateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public int getMaxAucId() {
		return maxAucId;
	}

	public void setMaxAucId(int maxAucId) {
		this.maxAucId = maxAucId;
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String toString() {
		return "AuctionFile[" + id + url + lastModified + realm + createTime
				+ lastUpdateTime + "]";
	}
}
