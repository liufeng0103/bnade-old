package lf.bnade.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 各种排行处理
 */
public class TopService {

	private static Map<String, TopItem> topItems = new HashMap<String, TopItem>();
	
	public static void addItemName(String name) {
		TopItem topItem = topItems.get(name);
		if (topItem == null) {
			topItems.put(name, new TopItem(name, 1));
		} else {
			topItem.setCount(topItem.getCount() + 1);
		}		
	}
	
	public static List<TopItem> getTopItem(int number) {
		List<TopItem> list = new ArrayList<TopItem>(topItems.values());
		Collections.sort(list);  
		List<TopItem> topList = new ArrayList<TopItem>(number);
		if (list.size() < number) {
			number = list.size();
		}
		for(int i = 0; i < number; i++) {
			topList.add(list.get(i));
		}
		return topList;
	}
	
	public static void main(String[] args) {
		TopService.addItemName("军团勋章");
		TopService.addItemName("军团勋章");
		TopService.addItemName("军团勋章");
		TopService.addItemName("铁矿石");
		TopService.addItemName("丝绸");
		TopService.addItemName("丝绸");
		
		System.out.println(getTopItem(10));
	}
}

class TopItem implements Comparable<TopItem> {
	private String name;
	private int count;
	
	public TopItem() {}
	
	public TopItem(String name, int count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		return name + " " + count;
	}
	
	@Override
	public int compareTo(TopItem item) {		
		return -(this.getCount() - item.getCount());
	}

}
