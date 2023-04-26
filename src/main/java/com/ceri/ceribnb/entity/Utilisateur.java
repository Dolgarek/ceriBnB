package com.ceri.ceribnb.entity;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilisateur {

    private String id;
    private String mail;
    private String nom;
    private String prenom;
    private String adresse;
    private String ville;
    private String codeZip;
    private String pays;
    private String role;
    private String password;

    public Utilisateur(String id, String mail, String nom, String prenom, String adresse, String ville, String codeZip, String pays, String role, String password) {
        this.id = id;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
        this.codeZip = codeZip;
        this.pays = pays;
        this.role = role;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodeZip() {
        return codeZip;
    }

    public void setCodeZip(String codeZip) {
        this.codeZip = codeZip;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Utilisateur exist(String email, String password) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://javafxUser:qVt2rXoDf9s8r13q@disceri.qy35zyn.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("CeriBnB");
        MongoCollection<Document> collection = database.getCollection("Utilisateur");

        Document query =
                new Document("$and", Arrays.asList(
                        new Document("mail", email),
                        new Document("password", password)));

        collection.find(query);
        Utilisateur u = null;
        for (Document doc: collection.find(query) ) {
            u = new Utilisateur(doc.getObjectId("_id").toString(), doc.getString("mail"), doc.getString("nom"), doc.getString("prenom"), doc.getString("adresse"), doc.getString("ville"), doc.getString("codeZip"), doc.getString("pays"), doc.getString("role"), doc.getString("password"));
        }
        return u;
    }
}
