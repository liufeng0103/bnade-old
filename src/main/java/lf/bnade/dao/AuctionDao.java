package lf.bnade.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lf.bnade.jmodel.JAuction;
import lf.bnade.model.Auction;
import lf.bnade.model.AuctionFile;
import lf.bnade.util.DSUtil;
import lf.bnade.vo.ItemsQueryAuctionItem;
import lf.bnade.vo.OwnerAuctionItem;
import lf.bnade.vo.PastAuctionItem;
import lf.bnade.vo.PastAuctionPet;
import lf.bnade.vo.RealmAuctionItem;
import lf.bnade.vo.RealmAuctionPet;
import lf.bnade.vo.RealmAuctionQuantity;
import lf.bnade.vo.TopOwner;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

public class AuctionDao extends BaseDao {
		
	/*
	 * 添加auction file信息到数据库
	 */
	public int add(AuctionFile file) throws SQLException {
		long curTime = System.currentTimeMillis();		
		return run.update("insert into t_auction_file (url,lastModified,realm,maxAucId,quantity,ownerQuantity,itemQuantity,createTime,lastUpdateTime) values(?,?,?,?,?,?,?,?,?)",
				file.getUrl(), file.getLastModified(), file.getRealm(), file.getMaxAucId(), file.getQuantity(), file.getOwnerQuantity(), file.getItemQuantity(), curTime, curTime);
	}
	
	/*
	 * 数据保存到各种t_latest_auction表, 每500提交一次
	 */
	public void addLatestAuctions(List<Auction> aucs) throws SQLException {		
			int commitCount = 500;
			Connection conn = DSUtil.getDataSource().getConnection();
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			List<Auction> tmpAucs = new ArrayList<Auction>();
			int rowCount = 0;
			for(int i = 0; i < aucs.size(); i++) {
				tmpAucs.add(aucs.get(i));
				if ((i + 1) % commitCount == 0) {				
					rowCount = commitCount;
					commitAuction(rowCount, tmpAucs, conn);
				} else if (i == aucs.size() -1) {
					rowCount = aucs.size() - (i + 1) / commitCount * commitCount;
					commitAuction(rowCount, tmpAucs, conn);
				}
			}		
			conn.setAutoCommit(autoCommit);
			DbUtils.close(conn);
		}
		
