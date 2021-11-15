package org.reseau;

import com.google.gson.Gson;
import controllers.*;
import javafx.application.Platform;
import models.Request;
import models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader input;
    private Gson gson;

    public ClientThread(Socket s) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        gson = new Gson();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String resp = input.readLine();
                // System.out.println("the resp is: "+resp);
                Request response = gson.fromJson(resp, Request.class);
                if (response != null) {
                    switch (response.getType()) {
                        case SEND_ID:
                            User user = new User(response.getContent());
                            user.setId(response.getClientId());
                            App.setUser(user);
                            // System.out.println(resp);
                            break;
                        case CONN_COMPLETE:
                            User user1 = new User(response.getContent());
                            user1.setId(response.getClientId());
                            App.setUser(user1);
                            System.out.println("user existe et connexion est complété " + resp);
                            System.out.println("voici le user: " + App.getUser().getName() + " id: " + App.getUser().getId());
                            App.setRoot("main_page");
                            break;
                        case SEND_USERS:
                            // System.out.println("passe par ici " + response.getType());
                            CreateConvPersonController createConvPersonController = App.getFxmlLoader().getController();
                            createConvPersonController.initListView(response);
                            break;
                        case GET_CONV:
                            System.out.println("GET_CONV received");
                            MainController maincontroller = App.getFxmlLoader().getController();
                            response.getListUserConv().forEach(userconv -> maincontroller.getObservableUserConvList().add(userconv));
                            maincontroller.initListConv();
                            break;
                        case GET_MESS:
                            Platform.runLater(() -> {
                                MainController mainController = App.getFxmlLoader().getController();
                                response.getListMessages().forEach(message -> mainController.getMessageObservableList().add(message));
                                mainController.initListMessage();
                            });
                            break;
                        case RECEIVE_MESS:
                            Platform.runLater(() -> {
                                MainController mainController1 = App.getFxmlLoader().getController();
                                // On sait qu'on reçoit seulement un seul message, donc get(0)
                                System.out.println("on reçoit un message ici: "+ response.getListMessages().get(0));
                                mainController1.getMessageObservableList().add(response.getListMessages().get(0));
                                mainController1.updateMessages(response);
                            });
                            break;
                        case CREATE_CONV_PERSON:
                            Platform.runLater(() -> {
                                MainController mainController = App.getFxmlLoader().getController();
                                response.getListUserConv().forEach(userConv -> mainController.getObservableUserConvList().add(userConv));
                                // mainController.getObservableUserConvList().add(response.getListUserConv());
                                mainController.updateConversations();
                            });
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
