package lf.bnade.task;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lf.bnade.jmodel.JAuction;
import lf.bnade.jmodel.JAuctions;
import lf.bnade.model.Auction;
import lf.bnade.model.AuctionFile;
import lf.bnade.model.Player;
import lf.bnade.model.Realm;
import lf.bnade.service.AuctionHistoryService;
import lf.bnade.service.AuctionService;
import lf.bnade.service.PlayerService;
import lf.bnade.service.RealmService;
import lf.bnade.service.TaskService;
import lf.bnade.task.handler.AuctionHandler;
import lf.bnade.util.BnadeProperties;
import lf.bnade.util.TimeMoniter;
import lf.bnade.wow.WowClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取服务器拍卖行数据，把每个物品的最低价格保持到数据库
 *
 */
public class Task1Tmp {
	
	private static Logger logger = LoggerFactory.getLogger(Task1Tmp.class);
	
	private AuctionService auctionService;
	private WowClient client;
	private AuctionHandler topAuctionHandler;
	private TimeMoniter timeMoniter;
	private AuctionHistoryService auctionHistoryService;
	
	public Task1Tmp() {
		auctionService = new AuctionService();
		client = new WowClient();
		topAuctionHandler = new AuctionHandler();
		auctionHistoryService = new AuctionHistoryService();
	}
	
	public void process(String realm) throws SQLException {
		logger.info("服务器[{}]开始运行{}", realm, getName());
		timeMoniter = new TimeMoniter();
		timeMoniter.start();
		long interval = BnadeProperties.getInterval();
		TaskService taskService = new TaskService();
		RealmService realmService = new RealmService();
		List<Realm> realms = realmService.getRealms();
		Map<String, Realm> realmMap = new HashMap<String, Realm>();
		for (Realm realmObj : realms) {
			realmMap.put(realmObj.getName(), realmObj);
		}
		String runningRealm = taskService.getRunningRealm();
		if (realm.equals(runningRealm)) {
			logger.info("服务器[{}]Task2正在清理这个服务器数据,跳过这次更新", realm);
		} else {
			AuctionFile auctionFile = auctionService.getAuctionFileByRealm(realm);
			logger.info("服务器[{}]从数据库读取上一次文件信息完毕{}", realm, timeMoniter.spend());
			if (auctionFile == null) {
				// 找不到json地址，不能
			} else if ((System.currentTimeMillis() - auctionFile.getLastModified()) > interval) {
				long currentTime = System.currentTimeMillis();
				String url = auctionFile.getUrl();
				int maxAucId = auctionFile.getMaxAucId();
				
				logger.info("服务器[{}]开始下载拍卖行数据", realm);
				JAuctions auctions = client.getAuctions(url);
				logger.info("服务器[{}]下载完成{}条拍卖行数据{}", realm, auctions.getAuctions().size(), timeMoniter.spend());	
				
				auctionFile.setRealm(realm);
				auctionFile.setLastModified(currentTime);
				auctionFile.setQuantity(auctions.getAuctions().size());

				processTopAuction(realmMap.get(realm), auctions, currentTime, maxAucId, auctionFile);
			} else {
				logger.info("服务器[{}]数据不更新, 上次更新时间为:{}, 当前时间：{}, 相隔时间没有超过设定的{}秒", realm, new Date(auctionFile.getLastModified()), new Date(System.currentTimeMillis()), interval/1000);
			}
		}
	}
	
