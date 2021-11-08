package models;


import org.reseau.SocketConnection;

public class User {
    private String name;
    private int id;
    private String ipAddress;

    public User(String name){

        this.name = name;
        ipAddress = SocketConnection.getSocketConn().getSocket().getLocalAddress().getHostAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
