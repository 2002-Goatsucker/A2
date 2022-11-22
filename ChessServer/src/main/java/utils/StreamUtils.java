package utils;

import java.io.*;
import java.net.Socket;

public class StreamUtils {
    private StreamUtils(){}

    public static BufferedWriter getWriter(Socket socket){
        OutputStream outputStream;
        BufferedWriter writer=null;
        try {
            outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
            writer=new BufferedWriter(outputStreamWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    public static BufferedReader getReader(Socket socket){
        InputStream inputStream;
        BufferedReader reader=null;
        try {
            inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            reader=new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reader;
    }
}
