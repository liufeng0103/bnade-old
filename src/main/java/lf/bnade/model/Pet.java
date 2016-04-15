package lf.bnade.model;

public class Pet {
	// 宠物笼，在拍卖行数据中所有宠物的item id都是这个
	public static final int PET_ITEM_ID = 82800; 
	
	private int id;
	private String name;
	private String icon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
