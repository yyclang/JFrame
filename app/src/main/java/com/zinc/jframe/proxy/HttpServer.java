package com.zinc.jframe.proxy;

import com.zinc.jframe.proxy.utils.LogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/5/11
 * @description
 */

public class HttpServer implements Runnable {

    private static String TAG = "HttpServer";

    public static HttpServer httpServer;

    //客户端连接 <==> 代理 集合
    public final static Map<String, Socket> socketInMap = new HashMap<String, Socket>();
    //代理 <==> 源服务器 集合
    public final static Map<String, Socket> socketOutMap = new HashMap<String, Socket>();

    public final static ExecutorService executorPool = Executors.newCachedThreadPool();

    private int port = 12580;

    private boolean isRunning = true;

    private ServerSocket server;

    public static void startServer() {
        httpServer = new HttpServer();
        Thread thread = new Thread(httpServer);
        thread.start();
    }

    public static void stopServer() {
        httpServer.isRunning = false;
    }

    @Override
    public void run() {

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        i("port:" + port + " waiting for connect... ... ");

        while (isRunning) {

            Socket socketIn = null;
            try {
                socketIn = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String clientAddress = socketIn.getRemoteSocketAddress().toString();

            synchronized (socketInMap) {
                if (socketInMap.containsKey(clientAddress)) {
                    throw new RuntimeException("the address is exit：" + clientAddress);
                }

                socketInMap.put(clientAddress, socketIn);
            }

            executorPool.execute(new SockInThread(clientAddress));


        }

    }

    private void i(String msg) {
        LogUtil.i(TAG, msg);
    }

}
