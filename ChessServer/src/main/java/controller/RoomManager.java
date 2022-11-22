package controller;

import thread.GameThread;

import java.net.Socket;
import java.util.HashMap;

public class RoomManager {
    public static HashMap<String, GameThread> rooms = new HashMap<>();
    public static boolean createRoom(String name,GameThread thread){
        if(rooms.containsKey(name)) return false;
        rooms.put(name,thread);
        return true;
    }

    public static boolean attendRoom(String name, String user, Socket socket){
        if(rooms.get(name).getPlayer2()==null){
            rooms.get(name).setPlayer2(user);
            rooms.get(name).setSocket2(socket);
            return true;
        }else return false;
    }
}
