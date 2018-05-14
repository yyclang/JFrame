package com.zinc.jframe.proxy;

import com.zinc.jframe.proxy.utils.LogUtil;
import com.zinc.jframe.proxy.utils.SocketUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author: zinc
 * @Describe: 将 源服务器的socket输入流 传给 客户端的socket的输出流【源服务器======》客户端】
 * @Date: Created in 下午8:27 2018/5/12
 * @Modified By:
 */

public class SockOutThread implements Runnable {

    private Socket socketIn;
    private Socket socketOut;

    private String address;

    public SockOutThread(String address) {

        this.address = address;

        synchronized (HttpServer.socketInMap) {
            socketIn = HttpServer.socketInMap.get(address);
            if (socketIn == null) {
                throw new RuntimeException("Miss the client socket in SockOutThread.");
            }
        }

        synchronized (HttpServer.socketOutMap) {
            socketOut = HttpServer.socketOutMap.get(address);
            if (socketOut == null) {
                throw new RuntimeException("Miss the remote socket in SockOutThread.");
            }
        }

    }

    @Override
    public void run() {

        try {
            InputStream isOut = socketOut.getInputStream();
            OutputStream osIn = socketIn.getOutputStream();

            byte[] buffer = new byte[2 * 1024];

            int len;
            while ((len = isOut.read(buffer)) != -1) {
                if (len > 0) {
//                    LogUtil.i("SockOutThread", "给客户<<<" + new String(buffer, 0, len));
                    osIn.write(buffer, 0, len);
                    osIn.flush();
                }
                if (socketIn.isOutputShutdown() || socketOut.isClosed()) {
                    break;
                }
            }

        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            SocketUtil.removeSocket(address);
        }

    }

}
