package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.CartController;
import com.ceri.ceribnb.ListSejourController;
import com.ceri.ceribnb.ReservationController;
import com.ceri.ceribnb.entity.Sejour;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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

            ImageView imageView = new ImageView(sejour.getImage());
            imageView.setFitHeight(150); // Vous pouvez ajuster la taille de l'image ici
            imageView.setFitWidth(150);

            Label titre = new Label(sejour.getTitre());
            titre.setFont(new Font("Helvetica Neue", 24));

            Label prix = new Label(String.valueOf(sejour.getPrix()) + "€");
            prix.setFont(new Font("Helvetica Neue", 18));

            Label status = new Label(sejour.getStatus());
            status.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 24));

            final Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            if (Objects.equals(sejour.getStatus(), "REFUSE")) {
                status.setTextFill(Color.RED);
            } else if (Objects.equals(sejour.getStatus(), "EN ATTENTE")){
                status.setTextFill(Color.ORANGE);
            } else if (Objects.equals(sejour.getStatus(), "VALIDE")){
                status.setTextFill(Color.GREEN);
            }

            vBox.getChildren().addAll(titre, prix);
            vBox.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
            vBox.setAlignment(Pos.CENTER_LEFT);

            HBox.setMargin(vBox, new Insets(0, 0, 0, 50));
            HBox.setMargin(status, new Insets(0, 0, 0, 50));
            hBox.getChildren().addAll(imageView, vBox, spacer, status);
            hBox.setAlignment(Pos.CENTER_LEFT);
            setGraphic(hBox);
        }
    }
}
