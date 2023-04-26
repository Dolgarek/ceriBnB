package com.ceri.ceribnb;

import com.ceri.ceribnb.helper.GlobalData;
import java.io.IOException;

import com.ceri.ceribnb.helper.GraphicalCalendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class CalendarController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private GraphicalCalendar calendar;

  public void initialize() {
    /*HBox hBox = new HBox();
    final Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    hBox.getChildren().addAll(gc);
    hBox.setAlignment(Pos.CENTER_LEFT);*/
  }

  public void switchToHomepageScene(ActionEvent event) throws IOException {
    Parent root = null;
    if (GlobalData.getInstance().getLoggedInUser().getRole().equals("hote")) {
      root = FXMLLoader.load(getClass().getResource("authentified-view-host.fxml"));
    } else if (GlobalData.getInstance().getLoggedInUser().getRole().equals("voyageur")) {
      root = FXMLLoader.load(getClass().getResource("authentified-view.fxml"));
    }
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("CeriBnB");
    stage.setScene(scene);
    stage.show();
  }

  public void switchToBookingRequestsScene(ActionEvent event) throws IOException {
    //Parent root = FXMLLoader.load(getClass().getResource("detail-view.fxml"));
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("booking-requests-view.fxml"));
    Parent root = fxmlLoader.load();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("Demandes de réservation");
    stage.setScene(scene);
    stage.show();
  }

}
