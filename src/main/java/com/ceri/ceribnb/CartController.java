package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.helper.SejourListCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class CartController {
    private ListSejourController mainController;

    @FXML
    private ListView<Sejour> cartListView;

    @FXML
    private Button validateOrderButton;

    @FXML
    private Button closeButton;


    private Stage stage;
    private Scene scene;
    private Parent root;

    private ReservationController reservationController;

    public void switchToHomepageScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("authentified-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1445, 833);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToListReservationScene(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("list-reservation.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list-reservation.fxml"));
        Parent root = fxmlLoader.load();
        reservationController = fxmlLoader.getController();
        reservationController.setMainController(this.mainController);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1445, 833);
        stage.setTitle("Demandes de réservation");
        stage.setScene(scene);
        stage.show();
    }

    public void setMainController(ListSejourController mainController) {
        this.mainController = mainController;
        cartListView.setItems(mainController.getCartItems());
        cartListView.setCellFactory(sejour -> new SejourListCell(mainController, this, true));
    }

    public void removeFromCart(Sejour sejour) {
        mainController.removeFromCart(sejour);
        cartListView.setItems(mainController.getCartItems());
    }

    public void validateOrder(ActionEvent event) throws IOException {
        mainController.validateOrder();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("authentified-view.fxml"));
        Parent root = fxmlLoader.load();

        // Create a new Scene object
        Scene sejourList = new Scene(root);

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene to the current stage
        currentStage.setScene(sejourList);
        currentStage.show();
    }
}
