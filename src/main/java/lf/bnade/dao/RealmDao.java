package lf.bnade.dao;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.model.Realm;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class RealmDao extends BaseDao {
	
	/*
	 * 添加服务器信息到t_realm
	 */
	public int addRealm(Realm realm) throws SQLException {
		return run.update("insert into t_realm (name, historyTableName, periodHistoryTableName, dailyHistoryTableName) values(?,?,?,?)"
				, realm.getName(), realm.getHistoryTableName(), realm.getPeriodHistoryTableName(), realm.getDailyHistoryTableName());
	}
	
	/*
	 * t_realm表记录数量
	 */
	public long getRealmCount() throws SQLException {
		return run.query("select count(*) from t_realm", new ScalarHandler<Long>());
	}
	
	/*
	 * 获取服务器列表
	 */
	public List<Realm> getRealms() throws SQLException {		
		return run.query("select * from t_realm", new BeanListHandler<Realm>(Realm.class));
	}
	
	/*
	 * 创建拍卖数据的history表
	 */
	public int createHistoryTableForName(String tableName) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE IF NOT EXISTS ");
		sb.append(tableName);
		sb.append("(id INT UNSIGNED NOT NULL AUTO_INCREMENT,");
		sb.append("itemId INT UNSIGNED NOT NULL,");
		sb.append("realmId INT UNSIGNED NOT NULL,");
		sb.append("minBid BIGINT NOT NULL,");
		sb.append("minBidOwner VARCHAR(12) NOT NULL,");
		sb.append("minBuyout BIGINT NOT NULL,");
		sb.append("minBuyoutOwner VARCHAR(12) NOT NULL,");
		sb.append("totalQuantity INT NOT NULL,");
		sb.append("lastModified BIGINT UNSIGNED NOT NULL,");
		sb.append("petSpeciesId INT NOT NULL,");
		sb.append("petLevel INT NOT NULL,");
		sb.append("PRIMARY KEY(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		return run.update(sb.toString());
	}
	
	/*
	 * 给表添加索引
	 */
	public int addIndexForTable(String tableName) throws SQLException {		
//		ALTER TABLE t_auction_history_xxx ADD INDEX(lastModified); -- task2获取服务器指定时间内数据时使用，task2清理服务器指定时间内数据时使用
//		ALTER TABLE t_auction_history_xxx ADD INDEX(itemId,lastModified); -- 获取24小时或一周内数据时用
//		ALTER TABLE t_auction_history_xxx ADD INDEX(petSpeciesId,lastModified);
		
		run.update("ALTER TABLE " + tableName + " ADD INDEX(lastModified)");
		run.update("ALTER TABLE " + tableName + " ADD INDEX(itemId,lastModified)");
		run.update("ALTER TABLE " + tableName + " ADD INDEX(petSpeciesId,lastModified)");
		return 1;
//		return run.update("ALTER TABLE " + tableName + " ADD INDEX(lastModified)");
	}
	
	/*
	 * 修改表结构
	 */
	public int alterTable(String tableName, String operate, String column) throws SQLException {		
		return run.update("ALTER TABLE " + tableName + " " + operate + " " + column);
	}
	
	/*
	 * 添加job处理过的服务器名
	 */
	public int addJobRealm(String realm) throws SQLException {
		return run.update("insert into t_job_control values(?)", realm);
	}
	
	/*
	 * 获取job处理过的所有服务器
	 */
	public List<String> getJobRealms() throws SQLException {
		return run.query("select * from t_job_control", new ColumnListHandler<String>());
	}
}
