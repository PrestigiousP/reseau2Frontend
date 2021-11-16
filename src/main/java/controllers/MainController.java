package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Message;
import models.Request;
import models.UserConv;
import org.reseau.App;
import org.reseau.SocketConnection;

public class MainController implements Initializable {

    public TextField chatTextField;
    public ListView conversationsList;
    public ListView messageList;
    private ArrayList<Integer> listIds;
    private ArrayList<Message> messageArrayList;
    private ObservableList<Message> messageObservableList = FXCollections.observableArrayList();
    private ArrayList<UserConv> userConvList;
    private ObservableList<UserConv> userConvObservableList = FXCollections.observableArrayList();

    public ObservableList<Message> getMessageObservableList(){
        return messageObservableList;
    }

    public ObservableList<UserConv> getObservableUserConvList(){
        return userConvObservableList;
    }

    public void createConversation() throws IOException {
        // App.setRoot("conversation_popup");
        App.setRoot("create_conv_person");
    }

    public void initListConv() {
        userConvList =  new ArrayList<>();
        userConvObservableList.forEach(userConv -> {
            if(userConv.getUser_id() == -1){
                conversationsList.getItems().add("broadcast");
            }
            else{
                conversationsList.getItems().add(userConv.getListUsers());
            }
            userConvList.add(userConv);
        });
    }

    public void initListMessage(){

        messageArrayList = new ArrayList<>();

        messageObservableList.forEach(message -> {
            messageList.getItems().add(message.getSenderName()+ ": "+message.getBody());
            messageArrayList.add(message);
        });
    }

    public void updateConversations(){
        conversationsList.getItems().clear();
        userConvObservableList.forEach(userConv -> {
            if(userConv.getUser_id() == -1){
                conversationsList.getItems().add("broadcast");
            }
            else{
                conversationsList.getItems().add(userConv.getListUsers());
            }
        });
        conversationsList.refresh();
    }

    public void updateMessages(Request response) {
        System.out.println("reçoit message: "+response.getListMessages().get(0).getBody());
        messageList.getItems().clear();
        messageObservableList.forEach(message -> messageList.getItems().add(message.getSenderName()+ ": "+message.getBody()));
        messageList.refresh();
        System.out.println("voici la longueur de la liste de messages: "+ messageList.getItems().size());
        System.out.println("voici la longueur de la liste d'observables: "+messageObservableList.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                Request request = new Request(Request.Type.GET_CONV);
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

    public void openConversation() {
        try{
            messageList.getItems().clear();
            messageObservableList.clear();
            System.out.println("clicked " + conversationsList.getSelectionModel().getSelectedItem());
            if(this.userConvList != null){
                int index = conversationsList.getSelectionModel().getSelectedIndex();
                if(index != -1){
                    UserConv userConv = userConvList.get(index);
                    // Crée un objet Request
                    Request request = new Request(Request.Type.GET_MESS);
                    request.setClientId(App.getUser().getId());
                    System.out.println("liste des users id: "+ userConv.getListUsersId());
                    request.setListUserConvId(userConv.getListUsersId());
                    listIds = userConv.getListUsersId();

                    // Crée un objet permettant l'envoie de données au serveur
                    PrintWriter out = new PrintWriter(SocketConnection.getSocketConn().getSocket().getOutputStream(), true);

                    // Crée un objet permettant de pouvoir transformer les objets
                    // en json
                    Gson gson = new Gson();
                    String jsonObj = gson.toJson(request);

                    // envoie la requête au serveur en format json
                    out.println(jsonObj);

                    chatTextField.setVisible(true);
                }
            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void listenKeyPressed(KeyEvent keyEvent) {
        try{
            if(keyEvent.getCode().toString().equals("ENTER")){
                System.out.println("user clicked enter");
                // Crée un objet Request
                Request request = new Request(Request.Type.SEND_MESS);
                request.setClientId(App.getUser().getId());
                // Message message = new Message(chatTextField.getText(), App.getUser().getId());
                request.setListUserConvId(listIds);
                request.setContent(chatTextField.getText());

                // request.setContent(conversationsList.getSelectionModel().getSelectedItem().toString());

                // Crée un objet permettant l'envoie de données au serveur
                PrintWriter out = new PrintWriter(SocketConnection.getSocketConn().getSocket().getOutputStream(), true);

                // Crée un objet permettant de pouvoir transformer les objets
                // en json
                Gson gson = new Gson();
                String jsonObj = gson.toJson(request);

                // envoie la requête au serveur en format json
                out.println(jsonObj);
                // System.out.println(chatTextField.getText());
                chatTextField.clear();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
