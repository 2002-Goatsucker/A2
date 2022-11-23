package com.example.chessclient.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConstantPool {
    public static String userName;
    
    static {
        if(userName==null) {
            try {
                userName = "Player" + InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
