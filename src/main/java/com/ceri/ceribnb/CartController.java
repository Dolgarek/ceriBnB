package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.helper.SejourListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class CartController {
    private ListSejourController mainController;

    @FXML
    private ListView<Sejour> cartListView;

    @FXML
    private Button validateOrderButton;

    @FXML
    private Button closeButton;

    public void close() {
        Stage cartStage = (Stage) validateOrderButton.getScene().getWindow();
        cartStage.close();
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

    public void validateOrder() {
        mainController.validateOrder();
    }
}
