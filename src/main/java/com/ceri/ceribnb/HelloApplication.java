package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Sejour;
import com.ceri.ceribnb.entity.Utilisateur;
import com.ceri.ceribnb.helper.SejourGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HelloApplication extends Application {
    SejourGenerator sg = new SejourGenerator();

    List<Utilisateur> users = sg.getUtilisateurs();

    List<Image> images;

    List<Sejour> test;

    //public void start(Stage stage) throws IOException {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("detail-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1445, 833);
        stage.setTitle("CeriBnB");
        stage.setScene(scene);
        stage.show();

        /*for (int i = 1; i <= 9; i++) {
            Image image = new Image(getClass().getResourceAsStream("/img/" + i + ".png"));
            images.add(image);
        }
        test = sg.genererSejours(10000, users, images);
        for (Sejour s: test) {
            System.out.println("Sejour = " + s.getTitre() + " " + s.getDescription() + " " + s.getPrix());
        }*/
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/

        /*Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        primaryStage.setTitle("Connexion");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/

        /*Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("list-sejour.fxml")));
        primaryStage.setTitle("Liste");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }
}