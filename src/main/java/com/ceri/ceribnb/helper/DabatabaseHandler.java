package com.ceri.ceribnb.helper;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DabatabaseHandler {
    private static final String DATABASE_NAME = "CeriBnB";
    private static final String SEJOUR_COLLECTION = "SejourReel";
    private static final String FIXTURE_ADRESSE = "fixtureAdresse";
    private static final String FIXTURE_CODEZIP = "fixtureCodeZip";
    private static final String FIXTURE_PAYS = "fixturePays";
    private static final String FIXTURE_PRIX = "fixturePrix";
    private static final String FIXTURE_TITRE = "fixtureTitre";
    private static final String SEJOUR_REEL = "SejourReel";
    private static final String RESERVATION = "Reservation";
    private MongoClient mongoClient;
    private MongoDatabase database;

    public static MongoDatabase instanciateDatabase() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://javafxUser:qVt2rXoDf9s8r13q@disceri.qy35zyn.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        return database;
    }

}
