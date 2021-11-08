package org.reseau;

import java.io.IOException;
import java.net.Socket;

public class SocketConnection {

    private static SocketConnection socketConn = new SocketConnection();
    private Socket socket;
    private ClientThread clientThread;

    private SocketConnection(){
        try{
            socket = new Socket("127.0.0.1", 9991);
//            out = new PrintWriter(socket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientThread = new ClientThread(socket);
            clientThread.start();
//        sendMessageTest("test");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static SocketConnection getSocketConn(){
        return socketConn;
    }

    public Socket getSocket(){
        return socket;
    }

}
