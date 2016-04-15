package lf.bnade.task;

import java.io.FileNotFoundException;
import java.util.List;

import lf.bnade.dao.AuctionDao;
import lf.bnade.dao.ItemDao;
import lf.bnade.jmodel.JItem;
import lf.bnade.model.Item;
import lf.bnade.model.ItemBonus;
import lf.bnade.model.PetBreed;
import lf.bnade.model.PetStats;
import lf.bnade.service.ItemService;
import lf.bnade.service.PetService;
import lf.bnade.wow.WowClient;

/*
 * 各种更新数据库数据的task
 */
public class Task4 {

	public static void main(String[] args) throws Exception {
		Task4 t = new Task4();
//		t.addItemById(9367);
		t.updateItems();
		t.updateItemBounus();
		t.updatePetBreed();
		t.updatePetStats();

		System.out.println("==========================完毕============================");
	}
	
	/*
	 * 获取没有的item
	 */
	public void updateItems() throws Exception {
		WowClient client = new WowClient();
		// 获取所有的item id
		ItemDao itemDao = new ItemDao();
		List<Integer> itemIds =  itemDao.getDistinctItemIds();
		System.out.println("现有物品数" + itemIds.size());
		AuctionDao ad = new AuctionDao();
		List<Long> historyItemIds = ad.getItemIds();
		System.out.println("当前所有服务器物品数" + historyItemIds.size());
		historyItemIds.removeAll(itemIds);
		System.out.println("没有找到id的物品数" + historyItemIds.size());
		System.out.println(historyItemIds);		
		for (int i = 0; i < historyItemIds.size(); i++) {
			if (historyItemIds.get(i) == 82800) {
				System.out.println("不更新宠物笼id " + historyItemIds.get(i));
				continue;
			}						
			try {
				JItem jItem = client.getItem(Integer.valueOf(historyItemIds.get(i).toString()));
				Item item = new Item();
				item.setId(jItem.getId());
				item.setName(jItem.getName());
				item.setDescription(jItem.getDescription());
				item.setIcon(jItem.getIcon());
				item.setItemClass(jItem.getItemClass());
				item.setItemSubClass(jItem.getItemSubClass());
				item.setInventoryType(jItem.getInventoryType());
				item.setItemLevel(jItem.getItemLevel());
				System.out.println(item.getId() + "		" + item.getName() + " " + item.getItemLevel());
				item.setJson(jItem.getJson());
				itemDao.add(item);
//				break;
			} catch (FileNotFoundException e1) {
				System.out.println("找不到物品ID"+historyItemIds.get(i));
				e1.printStackTrace();
			}
		}
		itemDao.deleteMItem();
		itemDao.initMItem();
	}
	
	public void addItemById(int id) throws Exception {
		WowClient client = new WowClient();
		// 获取所有的item id
		ItemDao itemDao = new ItemDao();
		
		JItem jItem = client.getItem(id);
		Item item = new Item();
		item.setId(jItem.getId());
		item.setName(jItem.getName());
		item.setDescription(jItem.getDescription());
		item.setIcon(jItem.getIcon());
		item.setItemClass(jItem.getItemClass());
		item.setItemSubClass(jItem.getItemSubClass());
		item.setInventoryType(jItem.getInventoryType());
		item.setItemLevel(jItem.getItemLevel());
		System.out.println(item.getId() + "		" + item.getName() + " " + item.getItemLevel());
		item.setJson(jItem.getJson());
		itemDao.add(item);

		
	}
	
	/*
	 * 更新item bonus信息
	 */
	public void updateItemBounus() throws Exception {
		ItemService itemService = new ItemService();
		List<ItemBonus> dbIb = itemService.getItemBonus();
		System.out.println("当前数据库Item Bonus数量" + dbIb.size());
		List<ItemBonus> aucIb = itemService.getAuctionItemBonus();
		System.out.println("当前最新排卖行数据中的Item Bonus数量" + aucIb.size());
		aucIb.removeAll(dbIb);
		System.out.println("最新的Item Bonus数量" + aucIb.size());
		for (ItemBonus ib : aucIb) {
			itemService.add(ib);
		}
		System.out.println("Item Bonus信息更新完成");
	}
	
	/*
	 * 更新pet breed信息
	 */
	public void updatePetBreed() throws Exception {
		PetService petService = new PetService();
		List<PetBreed> dbPb = petService.getPetBreeds();
		System.out.println("当前数据库中的pet breed数量" + dbPb.size());
		List<PetBreed> aucPb = petService.getAuctionPetBreeds();
		System.out.println("当前最新排卖行数据中的pet breed数量" + aucPb.size());
		aucPb.removeAll(dbPb);
		System.out.println("最新的pet breed数量" + aucPb.size());
		for (PetBreed pb : aucPb) {
			petService.add(pb);
		}
		System.out.println("pet breed信息更新完成");
	}
	/*
	 * 更新pet stats信息
	 */
	public void updatePetStats() throws Exception {
		WowClient client = new WowClient();
		PetService petService = new PetService();
		List<PetBreed> dbPs = petService.getNewPetBreed();
		System.out.println("当前数据库中需要更新pet stats数量:" + dbPs.size());
		for (PetBreed pb : dbPs) {
			PetStats ps = client.getPetStats(pb.getPetSpeciesId(), pb.getPetBreedId());
			petService.add(ps);
			System.out.println("更新" + pb.getPetSpeciesId() + "	" + pb.getPetBreedId());
//			System.out.println(ps.getHealth() + " " + ps.getPower() + " " + ps.getSpeed());
//			break;
		}
		System.out.println("pet stats信息更新完成");
	}
}
