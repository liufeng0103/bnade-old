package lf.bnade.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lf.bnade.model.Auction;
import lf.bnade.model.DailyAuction;
import lf.bnade.model.TopAuction;
import lf.bnade.util.DSUtil;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class AuctionHistoryDao extends BaseDao {
	
	/*
	 * 获取某个服务器所有的历史纪录
	 */
	public List<TopAuction> getHistoryByRealm(String realm) throws SQLException {
		return run.query("select * from t_top_auction_history where realm=?",
				new BeanListHandler<TopAuction>(TopAuction.class), realm);
	}
	
	/*
	 * 获取某个服务器所有的daily历史纪录
	 */
	public List<DailyAuction> getPeriodHistoryByRealm(String realm) throws SQLException {
		return run.query("select * from t_top_auction_daily where realm=?",
				new BeanListHandler<DailyAuction>(DailyAuction.class), realm);
	}
	
	/*
	 * 获取指定表中某段时间的history记录
	 */
	public List<Auction> getHistoryByRangeTime(String tableName, long startTime, long endTime) throws SQLException {
		return run.query("select * from " + tableName + " where lastModified >=? and lastModified <=?",
				new BeanListHandler<Auction>(Auction.class), startTime, endTime);
	}
	
	/*
	 * 数据保存到各种History表, 每500提交一次
	 */
	public void addHistorys(List<Auction> aucs, String tableName) throws SQLException {
		int commitCount = 500;
		Connection conn = DSUtil.getDataSource().getConnection();
		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		List<Auction> tmpAucs = new ArrayList<Auction>();
		int rowCount = 0;
		for(int i = 0; i < aucs.size(); i++) {
			tmpAucs.add(aucs.get(i));
			if ((i + 1) % commitCount == 0) {				
				rowCount = commitCount;
				commitAuction(rowCount, tmpAucs, conn, tableName);
			} else if (i == aucs.size() -1) {
				rowCount = aucs.size() - (i + 1) / commitCount * commitCount;
				commitAuction(rowCount, tmpAucs, conn, tableName);
			}
		}		
		conn.setAutoCommit(autoCommit);
		DbUtils.close(conn);
	}
	
	private void commitAuction(int count, List<Auction> tmpAucs, Connection conn, String tableName) throws SQLException {
		Object[][] params = new Object[count][12];
		for (int j = 0; j < tmpAucs.size(); j++) {
			params[j][0] = tmpAucs.get(j).getItemId();
			params[j][1] = tmpAucs.get(j).getRealmId();
			params[j][2] = tmpAucs.get(j).getMinBid();
			params[j][3] = tmpAucs.get(j).getMinBidOwner();			
			params[j][4] = tmpAucs.get(j).getMinBuyout();
			params[j][5] = tmpAucs.get(j).getMinBuyoutOwner();
			params[j][6] = tmpAucs.get(j).getTotalQuantity();
			params[j][7] = tmpAucs.get(j).getLastModified();
			params[j][8] = tmpAucs.get(j).getPetSpeciesId();
			params[j][9] = tmpAucs.get(j).getPetLevel();
			params[j][10] = tmpAucs.get(j).getContext();
			params[j][11] = tmpAucs.get(j).getBonusList();			
		}
		run.batch(conn, "insert into " + tableName + " (itemId,realmId,minBid,minBidOwner,minBuyout,minBuyoutOwner,totalQuantity,lastModified,petSpeciesId,petLevel,context,bonusList) values(?,?,?,?,?,?,?,?,?,?,?,?)", params);
		conn.commit();
		tmpAucs.clear();
	}
	
	/*
	 * 删除指定表某段时间内的数
	 */
	public int removeHistoryForTableAndTime(String tableName, long startTime, long endTime) throws SQLException {
		return run.update("delete from " + tableName + " where lastModified>=? and lastModified<=?", startTime, endTime);
	}
	
	/*
	 * 导入最近拍卖数据到history表格
	 */
	public int copyLatestAuctionsToHistoryByRealmId(String tableName, int realmId) throws SQLException {
		return run.update("insert into " + tableName + " (itemId,realmId,minBid,minBidOwner,minBuyout,minBuyoutOwner,totalQuantity,lastModified,petSpeciesId,petLevel,context,bonusList) select itemId,realmId,minBid,minBidOwner,minBuyout,minBuyoutOwner,totalQuantity,lastModified,petSpeciesId,petLevel,context,bonusList from t_latest_auction where realmId=? and petBreedId=0", realmId);
	}
}
