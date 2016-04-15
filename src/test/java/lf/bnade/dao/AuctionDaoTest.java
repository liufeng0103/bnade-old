package lf.bnade.dao;

import java.sql.SQLException;

public class AuctionDaoTest {

	public static void main(String[] args) throws SQLException {
		AuctionDao ad = new AuctionDao();
		System.out.println(ad.getLatestPetsByName("小鱼"));;
	}
}
