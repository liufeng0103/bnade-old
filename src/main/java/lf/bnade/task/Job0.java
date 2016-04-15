package lf.bnade.task;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.dao.TableMaintainanceDao;
import lf.bnade.jmodel.JPet;
import lf.bnade.jmodel.JPets;
import lf.bnade.model.Item;
import lf.bnade.model.Pet;
import lf.bnade.model.Realm;
import lf.bnade.service.ItemService;
import lf.bnade.service.PetService;
import lf.bnade.service.RealmService;
import lf.bnade.util.BnadeUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/*
 * 数据库表维护
 */
public class Job0 {

	private static Logger logger = LoggerFactory.getLogger(Job0.class);

	public static void main(String[] args) {
		Job0 job = new Job0();
//		job.optimizeTables();
		job.job7();
	}

	/*
	 * 删除所有服务器的history表
	 */
	public void job0() {
		try {
			RealmService realmService = new RealmService();
			// 获取所有服务器列表
			List<Realm> realmList = realmService.getRealms();
			for (Realm realm : realmList) {
				realmService.dropHistoryTable(realm.getHistoryTableName());
				realmService.dropHistoryTable(realm.getPeriodHistoryTableName());
				realmService.dropHistoryTable(realm.getDailyHistoryTableName());
				logger.info("服务器：" + realm.getName() + " 表删除完成");
			}
			logger.info("服务器所有history表删除完成");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 初始化t_realm表
	 */
	public void job1() {
		try {
			RealmService realmService = new RealmService();
			if (realmService.isRealmTableEmpty()) {
				// 获取所有服务器列表
				List<String> realmList = BnadeUtils
						.fileToStringList("tfrealmlist.txt");
				for (int i = 0; i < realmList.size(); i++) {
					int tmp = i + 1; // 从1开始
					realmService.addRealm(new Realm(realmList.get(i),
							"t_auction_history_" + tmp,
							"t_auction_history_period_" + tmp,
							"t_auction_history_daily_" + tmp));
					logger.info("添加服务器{}到t_realm完成", realmList.get(i));
				}
				logger.info("表t_realm初始化完成");
			} else {
				logger.info("表t_realm不为空不进行初始化");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 各服务器表的创建
	 */
	public void job2() {
		try {
			RealmService realmService = new RealmService();
			// 获取所有服务器列表
			List<Realm> realmList = realmService.getRealms();
			for (Realm realm : realmList) {
				// System.out.print(realm.getName());
				realmService.createHistoryTableForRealm(realm
						.getHistoryTableName());
				realmService.createHistoryTableForRealm(realm
						.getPeriodHistoryTableName());
				realmService.createHistoryTableForRealm(realm
						.getDailyHistoryTableName());
				logger.info("服务器：" + realm.getName() + " 表创建完成");
			}
			logger.info("服务器所有表创建完成");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 所有history表给itemId, realmId, lastModified添加索引
	 */
	public void job3() {
		try {
			RealmService realmService = new RealmService();
			// 获取所有服务器列表
			List<Realm> realmList = realmService.getRealms();
			for (Realm realm : realmList) {
				realmService.addIndexForTable(realm.getHistoryTableName());
				logger.info("服务器：" + realm.getName() + realm.getHistoryTableName() + " 表索引创建完成");
			}
			logger.info("服务器所有表索引创建完成");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 所有period history表给itemId, realmId, lastModified添加索引
	 */
	public void job4() {
		try {
			RealmService realmService = new RealmService();
			// 获取所有服务器列表
			List<Realm> realmList = realmService.getRealms();
			for (Realm realm : realmList) {
				realmService
						.addIndexForTable(realm.getPeriodHistoryTableName());
				logger.info("服务器：" + realm.getName()
						+ realm.getPeriodHistoryTableName() + " 表索引创建完成");
			}
			logger.info("服务器所有表索引创建完成");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 初始化宠物表t_pet
	 */
	public void job5() {
		try {
			String petJson = BnadeUtils.fileToString("tfpet.json");
			Gson gson = new Gson();
			JPets pets = gson.fromJson(petJson, JPets.class);
			List<JPet> petList = pets.getPets();
			PetService petService = new PetService();
			for (JPet jpet : petList) {
				Pet pet = new Pet();
				pet.setId(jpet.getStats().getSpeciesId());
				pet.setName(jpet.getName());
				pet.setIcon(jpet.getIcon());

				petService.addPet(pet);

				System.out.println("添加" + pet.getId() + pet.getName()
						+ pet.getIcon());
			}
			System.out.print("t_pet表初始化完毕一共添加" + petList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 给各种history表添加列
	 */
	public void job6() {
		try {
			RealmService realmService = new RealmService();
			// 获取所有服务器列表
			List<Realm> realmList = realmService.getRealms();
			String operate = "ADD";
			String column = "context INT NOT NULL, ADD bonusList VARCHAR(20) NOT NULL";
			for (Realm realm : realmList) {
				realmService.alterTable(realm.getHistoryTableName(), operate, column);
				realmService.alterTable(realm.getPeriodHistoryTableName(), operate, column);
				realmService.alterTable(realm.getDailyHistoryTableName(), operate, column);
				logger.info("服务器：" + realm.getName() + realm.getId() + " 表列添加完成");
			}
			logger.info("服务器所有列添加完成");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 给所有itemLevel设置值
	 */
	public void job7() {
		try {
			Gson gson = new Gson();
			ItemService itemService = new ItemService();
			List<Item> items = itemService.getItemForLevel0();
			for(Item item : items) {
//				System.out.println(item.getJson());
				Item tmpItem = gson.fromJson(item.getJson(), Item.class);
				System.out.println(tmpItem.getId() + " " + tmpItem.getName() + " " + tmpItem.getItemClass());
				itemService.update(tmpItem);
//				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void optimizeTables() {
		try {
			TableMaintainanceDao tableDao = new TableMaintainanceDao();
			RealmService realmService = new RealmService();
			// 获取所有服务器列表
			List<Realm> realmList = realmService.getRealms();
			for (Realm realm : realmList) {
				tableDao.optimizeTable(realm.getHistoryTableName());
				logger.info("服务器：" + realm.getName() + realm.getId() + " 优化完成");
//				break;
			}
			logger.info("所有服务器优化完成");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
