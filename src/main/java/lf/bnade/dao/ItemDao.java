package lf.bnade.dao;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.model.Item;
import lf.bnade.model.ItemBonus;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class ItemDao extends BaseDao {
	
	/*
	 * 获取t_item表中所有物品ID
	 */
	public List<Integer> getDistinctItemIds() throws SQLException {
		return run.query("select id from t_item", new ColumnListHandler<Integer>());
	}
	
	/*
	 * 添加物品信息
	 */
	public void add(Item item) throws SQLException {
		run.update( "insert into t_item (id,description,name,icon,itemClass,itemSubClass,inventoryType,itemLevel,json) values(?,?,?,?,?,?,?,?,?)",
				item.getId(), item.getDescription(), item.getName(), item.getIcon(), item.getItemClass(), item.getItemSubClass(), item.getInventoryType(), item.getItemLevel(), item.getJson());
	}
	
	/*
	 * 添加item bonus信息
	 */
	public int add(ItemBonus itemBonus) throws SQLException {
		return run.update( "insert into t_item_bonus (itemId,context,bonusList) values(?,?,?)",
				itemBonus.getItemId(), itemBonus.getContext(), itemBonus.getBonusList());
	}
	
	/*
	 * 获取物品的item bonus信息
	 */
	public List<ItemBonus> getItemBonusById(int itemId) throws SQLException {
		return run.query("select itemId,context,bonusList from t_item_bonus where itemId=?", new BeanListHandler<ItemBonus>(ItemBonus.class), itemId);
	}
	
	
	/*
	 * 更新物品信息
	 */
	public int update(Item item) throws SQLException {
		return run.update( "update t_item set itemClass=?,itemSubClass=?,inventoryType=? where id=?",
				item.getItemClass(), item.getItemSubClass(), item.getInventoryType(), item.getId());
	}
	
	/*
	 * 根据物品名称模糊查询
	 */
	public List<Item> getItemsByFuzzyName(String name) throws SQLException {
		return run.query("select id,name,icon from mt_item where name like ?", new BeanListHandler<Item>(Item.class), "%"+name+"%");
	}
	
	/*
	 * 根据物品名获取物品信息
	 */
	public Item getItemByName(String name) throws SQLException {
		return run.query("select id,name,icon from mt_item where name=?", new BeanHandler<Item>(Item.class), name);
	}
	
	/*
	 * 根据物品ID获取物品信息
	 */
	public Item getItemById(int id) throws SQLException {
		return run.query("select id,name,icon from mt_item where id=?", new BeanHandler<Item>(Item.class), id);
	}
	
	/*
	 * 根据物品名获取物品信息,可能有多个
	 */
	public List<Item> getItemsByName(String name) throws SQLException {
		return run.query("select id,name,icon,itemLevel from mt_item where name=?", new BeanListHandler<Item>(Item.class), name);
	}
	
	/*
	 * 获取所有item bonus信息
	 */
	public List<ItemBonus> getItemBonus() throws SQLException {
		return run.query("select itemId,context,bonusList from t_item_bonus", new BeanListHandler<ItemBonus>(ItemBonus.class));
	}
	
	/*
	 * 获取所有t_latest_auction中的item bonus信息
	 */
	public List<ItemBonus> getAuctionItemBonus() throws SQLException {
		return run.query("select itemId,context,bonusList from t_latest_auction where context in (3,5,6,13) group by itemId,context,bonusList", new BeanListHandler<ItemBonus>(ItemBonus.class));
	}
	
	/*
	 * 获取物品等级为0的数据
	 */
	public List<Item> getItemForLevel0() throws SQLException {
		return run.query("select * from t_item where itemClass=0 and json!=''", new BeanListHandler<Item>(Item.class));
	}
	
	/*
	 * 获取item在内存表中的数量
	 */
	public Long getMItemCount() throws SQLException {
		return run.query("select count(*) from mt_item", new ScalarHandler<Long>());
	}

	/*
	 * 把t_item表中数据导入到mt_item内存表中
	 */
	public int initMItem() throws SQLException {
		return run.update("insert into mt_item (id,name,icon,itemLevel) select id,name,icon,itemLevel from t_item");
	}
	
	/*
	 * 清空mt_item表数据
	 */
	public int deleteMItem() throws SQLException {
		return run.update("truncate mt_item");
	}
}
