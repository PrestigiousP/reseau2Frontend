package org.reseau;

import com.google.gson.Gson;
import models.Request;
import models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private BufferedReader input;
    private Gson gson;

    public ClientThread(Socket s) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        gson = new Gson();
    }

    @Override
    public void run(){
        try{
            while (true) {
                String resp = input.readLine();
                System.out.println("the resp is: "+resp);
                Request response = gson.fromJson(resp, Request.class);
                System.out.println("passed here, also response type is: "+response.getType());
                System.out.println("passed here, also response id is: "+response.getClientId());
                System.out.println("passed here, also response content is: "+response.getContent());

                switch (response.getType()){
                    case SEND_ID: {
                        User user = new User(response.getContent());
                        user.setId(response.getClientId());
                        App.setUser(user);
                        System.out.println(resp);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
