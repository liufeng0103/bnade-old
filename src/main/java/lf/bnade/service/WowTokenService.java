package lf.bnade.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lf.bnade.dao.WowTokenDao;
import lf.bnade.model.WowToken;

public class WowTokenService extends BaseService {
	
	private WowTokenDao dao;
	
	public WowTokenService () {
		dao = new WowTokenDao();
	}
	
	/*
	 * 添加徽章信息
	 */
	public int add(WowToken wowToken) throws SQLException {
		return dao.add(wowToken);
	}
	
	/*
	 * 获取全部时光徽章信息
	 */
	public List<List<Long>> getWowTokens() throws SQLException {
		List<WowToken> wowTokens = dao.getWowTokens();
		List<List<Long>> wts = new ArrayList<List<Long>>();
		for (WowToken ws : wowTokens) {
			List<Long> wta = new ArrayList<Long>();
			wta.add(0, ws.getUpdated());
			wta.add(1, new Long(ws.getBuy()));
			wts.add(wta);
		}
		return wts;
	}
	
	/*
	 * 获取时光徽章信息
	 */
	public WowToken getWowTokenByUpdated(long updated) throws SQLException {
		return dao.getWowTokenByUpdated(updated);
	}
}