		private void commitAuction(int count, List<Auction> tmpAucs, Connection conn) throws SQLException {
			Object[][] params = new Object[count][14];
			for (int j = 0; j < tmpAucs.size(); j++) {
				params[j][0] = tmpAucs.get(j).getItemId();
				params[j][1] = tmpAucs.get(j).getRealmId();
				params[j][2] = tmpAucs.get(j).getMinBid();
				params[j][3] = tmpAucs.get(j).getMinBidOwner();			
				params[j][4] = tmpAucs.get(j).getMinBuyout();
				params[j][5] = tmpAucs.get(j).getMinBuyoutOwner();
				params[j][6] = tmpAucs.get(j).getTotalQuantity();
				params[j][7] = tmpAucs.get(j).getTimeLeft();
				params[j][8] = tmpAucs.get(j).getLastModified();
				params[j][9] = tmpAucs.get(j).getPetSpeciesId();
				params[j][10] = tmpAucs.get(j).getPetLevel();
				params[j][11] = tmpAucs.get(j).getPetBreedId();
				params[j][12] = tmpAucs.get(j).getContext();
				params[j][13] = tmpAucs.get(j).getBonusList();			
			}
			run.batch(conn, "insert into t_latest_auction (itemId,realmId,minBid,minBidOwner,minBuyout,minBuyoutOwner,totalQuantity,timeLeft,lastModified,petSpeciesId,petLevel,petBreedId,context,bonusList) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
			conn.commit();
			tmpAucs.clear();
		}
	
	
	/*
	 * 更新auction file信息到数据库
	 */
	public int updateAuctionFile(AuctionFile file) throws SQLException {
		long curTime = System.currentTimeMillis();		
		return run.update("update t_auction_file set url=?, maxAucId=?,quantity=?,ownerQuantity=?,itemQuantity=?, lastModified=?,lastUpdateTime=? where realm=?",
				file.getUrl(), file.getMaxAucId(), file.getQuantity(), file.getOwnerQuantity(), file.getItemQuantity(), file.getLastModified(), curTime, file.getRealm());
	}
	
	/*
	 * 获取服务器的auction file信息
	 */
	public AuctionFile getAuctionFileByRealm(String realm) throws SQLException {
		return run.query("Select id,url,lastModified,realm,maxAucId,createTime,lastUpdateTime,quantity,ownerQuantity,itemQuantity from t_auction_file where realm=?", 
				new BeanHandler<AuctionFile>(AuctionFile.class), realm);
	}	
	
	/*
	 * 获取所有服务器拍卖数量的排行
	 */
	public List<RealmAuctionQuantity> getRealmAuctionQuantities() throws SQLException {
		return run.query("select realm,quantity,ownerQuantity,itemQuantity,lastUpdateTime from t_auction_file order by quantity desc", 
				new BeanListHandler<RealmAuctionQuantity>(RealmAuctionQuantity.class));
	}	

	/*
	 * 获取某个表某段时间内指定物品的数据
	 */
	public List<PastAuctionItem> getItemsByPastTime(String itemName, long pastTime, String tableName) throws SQLException {
		return run.query("select minBuyout,totalQuantity,lastModified from " + tableName + " h left join mt_item i on i.id=h.itemId where i.name=? and lastModified>=? order by lastModified", 
				new BeanListHandler<PastAuctionItem>(PastAuctionItem.class), itemName, System.currentTimeMillis() - pastTime);
	}
	
	/*
	 * 根据物品id获取某个表某段时间内指定物品的数据
	 */
	public List<PastAuctionItem> getItemsByItemIdAndPastTime(int itemId, long pastTime, String tableName) throws SQLException {
		return run.query("select minBuyout,totalQuantity,lastModified from " + tableName + " where itemId=? and lastModified>=? order by lastModified", 
				new BeanListHandler<PastAuctionItem>(PastAuctionItem.class), itemId, System.currentTimeMillis() - pastTime);
	}
	
	/*
	 * 根据物品id获取所有历史记录
	 */
	public List<PastAuctionItem> getItemsByItemId(int itemId, String tableName) throws SQLException {
		return run.query("select minBuyout,totalQuantity,lastModified from " + tableName + " where itemId=?", 
				new BeanListHandler<PastAuctionItem>(PastAuctionItem.class), itemId);
	}
	/*
	 * 根据物品id获取所有历史记录
	 */
	public List<PastAuctionItem> getItemsByItemId(int itemId, String tableName, String bonusList) throws SQLException {
		return run.query("select minBuyout,totalQuantity,lastModified from " + tableName + " where itemId=? and bonusList=? order by lastModified",
				new BeanListHandler<PastAuctionItem>(PastAuctionItem.class), itemId, bonusList);
	}
	
	/*
	 * 根据物品id获取某个表某段时间内指定物品的数据
	 */
	public List<PastAuctionItem> getItemsByItemIdAndPastTime(int itemId, long pastTime, String tableName, String bonusList) throws SQLException {
		return run.query("select minBuyout,totalQuantity,lastModified from " + tableName + " where itemId=? and lastModified>=? and bonusList=? order by lastModified", 
				new BeanListHandler<PastAuctionItem>(PastAuctionItem.class), itemId, System.currentTimeMillis() - pastTime, bonusList);
	}
	
	/*
	 * 获取某个表某段时间内指定宠物的数据
	 */
	public List<PastAuctionPet> getPetsByPastTime(String petName, int petLevel, long pastTime, String tableName) throws SQLException {
		return run.query("select minBuyout,totalQuantity,lastModified from " + tableName + " h left join t_pet p on p.id=h.petSpeciesId where petLevel=? and lastModified>=? and p.name=? order by lastModified", 
				new BeanListHandler<PastAuctionPet>(PastAuctionPet.class), petLevel, System.currentTimeMillis() - pastTime, petName);
	}
	
	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	public List<RealmAuctionItem> getLatestItemsByName(String itemName) throws SQLException {
		return run.query("select r.name as realm,minBuyout,minBuyoutOwner,totalQuantity,lastModified from t_latest_auction h left join mt_item i on i.id=h.itemId left join t_realm r on r.id=h.realmId where i.name=?", 
				new BeanListHandler<RealmAuctionItem>(RealmAuctionItem.class), itemName);
	}
	
	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	public List<RealmAuctionItem> getLatestItemsById(int itemId) throws SQLException {
		return run.query("select r.name as realm,minBuyout,minBuyoutOwner,totalQuantity,timeLeft,lastModified from t_latest_auction h left join t_realm r on r.id=h.realmId where h.itemId=?", 
				new BeanListHandler<RealmAuctionItem>(RealmAuctionItem.class), itemId);
	}
	
	/*
	 * 获取某个卖家拍卖的所有物品
	 */
	public List<OwnerAuctionItem> getLatestItemsByRealmAndOwner(String realm, String owner) throws SQLException {
		return run.query("select mt.name,minBuyout,lastModified from t_latest_auction h left join t_realm r on r.id=h.realmId left join mt_item mt on mt.id=h.itemId where r.name=? and h.minBuyoutOwner=?", 
				new BeanListHandler<OwnerAuctionItem>(OwnerAuctionItem.class), realm, owner);
	}
	
	/*
	 * 获取服务器物品列表的所有价格
	 */
	public List<ItemsQueryAuctionItem> getLatestItemsByRealmAndNames(String realm, String[] itemNames) throws SQLException {
		StringBuffer sb = new StringBuffer();
		Object[] params = new Object[itemNames.length + 1];
		params[0] = realm;
		for (int i = 0; i < itemNames.length; i++) {
			params[i + 1] = itemNames[i];
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append("?");
		}
		sb.insert(0, "select mt.name,minBuyout,minBuyoutOwner,totalQuantity,timeLeft,lastModified from t_latest_auction h left join t_realm r on r.id=h.realmId left join mt_item mt on mt.id=h.itemId where r.name=? and mt.name in(");
		sb.append(")");
		return run.query(sb.toString(), 
				new BeanListHandler<ItemsQueryAuctionItem>(ItemsQueryAuctionItem.class), params);
	}
	
	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	public List<RealmAuctionItem> getLatestItemsById(int itemId, String bonusList) throws SQLException {
		return run.query("select r.name as realm,minBuyout,minBuyoutOwner,totalQuantity,timeLeft,lastModified from t_latest_auction h left join t_realm r on r.id=h.realmId where h.itemId=? and bonusList=?", 
				new BeanListHandler<RealmAuctionItem>(RealmAuctionItem.class), itemId, bonusList);
	}
	
	/*
	 * 获取宠物在所有服务器最近一次的数据
	 */
	public List<RealmAuctionPet> getLatestPetsByName(String petName) throws SQLException {
		return run.query("select r.name as realm,minBuyout,minBuyoutOwner,totalQuantity,lastModified,petLevel from t_latest_auction h left join t_pet p on h.petSpeciesId=p.id left join t_realm r on r.id=h.realmId where p.name=?", 
				new BeanListHandler<RealmAuctionPet>(RealmAuctionPet.class), petName);
	}
	
	/*
	 * 获取宠物在所有服务器最近一次的数据
	 */
	public List<RealmAuctionPet> getLatestPetsByIdAndBreed(int id, int breed) throws SQLException {
		return run.query("select r.name as realm,minBuyout,minBuyoutOwner,totalQuantity,lastModified,petLevel from t_latest_auction h left join t_pet p on h.petSpeciesId=p.id left join t_realm r on r.id=h.realmId where p.id=? and h.petBreedId=?", 
				new BeanListHandler<RealmAuctionPet>(RealmAuctionPet.class), id, breed);
	}
	
	/*
	 * 获取玩家拍卖信息排行
	 */
	public List<TopOwner> getTopOwnersByRealmId(int realmId, String orderAttr, int limitQuantity) throws SQLException {
		return run.query("select h.aucQuantity,h.owner,h.total from (select count(minBuyoutOwner) as aucQuantity,minBuyoutOwner as owner,sum(minBuyout) as total from t_latest_auction where realmId=? group by minBuyoutOwner) h order by h."+orderAttr+" desc limit " + limitQuantity, 
				new BeanListHandler<TopOwner>(TopOwner.class), realmId);
	}
	
	/*
	 * 获取t_latest_auction表中所有物品ID, 用于查看没有物品信息的ID
	 */
	public List<Long> getItemIds() throws SQLException {
		return run.query("select distinct itemId from t_latest_auction", 
				new ColumnListHandler<Long>());
	}
	
	/*
	 * 删除t_latest_auction表中某个服务器的数据
	 */
	public int removeLatestAuctionsRealmId(int realmId) throws SQLException {
		return run.update("delete from t_latest_auction where realmId=?", realmId);
	}
	
	// --------------------------------拍卖行所有数据-------------------------------------------
	/*
	 * 数据保存到各种t_auction_house表, 每500提交一次
	 */
	public void addAuctions(List<JAuction> aucs, int realmId)
			throws SQLException {
		int commitCount = 500;
		Connection conn = DSUtil.getDataSource().getConnection();
		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		List<JAuction> tmpAucs = new ArrayList<JAuction>();
		int rowCount = 0;
		for (int i = 0; i < aucs.size(); i++) {
			tmpAucs.add(aucs.get(i));
			if ((i + 1) % commitCount == 0) {
				rowCount = commitCount;
				commitAuctions(rowCount, tmpAucs, conn, realmId);
			} else if (i == aucs.size() - 1) {
				rowCount = aucs.size() - (i + 1) / commitCount * commitCount;
				commitAuctions(rowCount, tmpAucs, conn, realmId);
			}
		}
		conn.setAutoCommit(autoCommit);
		DbUtils.close(conn);
	}

	private void commitAuctions(int count, List<JAuction> tmpAucs,
			Connection conn, int realmId)
			throws SQLException {
		Object[][] params = new Object[count][14];
		for (int j = 0; j < tmpAucs.size(); j++) {
			params[j][0] = realmId;
			params[j][1] = tmpAucs.get(j).getAuc();
			params[j][2] = tmpAucs.get(j).getItem();
			params[j][3] = tmpAucs.get(j).getOwner();
			params[j][4] = tmpAucs.get(j).getOwnerRealm();
			params[j][5] = tmpAucs.get(j).getBid();
			params[j][6] = tmpAucs.get(j).getBuyout();
			params[j][7] = tmpAucs.get(j).getQuantity();
			params[j][8] = tmpAucs.get(j).getTimeLeft();
			params[j][9] = tmpAucs.get(j).getPetSpeciesId();
			params[j][10] = tmpAucs.get(j).getPetLevel();
			params[j][11] = tmpAucs.get(j).getPetBreedId();
			params[j][12] = tmpAucs.get(j).getContext();
			params[j][13] = tmpAucs.get(j).getBonusLists();			
		}
		run.batch(
				conn,
				"insert into t_auction_house_" + realmId + " (realmId,auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusList) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				params);
		conn.commit();
		tmpAucs.clear();
	}
		
	/*
	 * 在t_auction_house表中删除某个服务器的数据
	 */
	public int removeAuctionsByRealmId(int realmId) throws SQLException {
		return run.update("truncate t_auction_house_" + realmId);
	}	
	
	/*
	 * 获取某个卖家拍卖的所有物品
	 */
	public List<OwnerAuctionItem> getItemsByRealmIdAndOwner(int realmId, String owner) throws SQLException {
		return run.query("select item,name,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,bonusList from t_auction_house_"+realmId
				+" ah left join mt_item mt on mt.id=ah.item where ah.realmId=? and ah.owner=? and item <> 82800 union all select item,name,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,bonusList from t_auction_house_"+realmId
				+" ah left join t_pet p on p.id=ah.petSpeciesId where realmId=? and owner=? and item = 82800", 
				new BeanListHandler<OwnerAuctionItem>(OwnerAuctionItem.class), realmId, owner, realmId, owner);
	}
}
