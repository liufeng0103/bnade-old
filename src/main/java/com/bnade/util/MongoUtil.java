package com.bnade.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
	
//	private static MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	private static MongoClient mongoClient = new MongoClient( "mongodb://bnade:luis2016@192.168.1.106:27017/ade" );
	

	public static MongoDatabase getDatabase() {
		return mongoClient.getDatabase("ade");
	}
	
	public static void main(String[] args) throws Exception {
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		MongoDatabase db = mongoClient.getDatabase("ade");
		MongoCollection<Document> coll = db.getCollection("col");
		
		Document doc = new Document("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new Document("x", 203).append("y", 102));
		coll.insertOne(doc);
		
		List<Document> documents = new ArrayList<Document>();
		coll.insertMany(documents);
		Document myDoc = coll.find().first();
		System.out.println(myDoc.toJson());
		MongoCursor<Document> cursor = coll.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		} finally {
		    cursor.close();
		}
		for (Document cur : coll.find()) {
		    System.out.println(cur.toJson());
		}
	}
	
}
