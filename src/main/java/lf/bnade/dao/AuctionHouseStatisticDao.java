package lf.bnade.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lf.bnade.model.AuctionHouseStatistic;
import lf.bnade.util.DSUtil;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class AuctionHouseStatisticDao extends BaseDao {

	/*
	 * 获取服务器拍卖数量和玩家数量统计结果
	 */
	public List<AuctionHouseStatistic> getAuctionHouseStatistics() throws SQLException {
		return run.query("select 0 as realmId,count(r.id) as realmCount,sum(af.quantity) as auctionQuantity,sum(af.ownerQuantity) as ownerQuantity from t_auction_file af join t_realm r on af.realm = r.name UNION ALL select r.id as realmId,1 as realmCount,af.quantity as auctionQuantity,af.ownerQuantity as ownerQuantity from t_auction_file af join t_realm r on af.realm = r.name", 
				new BeanListHandler<AuctionHouseStatistic>(AuctionHouseStatistic.class));
	}
	
	/*
	 * 保存服务器拍卖数量和玩家数量统计结果到历史表
	 */
	public void add(List<AuctionHouseStatistic> list) throws SQLException {		
		int commitCount = 500;
		Connection conn = DSUtil.getDataSource().getConnection();
		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		List<AuctionHouseStatistic> tmpList = new ArrayList<AuctionHouseStatistic>();
		int rowCount = 0;
		long currentTime = System.currentTimeMillis();
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setUpdated(currentTime);
			tmpList.add(list.get(i));
			if ((i + 1) % commitCount == 0) {				
				rowCount = commitCount;
				commitList(rowCount, tmpList, conn);
				tmpList.clear();
			} else if (i == list.size() -1) {
				rowCount = list.size() - (i + 1) / commitCount * commitCount;
				commitList(rowCount, tmpList, conn);
				tmpList.clear();
			}
		}		
		conn.setAutoCommit(autoCommit);
		DbUtils.close(conn);
	}
		
	private void commitList(int count, List<AuctionHouseStatistic> tmpList, Connection conn) throws SQLException {
		Object[][] params = new Object[count][5];
		for (int j = 0; j < tmpList.size(); j++) {
			params[j][0] = tmpList.get(j).getRealmId();
			params[j][1] = tmpList.get(j).getRealmCount();
			params[j][2] = tmpList.get(j).getAuctionQuantity();
			params[j][3] = tmpList.get(j).getOwnerQuantity();			
			params[j][4] = tmpList.get(j).getUpdated();				
		}
		run.batch(conn, "insert into t_auction_house_statistic (realmId,realmCount,auctionQuantity,ownerQuantity,updated) values(?,?,?,?,?)", params);
		conn.commit();
	}
}
