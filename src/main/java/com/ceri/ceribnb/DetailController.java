package com.ceri.ceribnb;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.helper.DabatabaseHandler;
import com.ceri.ceribnb.helper.GlobalData;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
import org.bson.Document;
import org.bson.types.ObjectId;

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

  public void addCommentaire(ActionEvent e) {
    MongoDatabase database = DabatabaseHandler.instanciateDatabase();
    MongoCollection<Document> sejourReel = database.getCollection("SejourReel");
    MongoCollection<Document> commentaire = database.getCollection("Commentaire");
    Document doc = new Document();

    if (titleComms.getText().length() != 0 && contentComms.getText().length() != 0) {
      System.out.println("Titre: " + titleComms.getText() + " " + titleComms.getText().length());
      System.out.println("Corps: " + contentComms.getText() + " " + contentComms.getText().length());
      doc.append("_id", new ObjectId());
      if (current.getId().length() <= 5) {
        Document d2 = new Document();
        d2.append("_id", new ObjectId());
        d2.append("hoteId", new ObjectId(current.getHote().getId()));
        d2.append("titre", current.getTitre());
        d2.append("description", current.getDescription());
        d2.append("adresse", current.getAdresse());
        d2.append("ville", current.getVille());
        d2.append("Pays", current.getPays());
        d2.append("codeZip", current.getCodeZip());
        d2.append("prix", String.valueOf(current.getPrix()));
        d2.append("img", current.getImgPath());
        d2.append("dateDebut", current.getDateDebut());
        d2.append("dateFin", current.getDateFin());
        sejourReel.insertOne(d2);
        GlobalData.getInstance().getSejours().get((GlobalData.getInstance().getNumberGenerated()-1) + Integer.valueOf(current.getId())).setId(d2.getObjectId("_id").toString());;
        current.setId(d2.getObjectId("_id").toString());
        doc.append("sejourId", new ObjectId(current.getId()));
      } else {
        doc.append("sejourId", new ObjectId(current.getId()));
      }
      doc.append("userId", new ObjectId(GlobalData.getInstance().getLoggedInUser().getId()));
      doc.append("titre", titleComms.getText());
      doc.append("corps", contentComms.getText());
      doc.append("date", new Date());
      commentaire.insertOne(doc);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Message invalide!");
      alert.setHeaderText(null);
      alert.setContentText("Votre commentaire doit contenir un titre et un corps.");
      alert.showAndWait();
    }
  }

  public void setMainController(ListSejourController listSejourController) {
    this.mainController = listSejourController;
  }
}
