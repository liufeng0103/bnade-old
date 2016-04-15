package lf.bnade.task;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lf.bnade.jmodel.JAuction;
import lf.bnade.jmodel.JAuctionLocation;
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
public class Task1 {
	
	private static Logger logger = LoggerFactory.getLogger(Task1.class);
	
	private AuctionService auctionService;
	private WowClient client;
	private AuctionHandler topAuctionHandler;
	private TimeMoniter timeMoniter;
	private AuctionHistoryService auctionHistoryService;
	
	public Task1() {
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
				JAuctionLocation aucLocal = client.getAuctionFile(realm);
				logger.info("服务器[{}]通过API获取文件信息完毕{}", realm, timeMoniter.spend());
				AuctionFile aFile = new AuctionFile();
				logger.info("服务器[{}]开始下载拍卖行数据", realm);	
				JAuctions auctions = client.getAuctions(aucLocal.getUrl());
				logger.info("服务器[{}]下载完成{}条拍卖行数据{}", realm, auctions.getAuctions().size(), timeMoniter.spend());	
				
				processTopAuction(realmMap.get(realm), auctions, aucLocal.getLastModified());
				
				aFile.setRealm(realm);
				aFile.setLastModified(aucLocal.getLastModified());
				aFile.setUrl(aucLocal.getUrl());
				aFile.setMaxAucId(topAuctionHandler.getMaxAuctionId());
				aFile.setQuantity(auctions.getAuctions().size());
				aFile.setOwnerQuantity(topAuctionHandler.getOwnerQuantity());
				aFile.setItemQuantity(topAuctionHandler.getAuctionList().size());
				logger.info("服务器[{}]插入auction file,创建{}条记录{}", realm, auctionService.addAuctionFile(aFile), timeMoniter.spend());				
			} else if ((System.currentTimeMillis() - auctionFile.getLastModified()) > interval) {
				JAuctionLocation aucLocal = client.getAuctionFile(realm);
				logger.info("服务器[{}]通过API获取文件信息完毕{}", realm, timeMoniter.spend());
				if (aucLocal.getLastModified() != auctionFile.getLastModified()) {
					logger.info("服务器[{}]开始下载拍卖行数据", realm);
					JAuctions auctions = client.getAuctions(aucLocal.getUrl());
					logger.info("服务器[{}]下载完成{}条拍卖行数据{}", realm, auctions.getAuctions().size(), timeMoniter.spend());	
					
					processTopAuction(realmMap.get(realm), auctions, aucLocal.getLastModified());
					
					auctionFile.setRealm(realm);
					auctionFile.setLastModified(aucLocal.getLastModified());
					auctionFile.setUrl(aucLocal.getUrl());
					auctionFile.setMaxAucId(topAuctionHandler.getMaxAuctionId());
					auctionFile.setQuantity(auctions.getAuctions().size());
					auctionFile.setOwnerQuantity(topAuctionHandler.getOwnerQuantity());
					auctionFile.setItemQuantity(topAuctionHandler.getAuctionList().size());
					logger.info("服务器[{}]更新文件信息{}条记录{}", realm, auctionService.updateAuctionFile(auctionFile), timeMoniter.spend());
				} else {
					logger.info("服务器[{}]数据库中上一次修改时间{}与api的获取的修改时间一样，不更新记录", realm, new Date(auctionFile.getLastModified()), timeMoniter.spend());
				}
			} else {
				logger.info("服务器[{}]数据不更新, 上次更新时间为:{}, 当前时间：{}, 相隔时间没有超过设定的{}秒", realm, new Date(auctionFile.getLastModified()), new Date(System.currentTimeMillis()), interval/1000);
			}
		}
	}
	
	private void processTopAuction(Realm realm, JAuctions auctions, long lastModified) throws SQLException {
		String realmName = realm.getName();
		for(JAuction auction : auctions.getAuctions()) {
			topAuctionHandler.add(auction, realm.getId(), lastModified);
		}
		logger.info("服务器[{}]{}条拍卖行数据分析完毕{}", realmName, auctions.getAuctions().size(), timeMoniter.spend());	
		List<Auction> auctionList = topAuctionHandler.getAuctionList();
		// 清理服务器数据
		logger.info("服务器[{}]开始清理t_latest_auction数据", realmName);			
		logger.info("服务器[{}]{}条t_latest_auction数据清理完毕{}", realmName, auctionService.removeLatestAuctionsRealmId(realm.getId()),timeMoniter.spend());
		// 写入t_latest_auction表
		logger.info("服务器[{}]开始写入{}条数据到t_latest_auction表", realmName, auctionList.size());
		auctionService.addLatestAuctions(auctionList);
		logger.info("服务器[{}]写入{}条数据到t_latest_auction表完毕{}", realmName, auctionList.size(), timeMoniter.spend());
		// 写入history表
		logger.info("服务器[{}]开始写入{}条数据到{}表", realmName, auctionList.size(), realm.getHistoryTableName());
//		auctionHistoryService.addHistorys(auctionList, realm.getHistoryTableName());
		auctionHistoryService.copyLatestAuctionsToHistoryByRealmId(realm.getHistoryTableName(), realm.getId());
		logger.info("服务器[{}]写入{}条数据到{}表完毕{}", realmName, auctionList.size(), realm.getHistoryTableName(), timeMoniter.spend());
		updatePlayerTmp(realmName, realm.getId());
	}
	private void updatePlayerTmp(String realmName, int realmId) {
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
		Task1 task = new Task1();
		task.process("古尔丹");
	}

	public void process(List<String> realms) throws SQLException, Exception {
		
	}
}
