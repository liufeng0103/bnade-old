package lf.bnade.service;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.dao.ItemDao;
import lf.bnade.model.Item;
import lf.bnade.model.ItemBonus;

public class ItemService {
	
	private ItemDao itemDao;
	
	public ItemService() {
		itemDao =  new ItemDao();
	}
	
	/*
	 * 添加item bonus信息
	 */
	public int add(ItemBonus itemBonus) throws SQLException {
		return itemDao.add(itemBonus);
	}
	
	/*
	 * 获取物品的item bonus信息
	 */
	public List<ItemBonus> getItemBonusById(int itemId) throws SQLException {
		return itemDao.getItemBonusById(itemId);
	}
	
	/*
	 * 更新物品信息
	 */
	public int update(Item item) throws SQLException {
		return itemDao.update(item);
	}
	
	/*
	 * 根据物品名称模糊查询，数据来自内存表，每次关闭数据库需要重新导入数据到内存表
	 * 找不到返回大小为0的list
	 */
	public List<Item> getItemsByFuzzyName(String name) throws SQLException {
		return itemDao.getItemsByFuzzyName(name);
	}
	
	/*
	 * 根据物品名获取物品信息
	 */
	public Item getItemByName(String name) throws SQLException {
		return itemDao.getItemByName(name);
	}
	
	/*
	 * 根据物品ID获取物品信息
	 */
	public Item getItemById(int id) throws SQLException {
		return itemDao.getItemById(id);
	}
	
	/*
	 * 根据物品名获取物品信息,可能有多个
	 */
	public List<Item> getItemsByName(String name) throws SQLException {
		List<Item> items = itemDao.getItemsByName(name);
		if (items != null && items.size() == 1) {
			Item item = items.get(0);
			List<ItemBonus> itemBonus = itemDao.getItemBonusById(item.getId());
			item.setItemBonus(itemBonus);
		}
		return items ;
	}
	
	/*
	 * 获取所有物品信息
	 */
	public List<Item> getItemForLevel0() throws SQLException {		
		return itemDao.getItemForLevel0();
	}
	
	/*
	 * 获取所有item bonus信息
	 */
	public List<ItemBonus> getItemBonus() throws SQLException {
		return itemDao.getItemBonus();
	}
	
	/*
	 * 获取所有t_latest_auction中的item bonus信息
	 */
	public List<ItemBonus> getAuctionItemBonus() throws SQLException {
		return itemDao.getAuctionItemBonus();
	}
	
	/*
	 * 获取item在内存表中的数量,用于判断mt_item是否需要初始化
	 */
	public Long getMItemCount() throws SQLException {
		return itemDao.getMItemCount();
	}
	
	/*
	 * 把t_item表中数据导入到mt_item内存表中
	 */
	public int initMItem() throws SQLException {
		return itemDao.initMItem();
	}
		
	/*
	 * 清空mt_item表数据
	 */
	public int deleteMItem() throws SQLException {
		return itemDao.deleteMItem();
	}
}
