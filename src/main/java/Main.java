import circuit.CircuitDB;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.mongodb.client.model.Filters.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private Properties properties;
    public static void main(String[] arguments) throws IOException {
        new Main().start();

    }

    public void start() throws IOException {
        CircuitDB circuitDB = new CircuitDB();
        Properties.initialize();
        properties = Properties.getInstance();
        logger.info("properties: {}", properties);

        MongoConnection.initialize();
        MongoDatabase mongoDatabase = MongoConnection.getMongoDatabase();
        circuitDB.cirDb(mongoDatabase);
        MongoCollection<Document> a =mongoDatabase.getCollection("uninan_cloud_20200519");
////        MongoCursor<Document> b=a.find(eq("jobId",100101)).iterator();
        MongoCursor<Document> b=a.find(eq("jobId",100101)).projection(Projections.include("_id","_class")).iterator();
//
//        System.out.println("테스트:"+b.next().toJson());
    }
}
