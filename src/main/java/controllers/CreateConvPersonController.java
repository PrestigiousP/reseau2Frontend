package controllers;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import models.Request;
import models.User;
import models.UserConv;
import org.reseau.App;
import org.reseau.SocketConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateConvPersonController implements Initializable {

    public Button createButton;
    private ArrayList<User> arrUserList;

    @FXML
    private ListView userList;

    public void initListView(Request response){
        System.out.println("la liste de users est: "+response.getListUsers());
        System.out.println("resp: "+response.getListUsers());
        // arrUserList = response.getListUsers();
        response.getListUsers().forEach(u ->{
            if(!u.getName().equals(App.getUser().getName()) && !u.getName().equals("broadcast")){
                userList.getItems().add(u.getName());
            }
        });
    }

    public void createConversation() throws IOException {
        Request request = new Request(Request.Type.CREATE_CONV_PERSON);
        request.setClientId(App.getUser().getId());
        userList.getSelectionModel().getSelectedItems().forEach(item -> {
            request.addUsersNameList(item.toString());
        });
        // request.setContent(userList.getSelectionModel().getSelectedItem().toString());

        // Crée un objet permettant l'envoie de données au serveur
        PrintWriter out = new PrintWriter(SocketConnection.getSocketConn().getSocket().getOutputStream(), true);

        // Crée un objet permettant de pouvoir transformer les objets
        // en json
        Gson gson = new Gson();
        String jsonObj = gson.toJson(request);

        // envoie la requête au serveur en format json
        out.println(jsonObj);
        App.setRoot("main_page");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            userList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            Request request = new Request(Request.Type.GET_USERS);
            request.setClientId(App.getUser().getId());
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

    }

    public void returnMainView(ActionEvent actionEvent) throws IOException {
        App.setRoot("main_page");
    }
}
