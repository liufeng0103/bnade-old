package lf.bnade.task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lf.bnade.model.Player;
import lf.bnade.model.Realm;
import lf.bnade.service.PlayerService;
import lf.bnade.service.RealmService;
import lf.bnade.util.BnadeUtils;
import lf.bnade.wow.WowClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task6 {
	
	private static Logger logger = LoggerFactory.getLogger(Task6.class);
	
	public static void main(String[] args) {
		Task6 t = new Task6();
		try {
			t.updatePlayerInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 更新玩家信息
	 */
	public void updatePlayerInfo() throws Exception{
		logger.info("开始更新玩家信息");
		WowClient client = new WowClient();
		PlayerService ps = new PlayerService();
		RealmService realmService = new RealmService();
		List<Realm> realms = realmService.getRealms();
		Map<String, Realm> realmMap = new HashMap<String, Realm>();
		for (Realm realmObj : realms) {
			realmMap.put(realmObj.getName(), realmObj);
		}
		ExecutorService pool = Executors.newFixedThreadPool(1);
		
		List<String> realmList = BnadeUtils.fileToStringList("realmlist.txt");			
		for (String realmName : realmList) {
			pool.execute(new Runnable(){
				@Override
				public void run() {
					try {
						Realm realm = realmMap.get(realmName);
						if (realm != null) {
							List<Player> players = ps.getPlayerByRealmId(realm.getId());
							List<Player> curPlayers = ps.getCurrentPlayersByRealmId(realm.getId());
							List<Player> lowLevelPlayers = ps.getLowLevelPlayersByRealmId(realm.getId());
							logger.info("服务器[{}]数据库玩家数{}, 最近拍卖行玩家数{}, 低等级玩家数{}", realmName, players.size(), curPlayers.size(), lowLevelPlayers.size());
							curPlayers.removeAll(players);
							curPlayers.removeAll(lowLevelPlayers);
							logger.info("服务器[{}]新玩家数{} 开始保存", realmName, curPlayers.size());
							for (Player p : curPlayers) {			 
								try {
									Player newPlayer = client.getPlayer(p.getRealm(), p.getName());
									newPlayer.setRealmId(p.getRealmId());
									ps.add(newPlayer);
									logger.info("服务器[{}]添加玩家{} {}",realmName, p.getRealm(), p.getName());
								} catch (FileNotFoundException e) {
									ps.addLowLevel(p);						
									logger.info("服务器[{}]找不到玩家(等级低于10级的获取不到){} {}", realmName, p.getRealm(), p.getName());
								} catch (IOException e) {
									if(e.getMessage().contains("HTTP response code: 500")) {					
										logger.error("服务器[{}]未知错误,玩家信息不可用{} {}", realmName, p.getRealm(), p.getName());
									} else if (e.getMessage().contains("HTTP response code: 403")) {
										logger.error("服务器[{}]玩家信息有错误{} {}", realmName, p.getRealm(), p.getName());
									} else {
										throw e;
									}
								} catch (SQLException e) {
									if(e.getMessage().contains("cannot be null")) {												
										logger.error("服务器[{}]获取玩家信息为null{} {}", realmName, p.getRealm(), p.getName());
									} else {
										throw e;
									}
								} 
//								catch (Exception e) {
//									logger.error("服务器[{}]异常{} {}", realmName, p.getRealm(), p.getName());
//								}
//								break;
							}
							logger.info("服务器[{}]保存完毕", realmName);
						} else {
							logger.error("服务器[{}]找不到Map", realmName);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
		}
		logger.info("所有玩家保存完毕");
	}
}
