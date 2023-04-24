package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.entity.Sejour;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SejourListCell extends ListCell<Sejour> {
    @Override
    protected void updateItem(Sejour sejour, boolean empty) {
        super.updateItem(sejour, empty);

        if (empty || sejour == null) {
            setText(null);
            setGraphic(null);
        } else {
            HBox hBox = new HBox();
            Text nomText = new Text(sejour.getTitre());
            Text descriptifText = new Text(sejour.getDescription());

            hBox.getChildren().addAll(nomText, descriptifText);
            setGraphic(hBox);
        }
    }
}
