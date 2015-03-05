/*
 * Class to connect to database
 * Author: Christopher Polanco
 */

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnection {

	private MongoCollection<Document> collection;
	private MongoClient mongoClient;
	private MongoDatabase db;

	public DatabaseConnection() {
		//connect to MongoDB
		//mongoClient = new MongoClient("localhost", 27017);
		mongoClient = new MongoClient(new MongoClientURI("mongodb://username:password@host/db"));
		System.out.println("Connection established.");

		//get DB then collection
		db = mongoClient.getDatabase("db");
		collection = db.getCollection("collection");
	}

	public void changeCollection(String newCollection){
		collection = db.getCollection(newCollection);
	}

	public void createCollection(String name){
		db.createCollection(name);
	}

	public MongoDatabase getDB(){
		return db;
	}

	public MongoCollection<Document> getCollection(){
		return collection;
	}

	public void close(){
		mongoClient.close();
	}
}
