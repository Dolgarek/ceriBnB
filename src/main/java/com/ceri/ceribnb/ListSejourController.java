package com.ceri.ceribnb;

import com.ceri.ceribnb.SejourGenerator;
import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import com.ceri.ceribnb.helper.SejourListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class ListSejourController {

    @FXML
    private ListView<Sejour> sejourListView;

    public void initialize() {
        SejourGenerator sg = new SejourGenerator();
        List<Utilisateur> users = sg.getUtilisateurs();
        List<Sejour> test = sg.genererSejours(10000, users);

        ObservableList<Sejour> sejours = FXCollections.observableArrayList(test);

        sejourListView.setItems(sejours);
        sejourListView.setCellFactory(sejour -> new SejourListCell());
    }
}
