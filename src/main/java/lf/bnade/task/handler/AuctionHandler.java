package lf.bnade.task.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lf.bnade.jmodel.JAuction;
import lf.bnade.model.Auction;
import lf.bnade.model.Pet;
import lf.bnade.model.Player;

/*
 * 对排行数据按物品分类处理，主要统计最低一口价
 */
public class AuctionHandler {
	
	private List<Auction> auctions;
	private Map<Integer, Auction> itemAuctions;
	private Map<String, Auction> multiTypeItemAuctions;
	private Map<String, Auction> pets;
	private int maxAuctionId = 0;
	private Set<Player> ownerSet;

	public AuctionHandler() {
		auctions = new ArrayList<Auction>();
		itemAuctions = new HashMap<Integer, Auction>();
		multiTypeItemAuctions = new HashMap<String, Auction>();
		pets = new HashMap<String, Auction>();
		ownerSet = new HashSet<Player>();
	}

	public void add(JAuction auction, int realmId, long lastModified) {
		int itemId = auction.getItem();
		long bid = auction.getBid();
		long buyout = auction.getBuyout();
		int quantity = auction.getQuantity();
		String timeLeft = auction.getTimeLeft();
		String owner = auction.getOwner();
		if (maxAuctionId < auction.getAuc()) {
			maxAuctionId = auction.getAuc();
		}
		Player player = new Player();
		player.setName(owner);
		player.setRealmId(realmId);
		player.setRealm(auction.getOwnerRealm());		
		ownerSet.add(player);
		if (buyout != 0) {
			if (auction.getItem() == Pet.PET_ITEM_ID) {
				String petKey = auction.getPetSpeciesId() + " " + auction.getPetBreedId();
				Auction top = pets.get(petKey);
				if (top == null) {
					pets.put(petKey,
							new Auction(itemId, realmId, bid / quantity, owner,
									buyout / quantity, owner, quantity, timeLeft,
									lastModified, auction.getPetSpeciesId(),
									auction.getPetLevel(), auction.getPetBreedId(), auction.getContext(), auction.getBonusLists()));
				} else {
					// 竞价小于总价二分之一的忽略
					if ((bid / quantity) / 2 < (buyout / quantity) && (bid / quantity) < top.getMinBid()) {
						top.setMinBid(bid / quantity);
						top.setMinBidOwner(owner);
					}
					if (top.getMinBuyout() == 0 || buyout != 0 && (buyout / quantity) < top.getMinBuyout()) {
						top.setMinBuyout(buyout / quantity);
						top.setMinBuyoutOwner(owner);
						top.setPetLevel(auction.getPetLevel());
						top.setTimeLeft(timeLeft);
					}
					top.setTotalQuantity(top.getTotalQuantity() + quantity);
				}
			} else if(!"".equals(auction.getBonusLists()) && auction.getContext() != 0) {			
				String itemWithBonusKey = itemId+""+auction.getContext()+auction.getBonusLists();
				Auction top = multiTypeItemAuctions.get(itemWithBonusKey);
				if (top == null) {
					multiTypeItemAuctions.put(itemWithBonusKey,
							new Auction(itemId, realmId, bid / quantity, owner,
									buyout / quantity, owner, quantity, timeLeft,
									lastModified, auction.getPetSpeciesId(),
									auction.getPetLevel(), auction.getPetBreedId(), auction.getContext(), auction.getBonusLists()));
				} else {
					// 竞价小于总价二分之一的忽略
					if ((bid / quantity) / 2 < (buyout / quantity)
							&& (bid / quantity) < top.getMinBid()) {
						top.setMinBid(bid / quantity);
						top.setMinBidOwner(owner);
					}
					if (top.getMinBuyout() == 0 || buyout != 0
							&& (buyout / quantity) < top.getMinBuyout()) {
						top.setMinBuyout(buyout / quantity);
						top.setMinBuyoutOwner(owner);
						top.setTimeLeft(timeLeft);
					}
					top.setTotalQuantity(top.getTotalQuantity() + quantity);
				}
			} else {
				Auction	top = itemAuctions.get(itemId);
				if (top == null) {
					itemAuctions.put(itemId,
							new Auction(itemId, realmId, bid / quantity, owner,
									buyout / quantity, owner, quantity, timeLeft,
									lastModified, auction.getPetSpeciesId(),
									auction.getPetLevel(), auction.getPetBreedId(),  auction.getContext(), auction.getBonusLists()));
				} else {
					// 竞价小于总价二分之一的忽略
					if ((bid / quantity) / 2 < (buyout / quantity)
							&& (bid / quantity) < top.getMinBid()) {
						top.setMinBid(bid / quantity);
						top.setMinBidOwner(owner);
					}
					if (top.getMinBuyout() == 0 || buyout != 0
							&& (buyout / quantity) < top.getMinBuyout()) {
						top.setMinBuyout(buyout / quantity);
						top.setMinBuyoutOwner(owner);
						top.setTimeLeft(timeLeft);
					}
					top.setTotalQuantity(top.getTotalQuantity() + quantity);
				}
			}
		}
	}

	public List<Auction> getAuctionList() {
		if (auctions.size() == 0) {
			for (Auction t : itemAuctions.values()) {
				auctions.add(t);
			}
			for (Auction t : pets.values()) {
				auctions.add(t);
			}
			for (Auction t : multiTypeItemAuctions.values()) {
				auctions.add(t);
			}
		}
		return auctions;
	}

	public int getMaxAuctionId() {
		return maxAuctionId;
	}
	
	public int getOwnerQuantity() {
		return ownerSet.size();
	}
	
	public List<Player> getPlayers() {
		return new ArrayList<Player>(ownerSet);
	}

}
