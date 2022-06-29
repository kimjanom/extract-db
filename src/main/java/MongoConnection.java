import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    private static final Properties properties = Properties.getInstance();
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    public static void initialize() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(
                        new ConnectionString("mongodb://" + properties.getMongo().getHost() + ":" + properties.getMongo().getPort() + "/" + properties.getMongo().getDb())
                )
                .retryWrites(true)
                .build();
        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase(properties.getMongo().getDb());


    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}
