package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Request;
import models.UserConv;
import org.reseau.App;
import org.reseau.SocketConnection;

public class MainController implements Initializable {

    public TextField chatTextField;
    public ListView conversationsList;
    public ListView messageList;


    private ObservableList<UserConv> userConvList = FXCollections.observableArrayList();

    public void createConversation() throws IOException {
        App.setRoot("conversation_popup");
    }

    public void initListConv(Request response) {
        response.getListUserConv().forEach(conv -> {
            conv.getListUsers().forEach(u -> {
                System.out.println();
            });
        });
        // arrUserList = response.getListUsers();
        response.getListUserConv().forEach(conv -> {
            conversationsList.getItems().add(conv.getListUsers());
        });
//        conversationsList.getSelectionModel().selectedItemProperty().addListener((ChangeListener<UserConv>) (observable, oldValue, newValue) -> {
//            System.out.println("cliquéééééé");
//        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread thread = new Thread(() -> {
            try {
//                while(App.getUser() == null){
//                    // attendre de recevoir la réponse
//                    //todo: régler le problème éventuellement (observateur)
//                }
                Request request = new Request(Request.Type.GET_CONV, App.getUser().getId(), 0, "");

                // Crée un objet permettant l'envoie de données au serveur
                PrintWriter out = new PrintWriter(SocketConnection.getSocketConn().getSocket().getOutputStream(), true);

                // Crée un objet permettant de pouvoir transformer les objets
                // en json
                Gson gson = new Gson();
                String jsonObj = gson.toJson(request);

                // envoie la requête au serveur en format json
                out.println(jsonObj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void openConversation(MouseEvent mouseEvent) {
//        try{
//            System.out.println(conversationsList.getSelectionModel().getSelectedItem().toString());
//            // Crée un objet Request
//            Request request = new Request(Request.Type.CONN, 0, 0, str);
//
//            // Crée un objet permettant l'envoie de données au serveur
//            PrintWriter out = new PrintWriter(SocketConnection.getSocketConn().getSocket().getOutputStream(), true);
//
//            // Crée un objet permettant de pouvoir transformer les objets
//            // en json
//            Gson gson = new Gson();
//            String jsonObj = gson.toJson(request);
//
//            // envoie la requête au serveur en format json
//            out.println(jsonObj);
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
    }
}
