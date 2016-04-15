package lf.bnade.ws;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lf.bnade.jmodel.Result;
import lf.bnade.service.AuctionService;
import lf.bnade.service.RealmService;
import lf.bnade.service.WowTokenService;
import lf.bnade.util.RealmMap;
import lf.bnade.vo.ItemsQueryAuctionItem;
import lf.bnade.vo.PastAuctionItem;
import lf.bnade.vo.PastAuctionPet;
import lf.bnade.vo.RealmAuctionItem;
import lf.bnade.vo.RealmAuctionPet;

@Path("auction")
public class AuctionResource {

	private AuctionService auctionService;
	private WowTokenService wowTokenService;
	private RealmService realmService;

	public AuctionResource() {
		auctionService = new AuctionService();
		wowTokenService = new WowTokenService();
		realmService = new RealmService();
	}

	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	// @GET
	// @Path("/item/{itemName}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionItem(@PathParam("itemName") String itemName) {
		try {
			List<RealmAuctionItem> auctionItems = auctionService
					.getLatestItemsByName(itemName);
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到物品:" + itemName);
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	@GET
	@Path("/item/id/{itemId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionItemsById(@PathParam("itemId") int itemId,
			@QueryParam("bl") String bl) {
		try {
			List<RealmAuctionItem> auctionItems = auctionService
					.getLatestItemsById(itemId, bl);
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到数据");
			} else {
				List<List<Object>> result = new ArrayList<List<Object>>();
				for (RealmAuctionItem item : auctionItems) {
					List<Object> itemArr = new ArrayList<Object>();
					itemArr.add(0, item.getRealm());
					itemArr.add(1, item.getMinBuyout());
					itemArr.add(2, item.getMinBuyoutOwner());
					itemArr.add(3, item.getTotalQuantity());
					itemArr.add(4, item.getLastModified());
					itemArr.add(5, item.getTimeLeft());
					result.add(itemArr);
				}
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取服务器物品24小时内的数据
	 */
	// @GET
	// @Path("/past24/realm/{serverName}/item/{itemName}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Object getPast24ItemByRealm(@PathParam("itemName") String itemName,
			@PathParam("serverName") String serverName) {
		try {
			String realm = RealmMap.getConnectedRealm(serverName);
			List<PastAuctionItem> auctionItems = null;
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + serverName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				auctionItems = auctionService.getPast24ItemByRealm(itemName,
						realm);
			}
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到物品:" + itemName);
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 根据物品ID,获取服务器物品24小时内的数据
	 */
	@GET
	@Path("/past24/realm/{realmName}/item/id/{itemId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPast24ItemsByRealmAndItemId(
			@PathParam("realmName") String realmName,
			@PathParam("itemId") int itemId, @QueryParam("bl") String bl) {
		try {
			String realm = RealmMap.getConnectedRealm(realmName);
			List<PastAuctionItem> auctionItems = null;
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + realmName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				auctionItems = auctionService
						.getPast24ItemsByItemIdAndPastTime(itemId, realm, bl);
			}
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到数据");
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取服务器物品一周内的数据
	 */
	// @GET
	// @Path("/pastweek/realm/{serverName}/item/{itemName}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Object getPastWeekItemByRealm(
			@PathParam("itemName") String itemName,
			@PathParam("serverName") String serverName) {
		try {
			String realm = RealmMap.getConnectedRealm(serverName);
			List<PastAuctionItem> auctionItems = null;
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + serverName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				auctionItems = auctionService.getPastWeekItemByRealm(itemName,
						realm);
			}
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到物品:" + itemName);
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 根据物品ID，获取服务器物品一周内的数据
	 */
	// @GET
	// @Path("/pastweek/realm/{realmName}/item/id/{itemId}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Object getPastWeekItemsByRealmAndItemId(
			@PathParam("realmName") String realmName,
			@PathParam("itemId") int itemId, @QueryParam("bl") String bl) {
		try {
			String realm = RealmMap.getConnectedRealm(realmName);
			List<PastAuctionItem> auctionItems = null;
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + realmName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				auctionItems = auctionService.getPastWeekItemsByRealmAndItemId(
						itemId, realm, bl);
			}
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到数据");
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 根据物品ID，获取服务器物品历史数据
	 */
	@GET
	@Path("/history/realm/{realmName}/item/id/{itemId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemHistoryByRealmAndItemId(
			@PathParam("realmName") String realmName,
			@PathParam("itemId") int itemId, @QueryParam("bl") String bl) {
		try {
			String realm = RealmMap.getConnectedRealm(realmName);
			List<PastAuctionItem> auctionItems = null;
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + realmName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				auctionItems = auctionService.getItemHistoryByItemId(itemId,
						realm, bl);
				if (auctionItems == null || auctionItems.size() == 0) {
					return Result.FAILED(404, "找不到数据");
				} else {
					List<List<Object>> result = new ArrayList<List<Object>>();
					for (PastAuctionItem item : auctionItems) {
						List<Object> itemArr = new ArrayList<Object>();
						itemArr.add(0, item.getLastModified());
						itemArr.add(1, item.getMinBuyout());
						itemArr.add(2, item.getTotalQuantity());
						result.add(itemArr);
					}
					return result;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取宠物在所有服务器最近一次的数据
	 */
	// @GET
	// @Path("/pet/{petName}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionPets(@PathParam("petName") String petName) {
		try {
			List<RealmAuctionPet> auctionItems = auctionService
					.getLatestPetsByName(petName);
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到物品:" + petName);
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取宠物在所有服务器最近一次的数据
	 */
	@GET
	@Path("/pet/id/{id}/breed/{breed}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionPetsById(@PathParam("id") int id,
			@PathParam("breed") int breed) {
		try {
			return auctionService.getLatestPetsByIdAndBreed(id, breed);
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取服务器宠物24小时内的数据
	 */
	// @GET
	// @Path("/past24/realm/{serverName}/pet/{petName}/level/{level}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Object getPast24PetByRealm(@PathParam("petName") String petName,
			@PathParam("level") int level,
			@PathParam("serverName") String serverName) {
		try {
			String realm = RealmMap.getConnectedRealm(serverName);
			List<PastAuctionPet> auctionItems = null;
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + serverName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				auctionItems = auctionService.getPast24PetsByRealm(petName,
						level, realm);
			}
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到物品:" + petName);
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取服务器物品一周内的数据
	 */
	// @GET
	// @Path("/pastweek/realm/{serverName}/pet/{petName}/level/{level}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Object getPastWeekPetByRealm(@PathParam("petName") String petName,
			@PathParam("level") int level,
			@PathParam("serverName") String serverName) {
		try {
			String realm = RealmMap.getConnectedRealm(serverName);
			List<PastAuctionPet> auctionItems = null;
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + serverName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				auctionItems = auctionService.getPastWeekPetsByRealm(petName,
						level, realm);
			}
			if (auctionItems == null || auctionItems.size() == 0) {
				return Result.FAILED(404, "找不到物品:" + petName);
			}
			return auctionItems;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	@GET
	@Path("/realm/{realmName}/owner/{owner}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionItemsByRealmAndOwner(
			@PathParam("realmName") String realmName,
			@PathParam("owner") String owner) {
		try {
			String realm = RealmMap.getConnectedRealm(realmName);
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + realmName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				return auctionService.getItemsByRealmIdAndOwner(realmService.getIdByName(realm), owner);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取物品在所有服务器最近一次的数据
	 */
	@GET
	@Path("/realm/{realmName}/item/names/{names}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionItemsByRealmAndItemNames(@PathParam("realmName") String realmName, @PathParam("names") String names) {
		try {	
			if (realmName == null || "".equals(realmName) || names == null || "".equals(names)) {
				return Result.FAILED("服务器和物品列表不能为空");
			}
			String realm = RealmMap.getConnectedRealm(realmName);
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + realmName + " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				int count = 0;
				int maxQueryCount = 50;
				List<String> queryList = new ArrayList<String>();
				String[] nameArr = names.split("_");
				for (int i = 0; i < nameArr.length; i++) {
					String name = nameArr[i].trim();
					if (!"".equals(name)) {
						queryList.add(name);
						if (count >= maxQueryCount) {
							break;
						}
					}
				}
				List<ItemsQueryAuctionItem> result = auctionService.getLatestItemsByRealmAndNames(realm, (String[])queryList.toArray(new String[queryList.size()]));
				List<List<Object>> returnList = new ArrayList<List<Object>>();
				for (ItemsQueryAuctionItem item : result) {
					List<Object> returnItem = new ArrayList<Object>();
					returnItem.add(0, item.getName());
					returnItem.add(1, item.getMinBuyout());
					returnItem.add(2, item.getMinBuyoutOwner());
					returnItem.add(3, item.getTotalQuantity());
					returnItem.add(4, item.getLastModified());
					returnItem.add(5, item.getTimeLeft());
					returnList.add(returnItem);
				}
				return returnList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取所有服务器拍卖数量的排行
	 */
	@GET
	@Path("/quantity")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getRealmAuctionQuantities() {
		try {
			return auctionService.getRealmAuctionQuantities();
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取玩家拍卖数量排行
	 */
	@GET
	@Path("/top/aucquantity/realm/{realmName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getTopAucQuantityOwners(
			@PathParam("realmName") String realmName) {
		try {
			String realm = RealmMap.getConnectedRealm(realmName);
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + realmName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				return auctionService.getTopAucQuantityOwnersByRealm(realm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	/*
	 * 获取玩家拍卖总价值排行
	 */
	@GET
	@Path("/top/totalprice/realm/{realmName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getTopTotalPriceOwners(
			@PathParam("realmName") String realmName) {
		try {
			String realm = RealmMap.getConnectedRealm(realmName);
			if (realm == null) {
				return Result.FAILED("找不到服务器:" + realmName
						+ " 如果是以下2个服务器请输入:晴日峰-江苏 丽丽-四川");
			} else {
				return auctionService.getTopTotalPriceOwnersByRealm(realm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}

	// 时光徽章
	@GET
	@Path("/wowtokens")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getWowTokens() {
		try {
			return wowTokenService.getWowTokens();
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED("unknow reason");
		}
	}
}
