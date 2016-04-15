package lf.bnade.service;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.model.Item;

public class ItemServiceTest {

	public static void main(String[] args) throws SQLException {
		long start = System.currentTimeMillis();
		ItemService i = new ItemService();
		List<Item> items = i.getItemsByName("动燃之");
		System.out.println(items==null);
		for(Item item : items){
			System.out.println(item.getId() + item.getName() + item.getIcon());
		}
		System.out.println(System.currentTimeMillis()-start);
	}

}
