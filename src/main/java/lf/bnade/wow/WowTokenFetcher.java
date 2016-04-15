package lf.bnade.wow;

import java.util.Date;
import java.util.List;

import lf.bnade.jmodel.wowtoken.JWowToken;
import lf.bnade.jmodel.wowtoken.JWowTokenRegionHistory;
import lf.bnade.jmodel.wowtoken.JWowTokens;
import lf.bnade.jmodel.wowtoken.JWowTokensHistory;
import lf.bnade.util.BnadeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class WowTokenFetcher {
	
	private static Logger logger = LoggerFactory.getLogger(WowTokenFetcher.class);
	
	private static final String URL = "https://wowtoken.info/snapshot.json";
	private static final String HISTORY_URL = "https://wowtoken.info/wowtoken.json";
	
	// 当调用api失败时最多尝试的次数
	private static final int TRY_COUNT = 6; 
	// 每次重新尝试中间等待时间，单位毫秒
	private static final long SLEEP_TIME_PER_TRY = 10000; 
	private int count = 0;
	
	public JWowToken getWowTokenByRegion(String region) {
		JWowToken wowToken = null;
		try {
			Gson gson = new Gson();	
			String json = BnadeUtils.urlToString2(URL);
			wowToken = gson.fromJson(json, JWowTokens.class).getCN().getRaw();
			count = 0;
		} catch (Exception e) {
			if (count <= TRY_COUNT) {
				count++;
				try {
					logger.info(e.getMessage());
					logger.info("获取时光徽章数据失败，等待{}秒，第{}次重新尝试", SLEEP_TIME_PER_TRY / 1000, count);
					Thread.sleep(SLEEP_TIME_PER_TRY);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				wowToken = getWowTokenByRegion(region);
			} else {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return wowToken;
	}
	
	public JWowTokenRegionHistory getWowTokenHistoryByRegion(String region) {
		JWowTokenRegionHistory wowToken = null;
		try {
			Gson gson = new Gson();		    
			String json = BnadeUtils.urlToString2(HISTORY_URL);
//			String json = BnadeUtils.fileToString("test.json");	
//			System.out.println(json);
			wowToken = gson.fromJson(json, JWowTokensHistory.class).getHistory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wowToken;
	}
	
	public static void main(String[] args) throws Exception {
		WowTokenFetcher fetcher = new WowTokenFetcher();
		JWowToken wowToken = fetcher.getWowTokenByRegion("CN");
		System.out.println(wowToken.getBuy());
		System.out.println(new Date(wowToken.getUpdated()));
		
		JWowTokenRegionHistory history = fetcher.getWowTokenHistoryByRegion("CN");
		for (List<Long> t : history.getCN()) {
			System.out.println(new Date(t.get(0)*1000) + " " + t.get(1));
		}		
	}
	
	
}


