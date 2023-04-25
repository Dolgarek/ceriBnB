package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Reservation;
import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.helper.DabatabaseHandler;
import com.ceri.ceribnb.helper.ReservationListCell;
import com.ceri.ceribnb.helper.SejourListCell;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.mongodb.client.model.Filters.eq;

public class ReservationController {

    private ListSejourController mainController;

    private Set<Sejour> reservationItems = new HashSet<>();

    @FXML
    private ListView<Sejour> resaListView;

    @FXML
    private Button closeButton;

    public void close() {
        Stage cartStage = (Stage) closeButton.getScene().getWindow();
        cartStage.close();
    }

    public void setMainController(ListSejourController mainController) {
        this.mainController = mainController;
        MongoDatabase database = DabatabaseHandler.instanciateDatabase();
        MongoCollection<Document> reservation = database.getCollection("Reservation");
        for (Document doc : reservation.find(eq("userId", new ObjectId("642fc683392ed9f00e00790d")))) {
            Reservation r = new Reservation();
            r.setSejourId(doc.getObjectId("sejourId"));
            r.setUserId(doc.getObjectId("userId"));
            r.setStatus(doc.getString("status"));
            MongoCollection<Document> sejours = database.getCollection("SejourReel");
            for (Document d : sejours.find(eq("_id", r.getSejourId()))) {
                Sejour s = new Sejour();
                s.setTitre(d.getString("titre"));
                s.setImgPath(d.getString("img"));
                File file = new File("/Users/theoquezel-perron/Downloads/" + s.getImgPath());
                if (file.exists()) {
                    Image img = new Image(file.toURI().toString());
                    s.setImage(img);
                } else {
                    Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Question_mark_(black).png")));
                    s.setImage(img);
                }
                s.setPrix(Double.valueOf(d.getString("prix")));
                s.setStatus(doc.getString("status"));
                reservationItems.add(s);
            }
        }
        resaListView.setItems(FXCollections.observableArrayList(reservationItems));
        resaListView.setCellFactory(resa -> new ReservationListCell(mainController, this));
    };


}
