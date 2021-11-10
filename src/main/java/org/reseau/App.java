package org.reseau;

import com.google.gson.Gson;
import controllers.LoginViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static FXMLLoader fxmlLoader;
    private static User user;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login_view"));
        LoginViewController loginViewController = fxmlLoader.getController();
        loginViewController.initSocket(SocketConnection.getSocketConn().getSocket());
        stage.setScene(scene);
        stage.show();

//        // serverConnection();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login_view.fxml"));
//        Parent root = fxmlLoader.load();
//        LoginView loginViewController = fxmlLoader.getController();
//        loginViewController.initSocket(SocketConnection.getSocketConn().getSocket());
//        // loginViewController.test();
//        primaryStage.setTitle("Chat TP2 réseaux");
//        primaryStage.setScene(new Scene(root, 800, 600));
//        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        App.fxmlLoader = fxmlLoader;
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setUser(User user){
        App.user = user;
    }

    public static User getUser(){
        return user;
    }
    public static FXMLLoader getFxmlLoader(){
        return fxmlLoader;
    }

}
