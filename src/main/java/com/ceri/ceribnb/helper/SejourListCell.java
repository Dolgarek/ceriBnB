package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.CartController;
import com.ceri.ceribnb.ListSejourController;
import com.ceri.ceribnb.entity.Sejour;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
            HBox hBox = new HBox();
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            ImageView imageView = new ImageView(sejour.getImage());
            imageView.setFitHeight(200); // Vous pouvez ajuster la taille de l'image ici
            imageView.setFitWidth(200);

            Label titre = new Label(sejour.getTitre());
            titre.setFont(new Font("Helvetica Neue", 24));
            Label pays = new Label(sejour.getPays());
            pays.setFont(new Font("Helvetica Neue",18));
            Label ville = new Label(sejour.getVille());
            ville.setFont(new Font("Helvetica Neue",18));

            final Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label dateDebut = new Label("Du : " + sejour.getDateDebut());
            dateDebut.setFont(new Font("Helvetica Neue",18));
            Label dateFin = new Label("Au : " + sejour.getDateFin());
            dateFin.setFont(new Font("Helvetica Neue",18));
            Label prix = new Label(String.valueOf(sejour.getPrix()) + "€/nuit");
            prix.setFont(new Font("Helvetica Neue",24));

            Button actionButton = new Button();

            if (cartMode) {
                actionButton.setText("Supprimer");
                actionButton.setOnAction(e -> cartController.removeFromCart(sejour));
            } else {
                actionButton.setText("Voir les détails de l'annonce");
                actionButton.setId("sejour_" + sejour.getId());
                if (GlobalData.getInstance().getLoggedInUser() != null) {
                    actionButton.setOnAction(e -> {
                        try {
                            if (GlobalData.getInstance().getLoggedInUser().getRole().equals("hote")) {
                                mainController.switchToDetailHostScene(e, sejour);
                            } else if (GlobalData.getInstance().getLoggedInUser().getRole().equals("voyageur")) {
                                mainController.switchToDetailScene(e, sejour);
                            }
                            // mainController.switchToDetailScene(e, sejour);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                } else {
                    actionButton.setOnAction(e -> {
                        try {
                            GlobalData.getInstance().setDetails(sejour);
                            mainController.switchToLoginFormScene(e);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
            }

            vBox1.getChildren().addAll(titre, pays, ville);
            vBox1.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
            vBox1.setAlignment(Pos.CENTER_LEFT);

            vBox2.getChildren().addAll(dateDebut, dateFin, prix);
            vBox1.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
            vBox2.setAlignment(Pos.CENTER_LEFT);

            actionButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

            HBox.setMargin(vBox1, new Insets(0, 0, 0, 50));
            HBox.setMargin(vBox2, new Insets(0, 0, 0, 50));
            HBox.setMargin(actionButton, new Insets(0, 0, 0, 50));
            hBox.getChildren().addAll(imageView, vBox1, spacer, vBox2, actionButton);
            hBox.setAlignment(Pos.CENTER_LEFT);
            setGraphic(hBox);
        }
    }
}
