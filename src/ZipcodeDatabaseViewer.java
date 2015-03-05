/*
 * Class that runs the database
 * including making the queries
 * to query the database
 * Author: Christopher Polanco
 */

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.QueryBuilder;

public class ZipcodeDatabaseViewer {
	
	/* Global variable to hold the collection
	 * that is retrieved when connecting to 
	 * the DB
	 */
	private MongoCollection<Document> collection;
	
	//holds connection to database
	private DatabaseConnection db;
	
	public ZipcodeDatabaseViewer(){
	    db = new DatabaseConnection();
	    collection = db.getCollection();
	}
	
	/* To insert a document into the database
	 * one must first create the document using
	 * either QueryBuilder or regular Document
	 * creation syntax. then one simply calls 
	 * the insertOne method with the document
	 * as its parameters
	 */
	public void insert(Scanner console){
		System.out.print("zipcode = "); String zipcode = console.next();
		System.out.print("city = "); String city = console.next();
		System.out.print("loc = "); String loc = console.next().concat(" "+ console.next());
		System.out.print("pop = "); String pop = console.next();
		System.out.print("state = "); String state = console.next();
		
		//Create Document using QueryBuilder
//		Document doc = QueryBuilder.query("_id").is(12345).and("city").is("BX")
//				.and("loc").is("[-47.2435, 45.2345]").and("pop").is("7483")
//				.and("state").is("PA").toDocument();
		
		//Create Document using Document Syntax
		Document doc = new Document("_id", zipcode)
				.append("city", city).append("loc", loc)
				.append("pop", pop).append("state", state);
		
		collection.insertOne(doc);
	}
	
	/* To delete a document into the database
	 * one must first create the document using
	 * either QueryBuilder or regular Document
	 * creation syntax. then one simply calls 
	 * the deleteOne method with the document
	 * as its parameters
	 */
	public void delete(Scanner console){
		System.out.print("zipcode = ");
		String zipcode = console.next();
		
		//Document doc = QueryBuilder.query("_id").is(12345).toDocument();
		
		Document doc = new Document("_id", zipcode);
		
		collection.deleteOne(doc);
	}
	
	public void update(){
		Document query = QueryBuilder.query("brand").is("adidas").toDocument();
		Document update = new Document("$set", new Document("color.tongue", "purple"));
		
		collection.updateOne(query, update);
	}
	
	/* To query a the database one must first 
	 * create a document using either QueryBuilder
	 * or regular Document creation syntax. Then 
	 * if necessary one would create a projection
	 * to limit the fields that will show in the 
	 * query. Then one simply calls the find method 
	 * on the collection with the query as the parameter
	 * then create a cursor by calling the iterator method
	 * on the query response. Lastly use the cursor to iterate
	 * through the query response.
	 */
	public void query(){
		/*
		 * Creates a document using QueryBuilder
		 * which makes it easier to create Docs
		 */
//		Document query = QueryBuilder.query("state").is("PA")
//				.or(QueryBuilder.query("city").is("SCRANTON"))
//				.or(QueryBuilder.query("city").is("DUNMORE"))//.and("pop").lessThanEquals(15432)
//				.toDocument();
		
		Document query = QueryBuilder.query("state").is("PA").toDocument();
		
		//Document projection = QueryBuilder.query("_id").is(false).and("city").is(false).toDocument();
		
		//join
		
		//creates a cursor to iterate through the query response
		MongoCursor<Document> cursor = collection.find(query).iterator();
		
		try{
			//iterate through response
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
		}finally{
			cursor.close();
		}
	}
	
	public void deleteCollection(){
		collection.dropCollection();
	}
	
	public void viewAllCollections(){
		for (String name : db.getDB().listCollectionNames()) {
		    System.out.println(name);
		}
	}
	
	public void createCollection(Scanner console){
		System.out.println("Please insert collection name: ");
		String name = console.next();
		
		db.createCollection(name);
	}
	
	public void changeCollection(Scanner console){
		System.out.println("What is the name of the collection you want to change to?");
		String newCollection = console.next();
		
		db.changeCollection(newCollection);
		collection = db.getCollection();
	}
	
	public void getCount(){
		System.out.println(collection.count());
	}
	
	public void close(){
		db.close();
	}
}