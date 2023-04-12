package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLoginButtonAction() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Valider les informations de connexion
        Utilisateur utilisateur = verifierIdentifiants(email, password);
        if (utilisateur != null) {
            // Connecter l'utilisateur et ouvrir la fenêtre principale
            ouvrirFenetrePrincipale(utilisateur);
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

    private void ouvrirFenetrePrincipale(Utilisateur utilisateur) {
        System.out.println("User " + utilisateur.getNom() + utilisateur.getPrenom() + utilisateur.toString());
        // Ouvrez la fenêtre principale de l'application et transmettez l'objet Utilisateur
    }
}
