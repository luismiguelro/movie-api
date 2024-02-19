package dev.luismiguelro.movies.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Value("${MONGO_DATABASE}")
    private String mongoDatabase;

    @Value("${MONGO_USER}")
    private String mongoUser;

    @Value("${MONGO_PASSWORD}")
    private String mongoPassword;

    @Value("${MONGO_CLUSTER}")
    private String mongoCluster;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), mongoDatabase);
    }

    @Bean
    public MongoClient mongoClient() throws Exception {
        String connectionString = String.format("mongodb+srv://%s:%s@%s", mongoUser, mongoPassword, mongoCluster);
        return MongoClients.create(connectionString);
    }
}
