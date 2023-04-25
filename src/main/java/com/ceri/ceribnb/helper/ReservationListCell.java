package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.CartController;
import com.ceri.ceribnb.ListSejourController;
import com.ceri.ceribnb.ReservationController;
import com.ceri.ceribnb.entity.Sejour;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class ReservationListCell extends ListCell<Sejour> {
    private ListSejourController mainController;
    private ReservationController reservationController;

    public ReservationListCell(ListSejourController mainController, ReservationController reservationController) {
        this.mainController = mainController;
        this.reservationController = reservationController;
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
            Text descriptifText = new Text(sejour.getStatus());
            Text prix = new Text(String.valueOf(sejour.getPrix()));
            if (Objects.equals(sejour.getStatus(), "EN ATTENTE")) {
                descriptifText.setFill(Color.RED);
            } else {
                descriptifText.setFill(Color.GREEN);
            }
            ImageView imageView = new ImageView(sejour.getImage());
            imageView.setFitHeight(100); // Vous pouvez ajuster la taille de l'image ici
            imageView.setFitWidth(100);

            hBox.getChildren().addAll(nomText, prix, descriptifText);
            vBox.getChildren().addAll(hBox, imageView);
            setGraphic(vBox);
        }
    }
}
