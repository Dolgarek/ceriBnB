package com.ceri.ceribnb;

import com.ceri.ceribnb.helper.SejourGenerator;
import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import com.ceri.ceribnb.helper.SejourListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ListSejourController {

    @FXML
    private ListView<Sejour> sejourListView;
    private List<Image> images = new ArrayList<>();
    private Random random = new Random();

    private CartController cartController;

    private ObservableList<Sejour> sejours;

    public void initialize() {
        for (int i = 1; i <= 9; i++) {
            Image image = new Image(getClass().getResourceAsStream("/img/" + i + ".png"));
            images.add(image);
        }

        CartController cartController = new CartController();

        SejourGenerator sg = new SejourGenerator();
        List<Utilisateur> users = sg.getUtilisateurs();
        List<Sejour> test = sg.genererSejours(10000, users, images);

        sejours = FXCollections.observableArrayList(test);

        sejourListView.setItems(sejours);
        sejourListView.setCellFactory(sejour -> new SejourListCell(this, cartController, false));
    }

    public Image getRandomImage() {
        return images.get(random.nextInt(images.size()));
    }

    private Set<Sejour> cartItems = new HashSet<>();

    public void addToCart(Sejour sejour) {
        if (cartItems.add(sejour)) {
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

    public ObservableList<Sejour> getCartItems() { return FXCollections.observableArrayList(cartItems); }

    public void removeFromCart(Sejour sejour) {
        cartItems.remove(sejour);
    }

    public ObservableList<Sejour> validateOrder() {
        sejours.removeAll(cartItems);
        cartItems.clear();
        // Retournez à la liste des séjours après avoir validé la commande.
        cartController.close();
        return sejours;
    }

    @FXML
    private Button cartButton;

    @FXML
    private void showCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ceri/ceribnb/cart.fxml"));
            Parent cartRoot = loader.load();
            cartController = loader.getController();
            cartController.setMainController(this);

            Scene cartScene = new Scene(cartRoot);
            Stage cartStage = new Stage();
            cartStage.setScene(cartScene);
            cartStage.setTitle("Panier");
            cartStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
