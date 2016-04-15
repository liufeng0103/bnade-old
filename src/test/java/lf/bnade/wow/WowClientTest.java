package lf.bnade.wow;

import java.util.List;

import lf.bnade.jmodel.JAuction;
import lf.bnade.jmodel.JAuctions;

public class WowClientTest {

	public static void main(String[] args) {
		WowClient c = new WowClient();
		JAuctions j = c.getAuctions("http://auction-api-cn.worldofwarcraft.com/auction-data/6b258426dbb37f9c73f0e008e6d56687/auctions.json");
		List<JAuction> aus = j.getAuctions();
		for (JAuction au : aus) {
			if(!"".equals(au.getBonusLists())){
				System.out.println(au);	
			}
					
		}
	}

}
