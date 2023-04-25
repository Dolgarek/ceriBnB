package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static GlobalData instance;
    private List<Sejour> sejours;

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
}

