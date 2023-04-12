package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
public class SejourGenerator {

    private static final String DATABASE_NAME = "CeriBnB";
    private static final String SEJOUR_COLLECTION = "SejourReel";
    private static final String FIXTURE_ADRESSE = "fixtureAdresse";
    private static final String FIXTURE_CODEZIP = "fixtureCodeZip";
    private static final String FIXTURE_PAYS = "fixturePays";
    private static final String FIXTURE_PRIX = "fixturePrix";
    private static final String FIXTURE_TITRE = "fixtureTitre";

    private MongoClient mongoClient;
    private MongoDatabase database;


    public SejourGenerator() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://javafxUser:qVt2rXoDf9s8r13q@disceri.qy35zyn.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

    public List<Sejour> getSejoursReels() {
        List<Sejour> sejoursReels = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection(SEJOUR_COLLECTION);

        for (Document doc : collection.find()) {
            Sejour sejour = new Sejour();
            sejour.setId(doc.getObjectId("_id").toString());
            sejour.setTitre(doc.getString("titre"));
            sejour.setDescription(doc.getString("description"));
            sejour.setAdresse(doc.getString("adresse"));
            sejoursReels.add(sejour);
        }

        return sejoursReels;
    }

    public List<Utilisateur> getUtilisateurs() {
        List<Utilisateur> users = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("Utilisateur");

        for (Document doc : collection.find()) {
            Utilisateur u = new Utilisateur(doc.getObjectId("_id").toString(), doc.getString("mail"), doc.getString("nom"), doc.getString("prenom"), doc.getString("adresse"), doc.getString("ville"), doc.getString("codeZip"), doc.getString("pays"), doc.getString("role"), doc.getString("password"));
            users.add(u);
        }

        return users;
    }

    public List<Sejour> genererSejours(int nombreSejours, List<Utilisateur> hotes) {
        List<Sejour> sejoursGeneres = new ArrayList<>();
        Random random = new Random();

        MongoCollection<Document> adresseCollection = database.getCollection(FIXTURE_ADRESSE);
        MongoCollection<Document> codeZipCollection = database.getCollection(FIXTURE_CODEZIP);
        MongoCollection<Document> paysCollection = database.getCollection(FIXTURE_PAYS);
        MongoCollection<Document> prixCollection = database.getCollection(FIXTURE_PRIX);
        MongoCollection<Document> titreCollection = database.getCollection(FIXTURE_TITRE);

        List<String> adresses = new ArrayList<>();
        List<String> codesZip = new ArrayList<>();
        List<String> pays = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<Double> prix = new ArrayList<>();
        List<String> titres = new ArrayList<>();

        for (Document doc : adresseCollection.find()) {
            adresses.add(doc.getString("adresse"));
        }
        for (Document doc : codeZipCollection.find()) {
            codesZip.add(doc.getString("code"));
        }
        for (Document doc : paysCollection.find()) {
            pays.add(doc.getString("ville"));
            descriptions.add(doc.getString("description"));
        }
        for (Document doc : prixCollection.find()) {
            prix.add(Double.valueOf(doc.getString("prix")));
        }
        for (Document doc : titreCollection.find()) {
            titres.add(doc.getString("adresse"));
        }

        for (int i = 0; i < nombreSejours; i++) {
            Sejour sejour = new Sejour();
            sejour.setTitre(titres.get(random.nextInt(titres.size())));
            sejour.setDescription(descriptions.get(random.nextInt(descriptions.size())));
            sejour.setAdresse(adresses.get(random.nextInt(adresses.size())));
            sejour.setPrix(prix.get(random.nextInt(prix.size())));
            sejour.setDateDebut(String.valueOf(new Date()));
            sejour.setDateFin(String.valueOf(new Date()));
            sejour.setHote(hotes.get(random.nextInt(hotes.size())));
            sejoursGeneres.add(sejour);
        }
        return sejoursGeneres;
    }
}
