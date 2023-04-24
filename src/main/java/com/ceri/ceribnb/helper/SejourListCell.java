package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.CartController;
import com.ceri.ceribnb.ListSejourController;
import com.ceri.ceribnb.entity.Sejour;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SejourListCell extends ListCell<Sejour> {
    private ListSejourController mainController;
    private CartController cartController;
    private boolean cartMode;

    public SejourListCell(ListSejourController mainController, CartController cartController, boolean cartMode) {
        this.mainController = mainController;
        this.cartController = cartController;
        this.cartMode = cartMode;
    }

    @Override
    protected void updateItem(Sejour sejour, boolean empty) {
        super.updateItem(sejour, empty);

        if (empty || sejour == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            Text nomText = new Text(sejour.getTitre());
            Text descriptifText = new Text(sejour.getDateDebut());
            ImageView imageView = new ImageView(sejour.getImage());
            imageView.setFitHeight(100); // Vous pouvez ajuster la taille de l'image ici
            imageView.setFitWidth(100);

            Button actionButton = new Button();

            if (cartMode) {
                actionButton.setText("Supprimer");
                actionButton.setOnAction(e -> cartController.removeFromCart(sejour));
            } else {
                actionButton.setText("Ajouter au panier");
                actionButton.setOnAction(e -> mainController.addToCart(sejour));
            }

            hBox.getChildren().addAll(nomText, descriptifText, actionButton);
            vBox.getChildren().addAll(hBox, imageView);
            setGraphic(vBox);
        }
    }
}
