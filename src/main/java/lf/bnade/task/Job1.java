package lf.bnade.task;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lf.bnade.model.Auction;
import lf.bnade.model.DailyAuction;
import lf.bnade.model.Realm;
import lf.bnade.model.TopAuction;
import lf.bnade.service.AuctionHistoryService;
import lf.bnade.service.RealmService;
import lf.bnade.util.TimeHelper;
import lf.bnade.util.TimeMoniter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 老的数据库表数据导入到新的表，估计有用一次以后很少会用到
 */
public class Job1 {
	
	private static Logger logger = LoggerFactory.getLogger(Job1.class);

	public static void main(String[] args) {
		if (args != null) {
			Job1 job = new Job1();
			if ("job1".equals(args[0])) {
				job.job1();
			}
			if ("job2".equals(args[0])) {
				job.job2();
			}
		}		
	}
	
	/*
	 * 导入history历史数据到服务器各表
	 */
	public void job1() {
		long start = System.currentTimeMillis();
		AuctionHistoryService auctionHistoryService = new AuctionHistoryService();
		// 获取所有服务器列表	
		RealmService realmService = new RealmService();
		File f = new File("jobstop");
		TimeMoniter t = new TimeMoniter();
		t.start();
		try {
			// 所有运行过该job的服务器名
			List<String> jobRealms = realmService.getJobRealms();
			// 所有服务器信息
			List<Realm> realmList = realmService.getRealms();
			t.spend();
			for (Realm realm : realmList) {
				if (jobRealms.contains(realm.getName())) {
					logger.info("服务器[{}]已运行过job1", realm.getName());
				} else {
					logger.info("服务器[{}]开始获取所有历史纪录", realm.getName());
					List<TopAuction> topAucs = auctionHistoryService.getHistoryByRealm(realm.getName()); 
					if (topAucs.size() > 0) {
						logger.info("服务器[{}]获取{}条历史纪录完毕{}", realm.getName(), topAucs.size(), t.spend());
						List<Auction> aucs = new ArrayList<Auction>();
						for (TopAuction topAuc : topAucs) {
							Auction auc = new Auction();					
							auc.setItemId(topAuc.getItemId());
							auc.setRealmId(realm.getId());
							auc.setMinBid(topAuc.getMinBid());
							auc.setMinBidOwner(topAuc.getMinBidOwner());
							auc.setMinBuyout(topAuc.getMinBuyout());
							auc.setMinBuyoutOwner(topAuc.getMinBuyoutOwner());
							auc.setTotalQuantity(topAuc.getTotalQuantity());
							auc.setLastModified(topAuc.getLastModified());
							aucs.add(auc);
						}
						topAucs.clear();
						logger.info("服务器[{}]开始保存历史纪录到表{}", realm.getName(), realm.getHistoryTableName());
						auctionHistoryService.addHistorys(aucs, realm.getHistoryTableName());
						logger.info("服务器[{}]保存历史纪录到表{}完毕{}", realm.getName(), realm.getHistoryTableName(), t.spend());					
						logger.info("服务器[{}]添加已处理到job control", realm.getName(), realmService.addJobRealm(realm.getName()));
					} else {
						logger.info("服务器[{}]获取{}条历史纪录，不做处理{}", realm.getName(), topAucs.size(), t.spend());
					}					
				}				
				if (f.exists()) {
					logger.info("发现stop文件,停止运行job1");		
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			logger.info("job1运行完毕用时{}", TimeHelper.format(System.currentTimeMillis() - start));
		}
	}

	/*
	 * 导入period history历史数据到服务器各表
	 */
	public void job2() {
		long start = System.currentTimeMillis();
		AuctionHistoryService auctionHistoryService = new AuctionHistoryService();
		File f = new File("jobstop");
		TimeMoniter t = new TimeMoniter();
		t.start();
		// 获取所有服务器列表	
		RealmService realmService = new RealmService();
		try {
			// 获取所有运行过的服务器名
			List<String> jobRealms = realmService.getJobRealms();
			// 服务器列表
			List<Realm> realmList = realmService.getRealms();
			t.spend();
			for (Realm realm : realmList) {
				if (jobRealms.contains(realm.getName())) {
					logger.info("服务器[{}]已运行过job2", realm.getName());
				} else {
					logger.info("服务器[{}]开始获取所有时段历史纪录", realm.getName());
					List<DailyAuction> topAucs = auctionHistoryService.getPeriodHistoryByRealm(realm.getName());
					if (topAucs.size() > 0) {
						logger.info("服务器[{}]获取{}条时段历史纪录完毕{}", realm.getName(), topAucs.size(), t.spend());
						List<Auction> aucs = new ArrayList<Auction>();
						for (DailyAuction topAuc : topAucs) {
							Auction auc = new Auction();					
							auc.setItemId(topAuc.getItemId());
							auc.setRealmId(realm.getId());
							auc.setMinBid(topAuc.getAvgMinBid());
							auc.setMinBidOwner("");
							auc.setMinBuyout(topAuc.getAvgMinBuyout());
							auc.setMinBuyoutOwner("");
							auc.setTotalQuantity(topAuc.getAvgTotalQuantity());
							auc.setLastModified(topAuc.getDataTime());
							aucs.add(auc);
						}
						topAucs.clear();
						logger.info("服务器[{}]开始保存时段历史纪录到表{}", realm.getName(), realm.getPeriodHistoryTableName());
						auctionHistoryService.addHistorys(aucs, realm.getPeriodHistoryTableName());
						logger.info("服务器[{}]保存时段历史纪录到表{}完毕{}", realm.getName(), realm.getPeriodHistoryTableName(), t.spend());					
						logger.info("服务器[{}]添加已处理到job control", realm.getName(), realmService.addJobRealm(realm.getName()));
//						logger.info("开始删除服务器[{}]时段历史纪录", realm.getName());				
//						logger.info("删除服务器[{}]{}条时段历史纪录完毕{}", realm.getName(), auctionHistoryService.removePeriodHistorys(realm.getName()), t.spend());
					} else {
						logger.info("服务器[{}]获取{}条时段历史纪录完毕{}", realm.getName(), topAucs.size(), t.spend());
						logger.info("服务器[{}]添加已处理到job control", realm.getName(), realmService.addJobRealm(realm.getName()));
					}					
				}
				if (f.exists()) {
					logger.info("发现stop文件,停止运行job2");		
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			logger.info("job2运行完毕用时{}", TimeHelper.format(System.currentTimeMillis() - start));
		}
	}	
	
}
