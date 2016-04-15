package lf.bnade.service;

import java.sql.SQLException;

public class AuctionServiceTest {

	public static void main(String[] args) throws SQLException {
		AuctionService auctionService = new AuctionService();
//		System.out.println(auctionService.getPast24ItemByRealm("军团勋章", "古尔丹"));
//		System.out.println(auctionService.getPastWeekItemByRealm("军团勋章", "古尔丹"));
//		System.out.println(auctionService.getLatestItemsByName("军团勋章"));
//		System.out.println(auctionService.getLatestPetsByName("幽灵虎幼崽", 25));
		System.out.println(auctionService.getPast24PetsByRealm("小鱼", 25, "古尔丹"));
	}

}
