package com.ceri.ceribnb;

import com.ceri.ceribnb.SejourGenerator;
import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import com.ceri.ceribnb.helper.SejourListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ListSejourController {

    @FXML
    private ListView<Sejour> sejourListView;
    private List<Image> images = new ArrayList<>();
    private Random random = new Random();

    public void initialize() {
        for (int i = 1; i <= 9; i++) {
            Image image = new Image(getClass().getResourceAsStream("/img/" + i + ".png"));
            images.add(image);
        }

        SejourGenerator sg = new SejourGenerator();
        List<Utilisateur> users = sg.getUtilisateurs();
        List<Sejour> test = sg.genererSejours(10000, users, images);

        ObservableList<Sejour> sejours = FXCollections.observableArrayList(test);

        sejourListView.setItems(sejours);
        sejourListView.setCellFactory(sejour -> new SejourListCell(this));
    }

    public Image getRandomImage() {
        return images.get(random.nextInt(images.size()));
    }

    private Set<Sejour> cart = new HashSet<>();

    public void addToCart(Sejour sejour) {
        if (cart.add(sejour)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Séjour ajouté au panier");
            alert.setHeaderText(null);
            alert.setContentText("Le séjour a été ajouté au panier.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Séjour déjà dans le panier");
            alert.setHeaderText(null);
            alert.setContentText("Le séjour est déjà présent dans le panier.");
            alert.showAndWait();
        }
    }
}
