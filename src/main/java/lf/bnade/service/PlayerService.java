package lf.bnade.service;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.dao.PlayerDao;
import lf.bnade.model.Player;

public class PlayerService {
	
	private PlayerDao dao;
	
	public PlayerService() {
		dao = new PlayerDao();
	}

	/*
	 * 添加玩家信息
	 */
	public int add(Player player) throws SQLException {
		return dao.add(player);
	}
	
	/*
	 * 保存新玩家到临时表
	 */
	public void addPlayers(List<Player> players) throws SQLException {
		dao.addPlayers(players);
	}
	
	/*
	 * 获取所有玩家
	 */
	public List<Player> getPlayerByRealmId(int realmId) throws SQLException {
		return dao.getPlayerByRealmId(realmId);
	}
	
	/*
	 * 获取临时表中指定服务器的所有玩家
	 */
	public List<Player> getCurrentPlayersByRealmId(int realmId) throws SQLException {
		return dao.getCurrentPlayersByRealmId(realmId);
	}
	
	/*
	 * 获取临时表中的所有玩家
	 */
	public List<Player> getCurrentPlayers() throws SQLException {
		return dao.getCurrentPlayers();
	}
	
	/*
	 * 保存新玩家到临时表
	 */
	public void addCurrentPlayers(List<Player> players) throws SQLException {
		dao.addCurrentPlayers(players);
	}
	
	/*
	 * 添加低等级玩家信息
	 */
	public int addLowLevel(Player player) throws SQLException {
		return dao.addLowLevel(player);
	}
	
	/*
	 * 获取所有低等级玩家
	 */
	public List<Player> getLowLevelPlayersByRealmId(int realmId) throws SQLException {
		return dao.getLowLevelPlayersByRealmId(realmId);
	}
}
