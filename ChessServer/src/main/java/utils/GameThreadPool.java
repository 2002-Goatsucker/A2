package utils;

import thread.GameThread;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameThreadPool {
    private static Map<Integer, GameThread> pool;
    private static Integer size;
    private static Integer accessibleThread;
    static {
        ResourceBundle bundle=ResourceBundle.getBundle("config");
        size = Integer.parseInt(bundle.getString("chessServer.utils.ThreadPool.size"));
        accessibleThread = size;
        pool = new HashMap<>(size);
        for(int i=0;i<size;++i){
            pool.put(i,new GameThread());
        }
    }

    public static void refresh(){
        for(int i=0;i<size;++i){
            GameThread temp=pool.get(i);
            if(!temp.isAlive()){
                pool.put(i,new GameThread());
            }
        }
    }

    public static GameThread getThread(){
        if(accessibleThread<=0) return null;
        for(int i=0;i<size;++i){
            if(pool.get(i).isFree()){
                pool.get(i).setFree(false);
                accessibleThread-=1;
                return pool.get(i);
            }
        }
        return null;
    }

}
