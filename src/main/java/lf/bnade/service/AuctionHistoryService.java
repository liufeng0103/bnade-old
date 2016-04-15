package lf.bnade.service;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.dao.AuctionHistoryDao;
import lf.bnade.model.Auction;
import lf.bnade.model.DailyAuction;
import lf.bnade.model.TopAuction;

public class AuctionHistoryService extends BaseService {
	
	private AuctionHistoryDao auctionHistoryDao;
	
	public AuctionHistoryService() {
		auctionHistoryDao = new AuctionHistoryDao();
	}

	/*
	 * 获取某个服务器所有的历史纪录
	 */
	public List<TopAuction> getHistoryByRealm(String realm) throws SQLException {
		return auctionHistoryDao.getHistoryByRealm(realm);
	}
	
	/*
	 * 获取某个服务器所有的daily历史纪录
	 */
	public List<DailyAuction> getPeriodHistoryByRealm(String realm) throws SQLException {
		return auctionHistoryDao.getPeriodHistoryByRealm(realm);
	}
	
	/*
	 * 保存history到指定表
	 */
	public void addHistorys(List<Auction> aucs, String tableName) throws SQLException {
		auctionHistoryDao.addHistorys(aucs, tableName);
	}
	
	/*
	 * 获取指定表中某段时间的history记录
	 */
	public List<Auction> getHistoryByRangeTime(String tableName, long startTime, long endTime) throws SQLException {
		return auctionHistoryDao.getHistoryByRangeTime(tableName, startTime, endTime);
	}
	
	/*
	 * 导入最近拍卖数据到history表格
	 */
	public int copyLatestAuctionsToHistoryByRealmId(String tableName, int realmId) throws SQLException {
		return auctionHistoryDao.copyLatestAuctionsToHistoryByRealmId(tableName, realmId);
	}
	
	/*
	 * 删除指定表某段时间内的数
	 */
	public int removeHistoryForTableAndTime(String tableName, long startTime, long endTime) throws SQLException {
		return auctionHistoryDao.removeHistoryForTableAndTime(tableName, startTime, endTime);
	}
	
}
