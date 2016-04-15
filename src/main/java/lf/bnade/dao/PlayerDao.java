package lf.bnade.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lf.bnade.model.Player;
import lf.bnade.util.DSUtil;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class PlayerDao extends BaseDao {

	/*
	 * 添加玩家信息
	 */
	public int add(Player player) throws SQLException {
		return run.update("insert into t_player (name,realm,realmId,classV,race,gender,level,thumbnail,lastModified) values(?,?,?,?,?,?,?,?,?)",
				player.getName(), player.getRealm(), player.getRealmId(), player.getClassV(), player.getRace(),
				player.getGender(), player.getLevel(), player.getThumbnail(), player.getLastModified());
	}
	
	/*
	 * 保存新玩家到临时表
	 */
	public void addPlayers(List<Player> players) throws SQLException {
		int commitCount = 500;
		Connection conn = DSUtil.getDataSource().getConnection();
		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		List<Player> tmpPlayers = new ArrayList<Player>();
		int rowCount = 0;
		for(int i = 0; i < players.size(); i++) {
			tmpPlayers.add(players.get(i));
			if ((i + 1) % commitCount == 0) {				
				rowCount = commitCount;
				commitPlayers(rowCount, tmpPlayers, conn);
			} else if (i == players.size() -1) {
				rowCount = players.size() - (i + 1) / commitCount * commitCount;
				commitPlayers(rowCount, tmpPlayers, conn);
			}
		}		
		conn.setAutoCommit(autoCommit);
		DbUtils.close(conn);
	}
	
	private void commitPlayers(int count, List<Player> tmpPlayers, Connection conn) throws SQLException {
		Object[][] params = new Object[count][9];
		for (int j = 0; j < tmpPlayers.size(); j++) {
			params[j][0] = tmpPlayers.get(j).getName();
			params[j][1] = tmpPlayers.get(j).getRealm();
			params[j][2] = tmpPlayers.get(j).getRealmId();
			params[j][3] = tmpPlayers.get(j).getClassV();	
			params[j][4] = tmpPlayers.get(j).getRace();	
			params[j][5] = tmpPlayers.get(j).getGender();	
			params[j][6] = tmpPlayers.get(j).getLevel();	
			params[j][7] = tmpPlayers.get(j).getThumbnail();
			params[j][8] = tmpPlayers.get(j).getLastModified();	
		}
		run.batch(conn, "insert into t_player (name,realm,realmId,classV,race,gender,level,thumbnail,lastModified) values(?,?,?,?,?,?,?,?,?)", params);
		conn.commit();
		tmpPlayers.clear();
	}
	
	/*
	 * 获取所有玩家
	 */
	public List<Player> getPlayerByRealmId(int realmId) throws SQLException {
		return run.query("select realm,name,realmId from t_player where realmId=?", new BeanListHandler<Player>(Player.class), realmId);
	}
	
	/*
	 * 获取临时表中指定服务器的所有玩家
	 */
	public List<Player> getCurrentPlayersByRealmId(int realmId) throws SQLException {
		return run.query("select realm,name,realmId from t_current_player where realmId=?", new BeanListHandler<Player>(Player.class), realmId);
	}
	
	/*
	 * 获取临时表中的所有玩家
	 */
	public List<Player> getCurrentPlayers() throws SQLException {
		return run.query("select realm,name,realmId from t_current_player", new BeanListHandler<Player>(Player.class));
	}
	
	/*
	 * 保存新玩家到临时表
	 */
	public void addCurrentPlayers(List<Player> players) throws SQLException {
		int commitCount = 500;
		Connection conn = DSUtil.getDataSource().getConnection();
		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		List<Player> tmpPlayers = new ArrayList<Player>();
		int rowCount = 0;
		for(int i = 0; i < players.size(); i++) {
			tmpPlayers.add(players.get(i));
			if ((i + 1) % commitCount == 0) {				
				rowCount = commitCount;
				commitAuction(rowCount, tmpPlayers, conn);
			} else if (i == players.size() -1) {
				rowCount = players.size() - (i + 1) / commitCount * commitCount;
				commitAuction(rowCount, tmpPlayers, conn);
			}
		}		
		conn.setAutoCommit(autoCommit);
		DbUtils.close(conn);
	}
	
	private void commitAuction(int count, List<Player> tmpPlayers, Connection conn) throws SQLException {
		Object[][] params = new Object[count][3];
		for (int j = 0; j < tmpPlayers.size(); j++) {
			params[j][0] = tmpPlayers.get(j).getRealm();
			params[j][1] = tmpPlayers.get(j).getName();
			params[j][2] = tmpPlayers.get(j).getRealmId();		
		}
		run.batch(conn, "insert into t_current_player (realm,name,realmId) values(?,?,?)", params);
		conn.commit();
		tmpPlayers.clear();
	}
	
	/*
	 * 添加低等级玩家信息
	 */
	public int addLowLevel(Player player) throws SQLException {
		return run.update("insert into t_lowlevel_player (name,realm,realmId) values(?,?,?)",
				player.getName(), player.getRealm(), player.getRealmId());
	}
	
	/*
	 * 获取所有低等级玩家
	 */
	public List<Player> getLowLevelPlayersByRealmId(int realmId) throws SQLException {
		return run.query("select realm,name,realmId from t_lowlevel_player where realmId=?", new BeanListHandler<Player>(Player.class), realmId);
	}
}
