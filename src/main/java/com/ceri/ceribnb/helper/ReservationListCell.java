package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.BookingRequestsController;
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
import org.bson.types.ObjectId;

import java.util.Objects;

public class ReservationListCell extends ListCell<Sejour> {

    private boolean isHost;
    private ListSejourController mainController;
    private ReservationController reservationController;
    private BookingRequestsController bookingRequestsController;

    public ReservationListCell(boolean isHost, BookingRequestsController bookingRequestsController) {
        this.bookingRequestsController = bookingRequestsController;
        this.isHost = isHost;
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

            final Region vSpacer = new Region();
            HBox.setMargin(spacer, new Insets(10));

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
            if (this.isHost) {
                VBox vBox1 = new VBox();
                vBox1.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
                vBox1.setAlignment(Pos.CENTER);
                if (sejour.getStatus().equals("EN ATTENTE")) {
                    Button valide = new Button("Accepter");
                    vBox1.setSpacing(5);
                    valide.setOnAction(e -> {
                        GlobalData.getInstance().setReservationId(sejour.getWaitingBookinId());
                        bookingRequestsController.updateStatus(e, "VALIDE");
                    });
                    Button refuse = new Button("Refuser");
                    refuse.setMinWidth(valide.getWidth());
                    refuse.setOnAction(e -> {
                        GlobalData.getInstance().setReservationId(sejour.getWaitingBookinId());
                        bookingRequestsController.updateStatus(e, "REFUSE");
                    });
                    vBox1.getChildren().addAll(valide, vSpacer, refuse);
                    hBox.getChildren().addAll(imageView, vBox, spacer, vBox1, status);
                } else {
                    hBox.getChildren().addAll(imageView, vBox, spacer, status);
                }
            } else {
                hBox.getChildren().addAll(imageView, vBox, spacer, status);
            }
            hBox.setAlignment(Pos.CENTER_LEFT);
            setGraphic(hBox);
        }
    }
}
