package com.ceri.ceribnb;

import com.ceri.ceribnb.helper.GlobalData;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookingRequestsController {

  private Stage stage;
  private Scene scene;
  private Parent root;

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

  public void switchToCalendarScene(ActionEvent event) throws IOException {
    //Parent root = FXMLLoader.load(getClass().getResource("detail-view.fxml"));
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("calendar-view.fxml"));
    Parent root = fxmlLoader.load();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("Calendrier");
    stage.setScene(scene);
    stage.show();
  }

  public void logout(ActionEvent e) throws IOException {
    GlobalData.getInstance().setOwnSejour(null);
    GlobalData.getInstance().setDetails(null);
    GlobalData.getInstance().setLoggedInUser(null);
    GlobalData.getInstance().setCart(null);

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("unauthentified-view.fxml"));
    Parent root = fxmlLoader.load();

    // Create a new Scene object
    Scene unauthentifiedView = new Scene(root);

    // Get the current stage
    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

    // Set the new scene to the current stage
    currentStage.setScene(unauthentifiedView);
    currentStage.show();
  }
}
