package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Request;
import org.reseau.App;

public class MainController {

    public void createConversation(ActionEvent actionEvent) {
        // Request request = new Request();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("conversation_popup.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            Popup popup = new Popup();
            popup.show(stage);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
