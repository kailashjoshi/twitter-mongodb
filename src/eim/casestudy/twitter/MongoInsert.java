package eim.casestudy.twitter;
import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

/**
 * 
 * @author Kailash Joshi
 *
 */
public class MongoInsert {
	/**
	 * Insert stream tweets to mongodb
	 * @param data
	 */
	public static void insertData(String data){
		try {
			 
			Mongo mongo = new Mongo(MongodbConf.HOST_NAME, MongodbConf.PORT);
			DB db = mongo.getDB(MongodbConf.DATABASE_NAME);
			DBCollection collection = db.getCollection(MongodbConf.COLLECTION);
			DBObject dbObject = (DBObject) JSON
					.parse(data);
			collection.insert(dbObject);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	}


