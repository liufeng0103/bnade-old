package lf.bnade.task.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lf.bnade.model.Auction;
import lf.bnade.model.Pet;
import lf.bnade.util.TimeHelper;

public class AuctionHistoryHandler {

	private List<Auction> finalResult;
	
	public void process(List<Auction> aucs, long am0) throws Exception {
		finalResult = new ArrayList<Auction>();

		Map<String, Auction> am0_6Map = new HashMap<String, Auction>();
		Map<String, Auction> am6_12Map = new HashMap<String, Auction>();
		Map<String, Auction> pm12_18Map = new HashMap<String, Auction>();
		Map<String, Auction> pm18_24Map = new HashMap<String, Auction>();
		Map<String, Auction> petAm0_6Map = new HashMap<String, Auction>();
		Map<String, Auction> petAm6_12Map = new HashMap<String, Auction>();
		Map<String, Auction> petPm12_18Map = new HashMap<String, Auction>();
		Map<String, Auction> petPm18_24Map = new HashMap<String, Auction>();
		long am0_6 = am0 + TimeHelper.HOUR * 6;
		long am6_12 = am0_6 + TimeHelper.HOUR * 6;
		long pm12_18 = am6_12 + TimeHelper.HOUR * 6;
		long pm18_24 = pm12_18 + TimeHelper.HOUR * 6;		
			
		for (Auction auc : aucs) {
			if (auc.getItemId() == Pet.PET_ITEM_ID) { // 宠物
				String petKey = auc.getPetSpeciesId() + " " + auc.getPetLevel();
				Auction currentAuc = null;
				if (auc.getLastModified() >= am0 &&  auc.getLastModified() <= am0_6) {
					currentAuc = petAm0_6Map.get(petKey);
					if (currentAuc == null) {
						auc.setLastModified(am0_6);
						petAm0_6Map.put(petKey, auc);
					} 
				} else if (auc.getLastModified() > am0_6 &&  auc.getLastModified() <= am6_12) {
					currentAuc = petAm6_12Map.get(petKey);
					if (currentAuc == null) {
						auc.setLastModified(am6_12);
						petAm6_12Map.put(petKey, auc);
					} 
				} else if (auc.getLastModified() > am6_12 &&  auc.getLastModified() <= pm12_18) {
					currentAuc = petPm12_18Map.get(petKey);
					if (currentAuc == null) {
						auc.setLastModified(pm12_18);
						petPm12_18Map.put(petKey, auc);
					} 
				} else if (auc.getLastModified() > pm12_18 &&  auc.getLastModified() <= pm18_24) {
					currentAuc = petPm18_24Map.get(petKey);
					if (currentAuc == null) {
						auc.setLastModified(pm18_24);
						petPm18_24Map.put(petKey, auc);
					} 
				} else {
					throw new Exception("数据的修改时间跟提供的时间不在同一天" + new Date(auc.getLastModified()) + " " + new Date(am0));
				}
				if (currentAuc != null) {
					currentAuc.setMinBid((currentAuc.getMinBid() + auc.getMinBid()) / 2);
					currentAuc.setMinBuyout((currentAuc.getMinBuyout() + auc.getMinBuyout()) / 2);
					currentAuc.setTotalQuantity((currentAuc.getTotalQuantity() + auc.getTotalQuantity()) / 2);
				}
			} else { // 普通物品
				Auction currentAuc = null;
				String tmpKey = "" + auc.getItemId() + auc.getContext() + auc.getBonusList();				
				if (auc.getLastModified() >= am0 &&  auc.getLastModified() <= am0_6) {
					currentAuc = am0_6Map.get(tmpKey);
					if (currentAuc == null) {
						auc.setLastModified(am0_6);
						am0_6Map.put(tmpKey, auc);
					} 
				} else if (auc.getLastModified() > am0_6 &&  auc.getLastModified() <= am6_12) {
					currentAuc = am6_12Map.get(tmpKey);
					if (currentAuc == null) {
						auc.setLastModified(am6_12);
						am6_12Map.put(tmpKey, auc);
					} 
				} else if (auc.getLastModified() > am6_12 &&  auc.getLastModified() <= pm12_18) {
					currentAuc = pm12_18Map.get(tmpKey);
					if (currentAuc == null) {
						auc.setLastModified(pm12_18);
						pm12_18Map.put(tmpKey, auc);
					} 
				} else if (auc.getLastModified() > pm12_18 &&  auc.getLastModified() <= pm18_24) {
					currentAuc = pm18_24Map.get(tmpKey);
					if (currentAuc == null) {
						auc.setLastModified(pm18_24);
						pm18_24Map.put(tmpKey, auc);
					} 
				} else {
					throw new Exception("数据的修改时间跟提供的时间不在同一天" + new Date(auc.getLastModified()) + " " + new Date(am0));
				}
				if (currentAuc != null) {
					currentAuc.setMinBid((currentAuc.getMinBid() + auc.getMinBid()) / 2);
					currentAuc.setMinBuyout((currentAuc.getMinBuyout() + auc.getMinBuyout()) / 2);
					currentAuc.setTotalQuantity((currentAuc.getTotalQuantity() + auc.getTotalQuantity()) / 2);
				}
			}
		}
		for (Auction auc : am0_6Map.values()) {
			finalResult.add(auc);
		}
		for (Auction auc : am6_12Map.values()) {
			finalResult.add(auc);
		}
		for (Auction auc : pm12_18Map.values()) {
			finalResult.add(auc);
		}
		for (Auction auc : pm18_24Map.values()) {
			finalResult.add(auc);
		}
		for (Auction auc : petAm0_6Map.values()) {
			finalResult.add(auc);
		}
		for (Auction auc : petAm6_12Map.values()) {
			finalResult.add(auc);
		}
		for (Auction auc : petPm12_18Map.values()) {
			finalResult.add(auc);
		}
		for (Auction auc : petPm18_24Map.values()) {
			finalResult.add(auc);
		}
	}
	
	public List<Auction> getResult() {
		return finalResult;
	}
}
