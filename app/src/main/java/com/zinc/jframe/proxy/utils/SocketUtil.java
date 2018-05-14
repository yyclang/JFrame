package com.zinc.jframe.proxy.utils;

import com.zinc.jframe.proxy.HttpServer;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by zinc on 2018/5/12.
 */
public class SocketUtil {

    public static void removeSocket(String address) {
        synchronized (HttpServer.socketInMap) {
            Socket socket = HttpServer.socketInMap.remove(address);
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        synchronized (HttpServer.socketOutMap) {
            Socket socket = HttpServer.socketOutMap.remove(address);
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
