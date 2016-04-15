package lf.bnade.service;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.dao.AuctionDao;
import lf.bnade.jmodel.JAuction;
import lf.bnade.model.Auction;
import lf.bnade.model.AuctionFile;
import lf.bnade.util.TimeHelper;
import lf.bnade.vo.ItemsQueryAuctionItem;
import lf.bnade.vo.OwnerAuctionItem;
import lf.bnade.vo.PastAuctionItem;
import lf.bnade.vo.PastAuctionPet;
import lf.bnade.vo.RealmAuctionItem;
import lf.bnade.vo.RealmAuctionPet;
import lf.bnade.vo.RealmAuctionQuantity;
import lf.bnade.vo.TopOwner;

public class AuctionService extends BaseService {	
	
	private AuctionDao auctionDao = new AuctionDao();
	
	/*
	 * 添加auction file信息到数据库
	 */
	public int addAuctionFile(AuctionFile auctionFile) throws SQLException {
		return auctionDao.add(auctionFile);
	}
	
	/*
	 * 数据保存到各种t_latest_auction表, 每500提交一次
	 */
	public void addLatestAuctions(List<Auction> aucs) throws SQLException {	
		auctionDao.addLatestAuctions(aucs);
	}
	
	/*
	 * 更新auction file信息到数据库
	 */
	public int updateAuctionFile(AuctionFile auctionFile) throws SQLException {
		return auctionDao.updateAuctionFile(auctionFile);
	}

	/*
	 * 获取服务器的auction file信息
	 */
	public AuctionFile getAuctionFileByRealm(String realm) throws SQLException {
		return auctionDao.getAuctionFileByRealm(realm);
	}
	
	/*
	 * 获取所有服务器拍卖数量的排行
	 */
	public List<RealmAuctionQuantity> getRealmAuctionQuantities() throws SQLException {
		return auctionDao.getRealmAuctionQuantities();
	}
	/*
	 * 获取auction history表里24小时内的数据
	 */
	public List<PastAuctionItem> getPast24ItemByRealm(String itemName, String realm) throws SQLException {		
		return auctionDao.getItemsByPastTime(itemName, TimeHelper.DAY, getRealmMap().get(realm).getHistoryTableName());
	}
	
	/*
	 * 根据物品id获取某个表某段时间内指定物品的数据
	 */
	public List<PastAuctionItem> getPast24ItemsByItemIdAndPastTime(int itemId, String realm, String bonusList) throws SQLException {
		if(bonusList==null) {
			return auctionDao.getItemsByItemIdAndPastTime(itemId, TimeHelper.DAY, getRealmMap().get(realm).getHistoryTableName());
		}
		return auctionDao.getItemsByItemIdAndPastTime(itemId, TimeHelper.DAY, getRealmMap().get(realm).getHistoryTableName(), bonusList);
	}
	
	/*
	 * 获取auction history表里24小时内的宠物数据
	 */
	public List<PastAuctionPet> getPast24PetsByRealm(String petName, int petLevel, String realm) throws SQLException {		
		return auctionDao.getPetsByPastTime(petName, petLevel, TimeHelper.DAY, getRealmMap().get(realm).getHistoryTableName());
	}
	
	/*
	 * 获取auction history表里一周内的宠物数据
	 */
	public List<PastAuctionPet> getPastWeekPetsByRealm(String petName, int petLevel, String realm) throws SQLException {		
		return auctionDao.getPetsByPastTime(petName, petLevel, 8 * TimeHelper.DAY, getRealmMap().get(realm).getPeriodHistoryTableName());
	}
	
	/*
	 * 获取auction daily表一周内的数据
	 */
	public List<PastAuctionItem> getPastWeekItemByRealm(String itemName, String realm) throws SQLException {
		return auctionDao.getItemsByPastTime(itemName, 8 * TimeHelper.DAY, getRealmMap().get(realm).getPeriodHistoryTableName());
	}
	
	/*
	 * 根据物品ID，获取auction daily表一周内的数据
	 */
	public List<PastAuctionItem> getPastWeekItemsByRealmAndItemId(int itemId, String realm, String bonusList) throws SQLException {
		if(bonusList==null) {
			return auctionDao.getItemsByItemIdAndPastTime(itemId, 8 * TimeHelper.DAY, getRealmMap().get(realm).getPeriodHistoryTableName());
		}
		return auctionDao.getItemsByItemIdAndPastTime(itemId, 8 * TimeHelper.DAY, getRealmMap().get(realm).getPeriodHistoryTableName(), bonusList);
	}
	
