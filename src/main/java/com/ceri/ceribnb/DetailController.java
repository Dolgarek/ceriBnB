package com.ceri.ceribnb;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.ceri.ceribnb.entity.Commentaire;
import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import com.ceri.ceribnb.helper.CommentairesListCell;
import com.ceri.ceribnb.helper.DabatabaseHandler;
import com.ceri.ceribnb.helper.GlobalData;
import com.ceri.ceribnb.helper.SejourListCell;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import static com.mongodb.client.model.Filters.eq;

public class DetailController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  ListView<Commentaire> commentaireListView;

  private ObservableList<Commentaire> commentaires;

  private ListSejourController mainController;

  private CartController cartController;

  private ReservationController reservationController;

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
    MongoDatabase database = DabatabaseHandler.instanciateDatabase();
    MongoCollection<Document> commentaire = database.getCollection("Commentaire");
    MongoCollection<Document> user = database.getCollection("Utilisateur");

    DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
    current = GlobalData.getInstance().getDetails();
    Date dateDebut = sourceFormat.parse(current.getDateDebut());
    Date dateFin = sourceFormat.parse(current.getDateFin());
    long diffInMillies = Math.abs(dateFin.getTime() - dateDebut.getTime());
    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    String realValue = String.valueOf(diff * current.getPrix());

    contentComms.setWrapText(true);

    titreSejour.setText(current.getTitre());
    imgSejour.setImage(current.getImage());
    descSejour.setText(current.getAdresse() + "\n" + current.getPays() + ", " + current.getVille() + ", " + current.getCodeZip() + "\n\n" + current.getDescription());
    descSejour.setWrapText(true);
    prixSejour.setText("Prix: " + String.valueOf(current.getPrix()) + "€/nuits" + " ~ " + realValue + "€");
    debutSejour.setText(current.getDateDebut());
    finSejour.setText(current.getDateFin());

    List<Commentaire> commentaireList = new ArrayList<>();

    if (current.getId().length() > 5) {
      for (Document d : commentaire.find(eq("sejourId", new ObjectId(current.getId())))) {
        Commentaire c = new Commentaire();
        Document du = user.find(eq("_id", d.getObjectId("userId"))).first();
        Utilisateur u = new Utilisateur(du.getObjectId("_id").toString(), du.getString("mail"), du.getString("nom"), du.getString("prenom"), du.getString("adresse"), du.getString("ville"), du.getString("codeZip"), du.getString("pays"), du.getString("role"), du.getString("password"));
        c.setAuteur(u);
        c.setSejour(current);
        c.setDate(d.getString("date"));
        c.setTitre(d.getString("titre"));
        c.setContent(d.getString("corps"));
        commentaireList.add(c);
      }
    }


    commentaires = FXCollections.observableArrayList(commentaireList);

    commentaireListView.setItems(commentaires);
    commentaireListView.setCellFactory(commentaireSimple -> new CommentairesListCell(this));
  }

  public void switchToCart(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
    Parent root = fxmlLoader.load();
    cartController = fxmlLoader.getController();
    cartController.setMainController(this.mainController);
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("Mon panier");
    stage.setScene(scene);
    stage.show();  }

  public void switchToBooking(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list-reservation.fxml"));
    Parent root = fxmlLoader.load();
    reservationController = fxmlLoader.getController();
    reservationController.setMainController(this.mainController);
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("Demandes de réservation");
    stage.setScene(scene);
    stage.show();
  }

  public void switchToBookingRequestsScene(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("booking-requests-view.fxml"));
    Parent root = fxmlLoader.load();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("Demandes de réservation");
    stage.setScene(scene);
    stage.show();
  }

  public void switchToCalendarScene(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("calendar-view.fxml"));
    Parent root = fxmlLoader.load();
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root, 1445, 833);
    stage.setTitle("Demandes de réservation");
    stage.setScene(scene);
    stage.show();
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

  public void addCommentaire(ActionEvent e) throws ParseException {
    MongoDatabase database = DabatabaseHandler.instanciateDatabase();
    MongoCollection<Document> sejourReel = database.getCollection("SejourReel");
    MongoCollection<Document> commentaire = database.getCollection("Commentaire");
    MongoCollection<Document> user = database.getCollection("Utilisateur");
    Document doc = new Document();

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRENCH);
    Date date = new Date();

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
      doc.append("date", df.format(date));
      commentaire.insertOne(doc);

      titleComms.setText("");
      contentComms.setText("");

      List<Commentaire> commentaireList = new ArrayList<>();


      for (Document d : commentaire.find(eq("sejourId", new ObjectId(current.getId())))) {
        Commentaire c = new Commentaire();
        Document du = user.find(eq("_id", d.getObjectId("userId"))).first();
        Utilisateur u = new Utilisateur(du.getObjectId("_id").toString(), du.getString("mail"), du.getString("nom"), du.getString("prenom"), du.getString("adresse"), du.getString("ville"), du.getString("codeZip"), du.getString("pays"), du.getString("role"), du.getString("password"));
        c.setAuteur(u);
        c.setSejour(current);
        c.setDate(d.getString("date"));
        c.setTitre(d.getString("titre"));
        c.setContent(d.getString("corps"));
        commentaireList.add(c);
      }

      commentaires = FXCollections.observableArrayList(commentaireList);

      commentaireListView.setItems(commentaires);
      commentaireListView.setCellFactory(commentaireSimple -> new CommentairesListCell(this));
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
