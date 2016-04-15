package lf.bnade.wow;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import lf.bnade.jmodel.JAuctionFiles;
import lf.bnade.jmodel.JAuctionLocation;
import lf.bnade.jmodel.JAuctions;
import lf.bnade.jmodel.JItem;
import lf.bnade.model.PetStats;
import lf.bnade.model.Player;
import lf.bnade.util.BnadeProperties;
import lf.bnade.util.BnadeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class WowClient {
	
	private static Logger logger = LoggerFactory.getLogger(WowClient.class);
	
	// 当调用api失败时最多尝试的次数
	private static final int TRY_COUNT = 6; 
	// 每次重新尝试中间等待时间，单位毫秒
	private static final long SLEEP_TIME_PER_TRY = 10000; 
	private Gson gson;
	private int count = 0;
	private Scanner scan2;
	private String region = BnadeProperties.getRegion();
	private String apiKey = BnadeProperties.getApiKey();
	
	private static Map<String, String> regionApi = new HashMap<String, String>();
	static {
		regionApi.put("GF", "https://api.battlenet.com.cn");
//		regionApi.put("GF", "https://www.battlenet.com.cn/api");		
		regionApi.put("TF", "https://tw.api.battle.net");
	}
	
	public WowClient() {
		gson = new Gson();
	}

	/*
	 * 获取服务器拍卖行数据文件的信息
	 */
	public JAuctionLocation getAuctionFile(String realm) {

		String json = null;
		JAuctionLocation jAuctionLocation = null;
		try {
			realm = realm.replaceAll(" ", "%20");
//			String url = regionApi.get(region) + "/wow/auction/data/"+ realm;
			String url = regionApi.get(region) + "/wow/auction/data/"+realm+"?apikey=" + apiKey + "&random="+System.currentTimeMillis();
			json = BnadeUtils.urlToString(url);
			jAuctionLocation = gson.fromJson(json, JAuctionFiles.class).getFiles().get(0);
			count = 0;
		} catch (Exception e) {
			if (count <= TRY_COUNT) {
				count++;
				try {
					logger.info(e.getMessage());
					logger.info("服务器[{}]获取auction file失败，等待{}秒，第{}次重新尝试", realm, SLEEP_TIME_PER_TRY / 1000, count);
					Thread.sleep(SLEEP_TIME_PER_TRY);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				jAuctionLocation = getAuctionFile(realm);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return jAuctionLocation;
	}
	
	public JAuctions getAuctions(String url) {		 
		JAuctions auctions = null;
		try {
			scan2 = new Scanner(new URL(url).openStream());
			StringBuffer sb = new StringBuffer();
			while(scan2.hasNextLine()) {
				sb.append(scan2.nextLine() + "\n");
			}
			String auctionJson = sb.toString();
			auctions = gson.fromJson(auctionJson, JAuctions.class);
			count = 0;
		} catch (Exception e) {
			if (count <= TRY_COUNT) {
				count++;
				try {
					logger.info(e.getMessage());
					logger.info("获取auctions失败，等待{}秒，第{}次重新尝试", SLEEP_TIME_PER_TRY / 1000, count);
					Thread.sleep(SLEEP_TIME_PER_TRY);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				auctions = getAuctions(url);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} 
		return auctions;
	}
	
	/*
	 * 获取物品信息
	 */
	public JItem getItem(int itemId) throws Exception {
		String url = regionApi.get(region) +  "/wow/item/"+itemId+"?apikey=" + apiKey;			
		String json = BnadeUtils.urlToString(url);
		JItem item = gson.fromJson(json, JItem.class);
		item.setJson(json);
		return item;
	}
	
	public PetStats getPetStats(int id, int breedId) throws Exception {
		String url = regionApi.get(region) +  "/wow/pet/stats/"+id+"?apikey=" + apiKey+"&level=25&qualityId=3&breedId="+breedId;			
		String json = BnadeUtils.urlToString(url);
		return gson.fromJson(json, PetStats.class);		
	}
	
	/*
	 * 获取玩家信息
	 */
	public Player getPlayer(String realm, String name) throws Exception {
		String url = regionApi.get(region) +  "/wow/character/"+realm+"/" + name + "?apikey=" + apiKey;			
		String json = BnadeUtils.urlToString2(url);
		return gson.fromJson(json, Player.class);		
	}
}