	/*
	 * 根据物品ID，获取历史数据
	 */
	public List<PastAuctionItem> getItemHistoryByItemId(int itemId, String realm, String bonusList) throws SQLException {
		if(bonusList==null) {
			return auctionDao.getItemsByItemId(itemId, getRealmMap().get(realm).getPeriodHistoryTableName());
		}
		return auctionDao.getItemsByItemId(itemId, getRealmMap().get(realm).getPeriodHistoryTableName(), bonusList);
	}
	
	/*
	 * 获取物品在所有服务器的最新数据
	 */
	public List<RealmAuctionItem> getLatestItemsByName(String itemName) throws SQLException {
		return auctionDao.getLatestItemsByName(itemName);
	}
	
	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	public List<RealmAuctionItem> getLatestItemsById(int itemId) throws SQLException {		
		return auctionDao.getLatestItemsById(itemId);
	}
	
	/*
	 * 获取某个卖家拍卖的所有物品
	 */
	public List<OwnerAuctionItem> getLatestItemsByRealmAndOwner(String realm, String owner) throws SQLException {
		return auctionDao.getLatestItemsByRealmAndOwner(realm, owner);
	}
	
	/*
	 * 获取服务器物品列表的所有价格
	 */
	public List<ItemsQueryAuctionItem> getLatestItemsByRealmAndNames(String realm, String[] itemNames) throws SQLException {
		return auctionDao.getLatestItemsByRealmAndNames(realm, itemNames);
	}
	
	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	public List<RealmAuctionItem> getLatestItemsById(int itemId, String bonusList) throws SQLException {
		if(bonusList == null) {
			return auctionDao.getLatestItemsById(itemId);
		}
		return auctionDao.getLatestItemsById(itemId, bonusList);
	}
	
	/*
	 * 获取宠物在所有服务器的最新数据
	 */
	public List<RealmAuctionPet> getLatestPetsByName(String petName) throws SQLException {
		return auctionDao.getLatestPetsByName(petName);
	}
	
	/*
	 * 获取宠物在所有服务器最近一次的数据
	 */
	public List<RealmAuctionPet> getLatestPetsByIdAndBreed(int id, int breed) throws SQLException {
		return auctionDao.getLatestPetsByIdAndBreed(id, breed);
	}
	
	/*
	 * 获取玩家拍卖数量排行
	 */
	public List<TopOwner> getTopAucQuantityOwnersByRealm(String realm) throws SQLException {
		return auctionDao.getTopOwnersByRealmId(getRealmMap().get(realm).getId(), "aucQuantity", 50);
	}
	
	/*
	 * 获取玩家拍卖总价值排行
	 */
	public List<TopOwner> getTopTotalPriceOwnersByRealm(String realm) throws SQLException {
		return auctionDao.getTopOwnersByRealmId(getRealmMap().get(realm).getId(), "total", 50);
	}
	
	/*
	 * 删除t_latest_auction表中某个服务器的数据
	 */
	public int removeLatestAuctionsRealmId(int realmId) throws SQLException {
		return auctionDao.removeLatestAuctionsRealmId(realmId);
	}
	
	// --------------------------------拍卖行所有数据-------------------------------------------
	/*
	 * 数据保存到各种t_auction_house表, 每500提交一次
	 */
	public void addAuctions(List<JAuction> aucs, int realmId) throws SQLException {
		auctionDao.addAuctions(aucs, realmId);
	}
	
	/*
	 * 在t_auction_house表中删除某个服务器的数据
	 */
	public int removeAuctionsByRealmId(int realmId) throws SQLException {
		return auctionDao.removeAuctionsByRealmId(realmId);
	}
	

	/*
	 * 获取某个卖家拍卖的所有物品
	 */
	public List<OwnerAuctionItem> getItemsByRealmIdAndOwner(int realmId, String owner) throws SQLException {
		return auctionDao.getItemsByRealmIdAndOwner(realmId, owner);
	}
}
