package lf.bnade.model;

public class ItemBonus {
	private int itemId;
	private int context;
	private String bonusList;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public String getBonusList() {
		return bonusList;
	}

	public void setBonusList(String bonusList) {
		this.bonusList = bonusList;
	}

	@Override
	public boolean equals(Object obj) {
		ItemBonus ib = (ItemBonus)obj;
		return this.itemId == ib.getItemId() && this.context == ib.getContext() && this.bonusList.equals(ib.getBonusList());
	}
}
