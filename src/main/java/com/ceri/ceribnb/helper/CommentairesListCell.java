package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.DetailController;
import com.ceri.ceribnb.entity.Commentaire;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CommentairesListCell extends ListCell<Commentaire> {

    private DetailController detailController;

    public CommentairesListCell(DetailController detailController) {
        this.detailController = detailController;
    }

    @Override
    protected void updateItem(Commentaire commentaire, boolean empty) {
        super.updateItem(commentaire, empty);

        if (empty || commentaire == null) {
            setText(null);
            setGraphic(null);
        } else {
            HBox hBox = new HBox();
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();

            String newString = commentaire.getContent();
            String contentStr = "";
            if (newString.length() > 40) {
                for (int i = 0; i < newString.length(); i++) {
                    contentStr += newString.charAt(i);
                    if (i%38 == 1 && i != 1) {
                        contentStr += "-\n";
                    }
                }
            } else {
                contentStr = newString;
            }

            Label titre = new Label(commentaire.getTitre());
            Label content = new Label(contentStr);
            Label auteur = new Label(commentaire.getAuteur().getNom() + " " + commentaire.getAuteur().getPrenom());
            Label date = new Label(commentaire.getDate());

            titre.setWrapText(true);
            content.setWrapText(true);

            titre.setStyle("-fx-font-size: 15px;");

            auteur.setTextFill(Color.GRAY);
            date.setTextFill(Color.GRAY);

            final Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            vBox1.getChildren().addAll(titre, content);
            vBox1.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
            vBox1.setAlignment(Pos.CENTER_LEFT);

            vBox2.getChildren().addAll(auteur, date);
            vBox1.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
            vBox2.setAlignment(Pos.CENTER_LEFT);

            HBox.setMargin(vBox1, new Insets(0, 0, 0, 50));
            HBox.setMargin(vBox2, new Insets(0, 0, 0, 50));
            hBox.getChildren().addAll(vBox1, spacer, vBox2);
            hBox.setAlignment(Pos.CENTER_LEFT);
            setGraphic(hBox);
        }
    }
}
