package lf.bnade.ws;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lf.bnade.jmodel.Result;
import lf.bnade.model.Item;
import lf.bnade.service.ItemService;
import lf.bnade.service.TopService;
import lf.bnade.util.BnadeUtils;



@Path("item")
public class ItemResource {
	
	private ItemService itemService;
	
	public ItemResource() {
		itemService = new ItemService();
	}

	/*
	 * 物品名称的模糊查询
	 */
	@GET
	@Path("/fuzzy/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemsByFuzzyName(@PathParam("name")String name) {
		if (name == null || "".equals(name)) {
			return Result.FAILED("物品名不能为空");
		}
		try {
			return itemService.getItemsByFuzzyName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED(e.getMessage());
		}		
	}
	
	/*
	 * 物品名称查询物品
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public Object getItemByName(@PathParam("name")String name) {
		if (name == null || "".equals(name)) {
			return Result.FAILED("物品名不能为空");
		}
		try {
			Item item = itemService.getItemByName(name);
			if(item != null) {
				String itemHtml = BnadeUtils.urlToString("https://www.battlenet.com.cn/wow/zh/item/" + item.getId() + "/tooltip");
//				String itemHtml = BnadeUtils.urlToString("http://www.battlenet.com.cn/wow/zh/spell/" + item.getSpellId() + "/tooltip");
				return itemHtml.replaceAll("href=\"[^\"]*\"", "href=\"\"");
			}
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.FAILED(e.getMessage());
		}		
	}
	
	/*
	 * 物品ID查询物品
	 */
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Object getItemById(@PathParam("id")int id,  @QueryParam("bl") String bl) {
		if (id < 1) {
			return "ID为负数";
		}
		try {
			String url = "https://www.battlenet.com.cn/wow/zh/item/" + id + "/tooltip";
			if(bl != null){
				url+="?u=529&bl=" + bl;
			}
			String itemHtml = BnadeUtils.urlToString(url);
//				String itemHtml = BnadeUtils.urlToString("http://www.battlenet.com.cn/wow/zh/spell/" + item.getSpellId() + "/tooltip");
			return itemHtml.replaceAll("href=\"[^\"]*\"", "href=\"\"");			
		} catch (Exception e) {
			e.printStackTrace();
			return "查询出错" + e.getMessage();
		}		
	}
	
	/*
	 * 物品ID查询物品
	 */
	@GET
	@Path("/id2/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemById(@PathParam("id")int id) {
		if (id < 1) {
			return Result.FAILED("id不正确");
		}
		try {
			return itemService.getItemById(id);	
		} catch (Exception e) {
			e.printStackTrace();
			return Result.FAILED(e.getMessage());
		}		
	}
	
	/*
	 * 物品名称查询物品
	 */
	@GET
	@Path("/list/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemsByName(@PathParam("name")String name) {
		if (name == null || "".equals(name)) {
			return Result.FAILED("物品名不能为空");
		}
		try {
			List<Item> items = itemService.getItemsByName(name);
			if(items.size() > 0) {
				TopService.addItemName(name);
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED(e.getMessage());
		} 	
	}
	
	/*
	 * 热门物品，根据用户搜索排行
	 */
	@GET
	@Path("/top10")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getTopItems() {
		return TopService.getTopItem(10);	
	}

}
