package com.ceri.ceribnb;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  public void switchToHomepageScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("authentified-view.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("CeriBnB");
    stage.setScene(scene);
    stage.show();
  }

}
