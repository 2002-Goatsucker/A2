package com.example.chessclient.utils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketUtils {
    private static BufferedWriter writer;
    private static BufferedReader reader;

    private static ObjectInputStream objectInputStream;

    private SocketUtils() {
    }
    private static Socket socket;

    static {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 8001);
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            writer = new BufferedWriter(osw);
//            objectInputStream = new ObjectInputStream(socket.getInputStream());

            InputStream inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            reader = new BufferedReader(isr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMess(String mess) throws IOException {
        writer.write(mess);
        writer.newLine();
        writer.flush();
    }

    public static String receive() throws IOException {
        return reader.readLine();
    }

    public static Socket buildConnection() {
        return socket;
    }

    public static Object receiveObj() throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }


}
