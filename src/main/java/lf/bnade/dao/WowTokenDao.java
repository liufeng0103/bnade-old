package lf.bnade.dao;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.model.WowToken;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class WowTokenDao extends BaseDao {

	/*
	 * 添加时光徽章信息
	 */
	public int add(WowToken wowToken) throws SQLException {
		return run.update("insert into t_wowtoken (buy,updated) values(?,?)", wowToken.getBuy(), wowToken.getUpdated());
	}
	
	/*
	 * 获取全部时光徽章信息
	 */
	public List<WowToken> getWowTokens() throws SQLException {
		return run.query("select buy,updated from t_wowtoken", new BeanListHandler<WowToken>(WowToken.class));
	}
	
	/*
	 * 获取时光徽章信息
	 */
	public WowToken getWowTokenByUpdated(long updated) throws SQLException {
		return run.query("select buy,updated from t_wowtoken where updated=?", new BeanHandler<WowToken>(WowToken.class), updated);
	}
}
