package lf.bnade.jmodel;

import java.util.List;

public class JAuctions {
	List<JRealm> realms;
	List<JAuction> auctions;
	public List<JRealm> getRealms() {
		return realms;
	}
	public void setRealms(List<JRealm> realms) {
		this.realms = realms;
	}
	public List<JAuction> getAuctions() {
		return auctions;
	}
	public void setAuctions(List<JAuction> auctions) {
		this.auctions = auctions;
	}
	
	public String toString() {
		return "" + realms + auctions;
	}
}

