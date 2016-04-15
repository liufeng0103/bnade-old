package lf.bnade.vo;

/*
 * 拍卖物品,用于restful json返回
 */
public class AuctionItem {
	private long minBuyout;
	private String minBuyoutOwner;
	private int totalQuantity;
	private long lastModified;

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
