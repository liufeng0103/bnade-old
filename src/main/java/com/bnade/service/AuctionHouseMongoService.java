package com.bnade.service;

import java.util.ArrayList;
import java.util.List;

import lf.bnade.jmodel.JAuction;
import lf.bnade.jmodel.JAuctions;

import org.bson.Document;

import com.bnade.dao.MongoDao;
import com.bnade.util.HttpClient;
import com.google.gson.Gson;

public class AuctionHouseMongoService {
	
	private MongoDao dao;
	private Gson gson;
	
	public AuctionHouseMongoService() {
		dao = new MongoDao();
		gson = new Gson();
	}

	public void insertMany(int realmId, List<JAuction> auctions) {
		List<Document> docs = new ArrayList<Document>();
		for (JAuction auc : auctions) {
			auc.setRealmId(realmId);
			Document doc = Document.parse(gson.toJson(auc));
			docs.add(doc);
		}
		dao.insertMany("ah", docs);
	}
	
	public static void main(String[] args) throws Exception {
		HttpClient httpClient = new HttpClient();
		AuctionHouseMongoService service = new AuctionHouseMongoService();
		Gson gson = new Gson();
		String json = httpClient.get("http://auction-api-cn.worldofwarcraft.com/auction-data/6b258426dbb37f9c73f0e008e6d56687/auctions.json");
		List<JAuction> auctions = gson.fromJson(json, JAuctions.class).getAuctions();
		service.insertMany(38, auctions);
	}
}
