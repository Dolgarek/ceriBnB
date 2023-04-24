package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.ListSejourController;
import com.ceri.ceribnb.entity.Sejour;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SejourListCell extends ListCell<Sejour> {
    private ListSejourController controller;

    public SejourListCell(ListSejourController controller) {
        this.controller = controller;
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

            Button addToCartButton = new Button("Ajouter au panier");
            addToCartButton.setOnAction(e -> controller.addToCart(sejour));

            hBox.getChildren().addAll(nomText, descriptifText, addToCartButton);
            vBox.getChildren().addAll(hBox, imageView);
            setGraphic(vBox);
        }
    }
}
