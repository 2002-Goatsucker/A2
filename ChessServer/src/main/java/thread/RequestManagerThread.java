package thread;

import controller.RoomManager;
import controller.SocketManager;
import utils.GameThreadPool;
import utils.StreamUtils;

import java.io.*;
import java.net.Socket;

//建议设置为守护线程，用以处理所有接收到的socket对象。
public class RequestManagerThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = SocketManager.getSocket();
                //输入流
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //输出流
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                receiveAction(writer,reader,socket);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void receiveAction(BufferedWriter writer,BufferedReader reader,Socket socket) throws IOException {
        String action = reader.readLine();
        if (action.equals("action:create")) {
            GameThread gameThread = GameThreadPool.getThread();
            if (gameThread != null) {
                String user=reader.readLine();
                String name=reader.readLine();
                //读取房间名
                boolean isCreate = RoomManager.createRoom(name, gameThread);
                //读取用户名
                if(isCreate) {
                    gameThread.setPlayer1(user);
                    gameThread.setSocket1(socket);
                    writer.write("success");
                    writer.newLine();
                    writer.flush();
                }else {
                    writer.write("failed");
                    writer.newLine();
                    writer.flush();
                    socket.close();
                }
            } else {
                writer.write("failed");
                writer.newLine();
                writer.flush();
                socket.close();
            }
        } else if (action.equals("action:attend")) {
            //读取房间名，用户名
            String room=reader.readLine();
            String user = reader.readLine();
            boolean isAttend = RoomManager.attendRoom(room, user, socket);
            if (isAttend) {
                writer.write("success");
                writer.newLine();
                writer.flush();
                RoomManager.rooms.get(room).start();
            } else {
                writer.write("failed");
                writer.newLine();
                writer.flush();
                socket.close();
            }
        }else if(action.equals("room")){
//                    System.out.println(RoomManager.rooms.size());
            writer.write(RoomManager.rooms.size()+"");
            writer.newLine();
            writer.flush();

            for(String name:RoomManager.rooms.keySet()){
                writer.write(name);
                writer.newLine();
                writer.flush();
            }
            receiveAction(writer,reader,socket);
        }
    }


}
