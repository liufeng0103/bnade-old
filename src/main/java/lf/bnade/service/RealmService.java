package lf.bnade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lf.bnade.dao.RealmDao;
import lf.bnade.model.Realm;

/*
 * 服务器信息相关操作
 */
public class RealmService {	
	
	private RealmDao realmDao;	
	private static Map<String, Integer> nameToIdMap;
	
	public RealmService() {
		realmDao = new RealmDao();		
	}

	/*
	 * 添加服务器信息到t_realm
	 */
	public int addRealm(Realm realm) throws SQLException {
		return realmDao.addRealm(realm);
	}
	
	/*
	 * 初始化t_realm用于判断表是否已经被初始化过了
	 */
	public boolean isRealmTableEmpty() throws SQLException {		
		if (realmDao.getRealmCount()  > 0) {
			return false;
		}
		return true;
	}
	
	/*
	 * 获取服务器列表，主要包括服务器ID，名字，以及各history表名
	 */
	public List<Realm> getRealms() throws SQLException {
		return realmDao.getRealms();
	}
	
	/*
	 * 通过服务器名获取服务器ID
	 */
	public int getIdByName(String realmName) throws SQLException {
		if (nameToIdMap == null) {	
			nameToIdMap = new HashMap<String, Integer>();
			for (Realm realm : realmDao.getRealms()) {
				nameToIdMap.put(realm.getName(), realm.getId());
			}
		}
		return nameToIdMap.get(realmName);		
	}

	/*
	 * 创建拍卖数据的history表
	 */
	public int createHistoryTableForRealm(String tableName) throws SQLException {
		return realmDao.createHistoryTableForName(tableName);
	}
	
	/*
	 * 给表添加索引
	 */
	public int addIndexForTable(String tableName) throws SQLException {
		return realmDao.addIndexForTable(tableName);
	}

	/*
	 * 修改表结构
	 */
	public int alterTable(String tableName, String operate, String column) throws SQLException {		
		return realmDao.alterTable(tableName, operate, column);
	}
	
	/*
	 * 添加job处理过的服务器名
	 */
	public int addJobRealm(String realm) throws SQLException {
		return realmDao.addJobRealm(realm);
	}
	
	/*
	 * 获取job处理过的所有服务器，防止多次调用job，对服务器重复操作
	 */
	public List<String> getJobRealms() throws SQLException {
		return realmDao.getJobRealms();
	}
	
	/*
	 * 删除某个表, job0测试时使用
	 */
	public int dropHistoryTable(String tableName) throws SQLException {
		return realmDao.dropTable(tableName);
	}
	
}
