package controllers;
import com.google.gson.Gson;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import models.Request;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
public class LoginViewController {

    private Socket socket;

    @FXML
    private TextField nameField;

    public void initSocket(Socket sock){
        socket = sock;
    }

    public void connect(){
        try{
            System.out.println("connexion");
            // Prend la valeur écrite dans le champ
            String str = nameField.getText();

            // Crée un objet Request
            Request request = new Request(Request.Type.CONN);
            request.setContent(str);

            // Crée un objet permettant l'envoie de données au serveur
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Crée un objet permettant de pouvoir transformer les objets
            // en json
            Gson gson = new Gson();
            String jsonObj = gson.toJson(request);

            // envoie la requête au serveur en format json
            out.println(jsonObj);

            // Change de scène
            //App.setRoot("main_page");

            // out.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

//    public void sendMessageTest(String s) throws IOException {
//        out.println(s);
//        String resp = in.readLine();
//        System.out.println(resp);
//    }


}
