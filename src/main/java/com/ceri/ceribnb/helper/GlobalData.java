package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import javafx.scene.image.Image;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GlobalData {
    private static GlobalData instance;

    private List<Sejour> sejours;

    private List<Sejour> ownSejour;

    private Set<Sejour> cart;

    private Utilisateur loggedInUser;

    private Sejour details;

    private int numberGenerated;

    private String reservationId;

    private GlobalData() {}

    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public List<Sejour> getSejours() {
        return sejours;
    }

    public void setSejours(List<Sejour> sejours) {
        this.sejours = sejours;
    }

    public Set<Sejour> getCart() { return cart; }

    public void setCart(Set<Sejour> cart) { this.cart = cart; }

    public static void setInstance(GlobalData instance) { GlobalData.instance = instance; }

    public Utilisateur getLoggedInUser() { return loggedInUser; }

    public void setLoggedInUser(Utilisateur loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Sejour getDetails() { return details; }

    public void setDetails(Sejour details) { this.details = details; }


    public int getNumberGenerated() { return numberGenerated; }

    public void setNumberGenerated(int numberGenerated) { this.numberGenerated = numberGenerated; }

    public List<Sejour> getOwnSejour() { return ownSejour; }

    public void setOwnSejour(List<Sejour> ownSejour) { this.ownSejour = ownSejour; }

    public String getReservationId() { return reservationId; }

    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
}

