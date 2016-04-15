package com.bnade.dao;

import java.util.List;

import org.bson.Document;

import com.bnade.util.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDao {
	
	private MongoDatabase db;
	
	public MongoDao() {
		db = MongoUtil.getDatabase();
	}
	
	public void insert(String collectionName, Document document) {
		MongoCollection<Document> coll = db.getCollection(collectionName);
		coll.insertOne(document);
	}
	
	public void insertMany(String collectionName, List<Document> documents) {
		MongoCollection<Document> coll = db.getCollection(collectionName);
		coll.insertMany(documents);
	}
}
