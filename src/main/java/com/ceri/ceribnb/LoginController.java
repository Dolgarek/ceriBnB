package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import com.ceri.ceribnb.helper.DabatabaseHandler;
import com.ceri.ceribnb.helper.GlobalData;
import com.ceri.ceribnb.helper.SejourGenerator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DetailController detailController;

    private ListSejourController mainController;

    public void switchToHomepageScene(ActionEvent event) throws IOException {
        GlobalData.getInstance().setDetails(null);
        Parent root = null;
        if (GlobalData.getInstance().getLoggedInUser() != null) {
            if (GlobalData.getInstance().getLoggedInUser().getRole().equals("hote")) {
                root = FXMLLoader.load(getClass().getResource("authentified-view-host.fxml"));
            } else if (GlobalData.getInstance().getLoggedInUser().getRole().equals("voyageur")) {
                root = FXMLLoader.load(getClass().getResource("authentified-view.fxml"));
            }
        } else {
            root = FXMLLoader.load(getClass().getResource("unauthentified-view.fxml"));
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1445, 833);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Valider les informations de connexion
        Utilisateur utilisateur = verifierIdentifiants(email, password);
        if (utilisateur != null) {
            // Connecter l'utilisateur et ouvrir la fenêtre principale
            GlobalData.getInstance().setLoggedInUser(utilisateur);
            if (utilisateur.getRole() == "hote") {
                SejourGenerator sg = new SejourGenerator();
                GlobalData.getInstance().setOwnSejour(sg.getSejourReelByUser(new ObjectId(utilisateur.getId())));
                System.out.println(GlobalData.getInstance().getOwnSejour().size());
            }
            ouvrirFenetrePrincipale(event);
        } else {
            errorLabel.setText("Email ou mot de passe incorrect");
        }
    }

    private Utilisateur verifierIdentifiants(String email, String password) {
        Utilisateur u = Utilisateur.exist(email, password);
        // Vérifiez les informations de connexion avec la base de données
        // ou tout autre mécanisme d'authentification que vous utilisez
        // Retourne un objet Utilisateur si les identifiants sont valides, sinon retourne null
        return u;
    }

    private void ouvrirFenetrePrincipale(ActionEvent event) throws IOException {

        Parent root = null;
        if (GlobalData.getInstance().getDetails() == null) {
            // Load the new FXML file
            if (GlobalData.getInstance().getLoggedInUser().getRole().equals("hote")) {
                root = FXMLLoader.load(getClass().getResource("authentified-view-host.fxml"));
            } else if (GlobalData.getInstance().getLoggedInUser().getRole().equals("voyageur")) {
                root = FXMLLoader.load(getClass().getResource("authentified-view.fxml"));
            }
        } else {
            // Load the new FXML file
            if (GlobalData.getInstance().getLoggedInUser().getRole().equals("hote")) {
                //root = FXMLLoader.load(getClass().getResource("detail-view-host.fxml"));
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detail-view-host.fxml"));
                root = fxmlLoader.load();
                detailController = fxmlLoader.getController();
                detailController.setMainController(this.mainController);
            } else if (GlobalData.getInstance().getLoggedInUser().getRole().equals("voyageur")) {
                //root = FXMLLoader.load(getClass().getResource("detail-view.fxml"));
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detail-view.fxml"));
                root = fxmlLoader.load();
                detailController = fxmlLoader.getController();
                detailController.setMainController(this.mainController);
            }
        }

        // Create a new Scene object
        Scene homepageAuthentified = new Scene(root);

        // Get the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene to the current stage
        currentStage.setScene(homepageAuthentified);
        currentStage.show();
    }

    public void setMainController(ListSejourController listSejourController) {
        this.mainController = listSejourController;
    }
}
