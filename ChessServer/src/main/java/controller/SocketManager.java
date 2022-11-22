package controller;

import thread.RequestManagerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketManager {

    private static ServerSocket serverSocket;
    private static final BlockingQueue<Socket> blockedSocket=new LinkedBlockingQueue<>();

    private SocketManager(){};


    public static void welcomeSocket(int port){
        try {
            serverSocket=new ServerSocket(port);
            int num= Integer.parseInt(ResourceBundle.getBundle("config").getString("chessServer.utils.ThreadPool.size"));
            for(int i=0;i<num;++i){
                RequestManagerThread thread=new RequestManagerThread();
                thread.start();
            }
            while (true){
                Socket socket=serverSocket.accept();
                blockedSocket.add(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() throws InterruptedException {
        return blockedSocket.take();
    }





}
