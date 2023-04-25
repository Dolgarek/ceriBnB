package com.ceri.ceribnb;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.helper.GlobalData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DetailController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  private ListSejourController mainController;

  private Sejour current;

  @FXML
  private Button add_to_cart;

  @FXML
  private Button cart;

  @FXML
  private Button user_book;

  @FXML
  private Label titreSejour;

  @FXML
  private ImageView imgSejour;

  @FXML
  private TextArea descSejour;

  @FXML
  private Label prixSejour;

  @FXML
  private Label debutSejour;

  @FXML
  private Label finSejour;

  @FXML
  private TextField titleComms;

  @FXML
  private  TextArea contentComms;

  @FXML
  private Button sendComms;

  public void initialize() throws ParseException {
    DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
    current = GlobalData.getInstance().getDetails();
    Date dateDebut = sourceFormat.parse(current.getDateDebut());
    Date dateFin = sourceFormat.parse(current.getDateFin());
    long diffInMillies = Math.abs(dateFin.getTime() - dateDebut.getTime());
    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    String realValue = String.valueOf(diff * current.getPrix());
    titreSejour.setText(current.getTitre());
    imgSejour.setImage(current.getImage());
    descSejour.setText(current.getAdresse() + "\n" + current.getPays() + ", " + current.getVille() + ", " + current.getCodeZip() + "\n\n" + current.getDescription());
    descSejour.setWrapText(true);
    prixSejour.setText("Prix: " + String.valueOf(current.getPrix()) + "€/nuits" + " ~ " + realValue + "€");
    debutSejour.setText(current.getDateDebut());
    finSejour.setText(current.getDateFin());
  }

  public void switchToCart(ActionEvent event) throws IOException {
    this.mainController.showCart(event);
  }

  public void switchToBooking(ActionEvent event) throws IOException {
    this.mainController.showReservation(event);
  }

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
