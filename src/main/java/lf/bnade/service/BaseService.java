package lf.bnade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import lf.bnade.dao.RealmDao;
import lf.bnade.model.Realm;

public class BaseService {
	
	private RealmDao realmDao;
	private Map<String, Realm> realmMap;
	
	public BaseService() {
		realmDao = new RealmDao();
	}
	
	/*
	 * 获取服务器名和服务器相信的map,以后需要做成单例的
	 */
	public Map<String, Realm> getRealmMap() throws SQLException {
		if (realmMap == null) {
			realmMap = new HashMap<String, Realm>();
			for (Realm realmObj : realmDao.getRealms()) {
				realmMap.put(realmObj.getName(), realmObj);
			}
		}		
		return realmMap;
	}
}
