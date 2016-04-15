package lf.bnade.jmodel.wowtoken;


public class JWowToken {
	private int buy;
	private long updated;

	public int getBuy() {
		return buy;
	}

	public void setBuy(int buy) {
		this.buy = buy;
	}

	public long getUpdated() {
		return updated * 1000;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

}
