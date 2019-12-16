package shankarsan.reactive.configuration;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

@Configuration
public class MongoDBConnectionProvider {

	@Value("${mongodb.host}") private String mongodbHost;
	@Value("${mongodb.port}") private String mongodbPort;
	@Value("${mongodb.database}") private String mongodbDatabase;
	@Value("${mongodb.collection}") private String mongodbCollection;
	
	
	@Bean(value = "mongoDBClient")
	public MongoClient getMongoDBConnection() {
		return new MongoClient(mongodbHost, Integer.valueOf(mongodbPort));
	}
	
	@Bean(value = "mongodbCollection")
	public MongoCollection<Document> getCollection(@Qualifier("mongoDBClient") MongoClient mongoClient){
		return mongoClient.getDatabase(mongodbDatabase).getCollection(mongodbCollection);
	}
}
