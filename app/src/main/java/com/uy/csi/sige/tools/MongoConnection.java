package com.uy.csi.sige.tools;

import android.util.Log;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoConnection {

    public static final String TAG = "RANK: MongoConnection";

    public MongoDatabase getDB(){

        MongoClientURI uri = new MongoClientURI("mongodb+srv://rank_2019:csi.rank-2019@cluster0-gddh9.mongodb.net/test?retryWrites=true");
        MongoClient mongoClient = null;

        mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase( "pahr" );

        return database;
    }

    public DBCollection getCollection(String collectionName, Bson filter ){

        MongoCollection<Document> collection = getDB().getCollection( collectionName );
        FindIterable<Document> documents = collection.find( filter );
        MongoCursor<Document> cursor = documents.iterator();

        try{
            Log.i(TAG, "getCollection: " + cursor.next().toJson() );
        }catch(Exception e){
            Log.e( TAG, "getCollection: ", e );
        } finally {
            cursor.close();
        }

        return null;
    }

}
