package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Reservation;
import com.ceri.ceribnb.helper.DabatabaseHandler;
import com.ceri.ceribnb.helper.GlobalData;
import com.ceri.ceribnb.helper.SejourGenerator;
import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import com.ceri.ceribnb.helper.SejourListCell;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.Gson;

import static com.mongodb.client.model.Filters.eq;

public class ListSejourController {

    @FXML
    private ListView<Sejour> sejourListView;
    private List<Image> images = new ArrayList<>();
    private Random random = new Random();

    private CartController cartController;

    private ReservationController resaController;

    private DetailController detailController;

    private ObservableList<Sejour> sejours;

    private Set<Sejour> cartItems = new HashSet<>();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button add_to_cart;

    @FXML
    private Button user_book;

    @FXML
    private Button all_sejour;

    @FXML
    private Button my_sejour;

    @FXML
    private Button authentified_username;

    public int state = 0;

    public void switchToLoginFormScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1445, 833);
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDetailScene(ActionEvent event, Sejour sejour) throws IOException {
        GlobalData.getInstance().setDetails(sejour);
        //Parent root = FXMLLoader.load(getClass().getResource("detail-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detail-view.fxml"));
        Parent root = fxmlLoader.load();
        detailController = fxmlLoader.getController();
        detailController.setMainController(this);
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
    
    public void initialize() {
        //TODO: Supprimer après;
        if (GlobalData.getInstance().getOwnSejour() == null && GlobalData.getInstance().getLoggedInUser() != null) {
         SejourGenerator sg = new SejourGenerator();
         GlobalData.getInstance().setOwnSejour(sg.getSejourReelByUser(new ObjectId(GlobalData.getInstance().getLoggedInUser().getId())));
        }
        if (user_book != null) {
            user_book.setOnAction(e -> {
                try {
                    showReservation(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        if (add_to_cart != null) {
            add_to_cart.setOnAction(e -> {
                try {
                    showCart(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        if (all_sejour != null) {
            all_sejour.setOnAction(e -> displayAllSejour(e));
        }
        if (my_sejour != null) {
            my_sejour.setOnAction(e -> displayMySejour(e));
        }
        if (authentified_username != null) {
            authentified_username.setOnAction(e -> {
                try {
                    logout(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        for (int i = 1; i <= 9; i++) {
            Image image = new Image(getClass().getResourceAsStream("/img/" + i + ".png"));
            images.add(image);
        }

        CartController cartController = new CartController();

        cartItems = GlobalData.getInstance().getCart();
        if (cartItems == null) {
            cartItems = new HashSet<>();
        }

        SejourGenerator sg = new SejourGenerator();
        List<Sejour> test = GlobalData.getInstance().getSejours();
        if (test == null) {
            List<Utilisateur> users = sg.getUtilisateurs();
            test = sg.genererSejours(10000, users, images);
            GlobalData.getInstance().setSejours(test);
        }

        sejours = FXCollections.observableArrayList(test);

        sejourListView.setItems(sejours);
        sejourListView.setCellFactory(sejour -> new SejourListCell(this, cartController, false));
    }

    public Image getRandomImage() {
        return images.get(random.nextInt(images.size()));
    }

    public void addToCart(Sejour sejour) {
        if (cartItems.add(sejour)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Séjour ajouté au panier");
            alert.setHeaderText(null);
            alert.setContentText("Le séjour a été ajouté au panier.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Séjour déjà dans le panier");
            alert.setHeaderText(null);
            alert.setContentText("Le séjour est déjà présent dans le panier.");
            alert.showAndWait();
        }
        GlobalData.getInstance().setCart(cartItems);
    }

    public ObservableList<Sejour> getCartItems() { return FXCollections.observableArrayList(cartItems); }

    public void removeFromCart(Sejour sejour) {
        cartItems.remove(sejour);
        GlobalData.getInstance().setCart(cartItems);
    }

    public ObservableList<Sejour> validateOrder() {
        MongoDatabase database = DabatabaseHandler.instanciateDatabase();
        MongoCollection<Document> reservation = database.getCollection("Reservation");
        MongoCollection<Document> sejourReel = database.getCollection("SejourReel");
        for (Sejour s : cartItems) {
            Reservation r = new Reservation();
            Gson gson = new Gson();
            if (s.getId().length() <= 5) {
                /*Sejour manip = s;
                manip.setId(new ObjectId().toString());
                r.setSejourId(new ObjectId(manip.getId()));*/

                Document d2 = new Document();
                d2.append("_id", new ObjectId());
                d2.append("hoteId", new ObjectId(s.getHote().getId()));
                d2.append("titre", s.getTitre());
                d2.append("description", s.getDescription());
                d2.append("adresse", s.getAdresse());
                d2.append("ville", s.getVille());
                d2.append("Pays", s.getPays());
                d2.append("codeZip", s.getCodeZip());
                d2.append("prix", String.valueOf(s.getPrix()));
                d2.append("img", s.getImgPath());
                d2.append("dateDebut", s.getDateDebut());
                d2.append("dateFin", s.getDateFin());
                sejourReel.insertOne(d2);
                r.setSejourId(d2.getObjectId("_id"));

            } else {
                r.setSejourId(new ObjectId(s.getId()));
            }
            r.setUserId(new ObjectId(s.getHote().getId()));
            r.setStatus("EN ATTENTE");
            Document d = new Document();
            d.append("_id", new ObjectId());
            d.append("sejourId", r.getSejourId());
            d.append("userId", r.getUserId());
            d.append("status", r.getStatus());
            reservation.insertOne(d);
        }
        sejours.removeAll(cartItems);
        cartItems.clear();
        GlobalData.getInstance().setCart(cartItems);
        GlobalData.getInstance().setSejours(sejours.stream().toList());
        // Retournez à la liste des séjours après avoir validé la commande.
        return sejours;
    }

    @FXML
    public void showCart(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
        Parent root = fxmlLoader.load();
        cartController = fxmlLoader.getController();
        cartController.setMainController(this);

        // Create a new Scene object
        Scene cartList = new Scene(root);

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene to the current stage
        currentStage.setScene(cartList);
        currentStage.show();
    }

    @FXML
    public void showReservation(ActionEvent event) throws IOException {
        // Load the new FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list-reservation.fxml"));
        Parent root = fxmlLoader.load();
        resaController = fxmlLoader.getController();
        resaController.setMainController(this);

        // Create a new Scene object
        Scene resaList = new Scene(root);

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene to the current stage
        currentStage.setScene(resaList);
        currentStage.show();
    }

    public void displayAllSejour(ActionEvent e) {
        if (state == 1) {
            sejours = FXCollections.observableArrayList(GlobalData.getInstance().getSejours());

            sejourListView.setItems(sejours);
            sejourListView.setCellFactory(sejour -> new SejourListCell(this, cartController, false));
            state = 0;
        }
    }

    public void displayMySejour(ActionEvent e) {
        if (state == 0) {
            sejours = FXCollections.observableArrayList(GlobalData.getInstance().getOwnSejour());

            sejourListView.setItems(sejours);
            sejourListView.setCellFactory(sejour -> new SejourListCell(this, cartController, false));

            state = 1;
        }
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