	private void processTopAuction(Realm realm, JAuctions auctions, long lastModified, int maxAucId, AuctionFile auctionFile) throws SQLException {
		String realmName = realm.getName();
		for(JAuction auction : auctions.getAuctions()) {
			topAuctionHandler.add(auction, realm.getId(), lastModified);
		}
		logger.info("服务器[{}]{}条拍卖行数据分析完毕{}", realmName, auctions.getAuctions().size(), timeMoniter.spend());	
		List<Auction> auctionList = topAuctionHandler.getAuctionList();
		int latestMaxAucId = topAuctionHandler.getMaxAuctionId();
		logger.info("服务器[{}]最大的拍卖ID{},上一次拍卖ID{}", realmName, latestMaxAucId, maxAucId);
		if (latestMaxAucId > maxAucId) {
			auctionFile.setMaxAucId(latestMaxAucId);
			auctionFile.setOwnerQuantity(topAuctionHandler.getOwnerQuantity());
			logger.info("服务器[{}]更新文件信息{}条记录{}", realmName, auctionService.updateAuctionFile(auctionFile), timeMoniter.spend());
			// 清理服务器所有数据
			logger.info("服务器[{}]开始清理t_auction_house_{}数据", realmName, realm.getId());			
			logger.info("服务器[{}]{}条t_auction_house_{}数据清理完毕{}", realmName, auctionService.removeAuctionsByRealmId(realm.getId()), realm.getId(),timeMoniter.spend());
			// 写入所有数据到t_auction_house表
			List<JAuction> jAuctions = auctions.getAuctions();
			logger.info("服务器[{}]开始写入{}条数据到t_auction_house_{}表", realmName, jAuctions.size(), realm.getId());
			auctionService.addAuctions(jAuctions, realm.getId());
			logger.info("服务器[{}]写入{}条数据到t_auction_house_{}表完毕{}", realmName, jAuctions.size(), realm.getId(), timeMoniter.spend());
			
			// 清理服务器数据
			logger.info("服务器[{}]开始清理t_latest_auction数据", realmName);			
			logger.info("服务器[{}]{}条t_latest_auction数据清理完毕{}", realmName, auctionService.removeLatestAuctionsRealmId(realm.getId()),timeMoniter.spend());
			// 写入t_latest_auction表
			logger.info("服务器[{}]开始写入{}条数据到t_latest_auction表", realmName, auctionList.size());
			auctionService.addLatestAuctions(auctionList);
			logger.info("服务器[{}]写入{}条数据到t_latest_auction表完毕{}", realmName, auctionList.size(), timeMoniter.spend());
			// 写入history表
			logger.info("服务器[{}]开始写入{}条数据到{}表", realmName, auctionList.size(), realm.getHistoryTableName());
//			auctionHistoryService.addHistorys(auctionList, realm.getHistoryTableName());
			auctionHistoryService.copyLatestAuctionsToHistoryByRealmId(realm.getHistoryTableName(), realm.getId());
			logger.info("服务器[{}]写入{}条数据到{}表完毕{}", realmName, auctionList.size(), realm.getHistoryTableName(), timeMoniter.spend());
//			updatePlayerTmp(realmName, realm.getId());
		} else {
			logger.info("服务器[{}]2次ID一样不更新", realmName);
		}
	}

	private void updatePlayerTmp(String realmName, int realmId) throws SQLException {
		try {
			// 添加这次更新的新玩家
			logger.info("服务器[{}]开始添加新玩家", realmName);
			PlayerService playerService = new PlayerService();
			List<Player> players = topAuctionHandler.getPlayers();
			List<Player> dbPlayers = playerService.getCurrentPlayersByRealmId(realmId);
			logger.info("服务器[{}]当前玩家数{}, 数据库玩家数{}", realmName, players.size(), dbPlayers.size());
			players.removeAll(dbPlayers);
			logger.info("服务器[{}]新玩家数{},开始保存", realmName, players.size());
			playerService.addCurrentPlayers(players);
			logger.info("服务器[{}]新玩家保存完毕", realmName);
		} catch (SQLException e) {
			String error=e.getMessage();
			if(error.length() > 255){
				error=error.substring(0, 255);
			}
			logger.error("服务器[{}]异常:{}", realmName, error);			
		}
	}
	
	public int getType() {
		return 0;
	}
	
	public String getName() {
		return "Task1";
	}
	
	public String getDescription() {
		return "";
	}

	public static void main(String[] args) throws SQLException {
		Task1Tmp task = new Task1Tmp();
		task.process("古尔丹");
	}

	public void process(List<String> realms) throws SQLException, Exception {
		
	}
}
