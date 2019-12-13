package shankarsan.reactive.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class MongoDBConnectionProvider {

	@Value("${mongodb.host}") private String mongodbHost;
	@Value("${mongodb.port}") private String mongodbPort;
	
	
	@Bean(value = "MongoDBClient")
	public MongoClient getMongoDBConnection() {
		return new MongoClient(mongodbHost, Integer.valueOf(mongodbPort));
	}
}
