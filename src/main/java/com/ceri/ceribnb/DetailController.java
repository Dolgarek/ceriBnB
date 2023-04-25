package com.ceri.ceribnb;

import java.io.IOException;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.helper.GlobalData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DetailController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  private ListSejourController mainController;

  private Sejour current;

  @FXML
  private Button add_to_cart;

  public void switchToHomepageScene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("authentified-view.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setScene(scene);
    stage.show();
  }

  public void addToCart(ActionEvent e) throws IOException {
    if (GlobalData.getInstance().getDetails() != null) {
      this.mainController.addToCart(GlobalData.getInstance().getDetails());
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Aie... Une erreur  c'est produite");
      alert.setHeaderText(null);
      alert.setContentText("Sejour not found in GlobalData");
      alert.showAndWait();
    }
  }

  public void setMainController(ListSejourController listSejourController) {
    this.mainController = listSejourController;
  }
}
