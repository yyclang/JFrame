package com.zinc.jframe.proxy;


import com.zinc.jframe.proxy.model.Message;
import com.zinc.jframe.proxy.utils.LogUtil;
import com.zinc.jframe.proxy.utils.SocketUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zinc on 2018/5/12.
 */
public class SockInThread implements Runnable {

    private static final String TAG = "SockInThread";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 已连接到请求的服务器
     */
    private static final String AUTHORED = "HTTP/1.1 200 Connection established\r\n\r\n";
    /**
     * 本代理登陆失败
     */
    private static final String UNAUTHORED = "HTTP/1.1 407 Unauthorized\r\n\r\n";
    /**
     * 内部错误
     */
    private static final String SERVERERROR = "HTTP/1.1 500 Connection FAILED\r\n\r\n";

    private String address;

    private Socket socketIn;
    private Socket socketOut;

    private HttpCodec httpCodec;

    private Message message;

    public SockInThread(String address) {
        this.address = address;

        synchronized (HttpServer.socketInMap) {
            socketIn = HttpServer.socketInMap.get(address);
            if (socketIn == null) {
                throw new RuntimeException("The socket of address(" + address + ") is not create.");
            }
        }

        httpCodec = new HttpCodec();
        message = new Message();

    }

    @Override
    public void run() {

        try {

            //与客户端的输入流
            InputStream isIn = socketIn.getInputStream();
            //与客户端的输入流
            OutputStream osIn = socketIn.getOutputStream();

            //读取状态
            httpCodec.readStateLine(isIn, message);

            //读取头部
            httpCodec.readHeaders(isIn, message);

            i("Request Time  ：" + sdf.format(new Date()));
            i("From    Host  ：" + socketIn.getInetAddress());
            i("From    Port  ：" + socketIn.getPort());
            i("Proxy   Method：" + message.getMethod());
            i("Request Host  ：" + message.getHost());
            i("Request Port  ：" + message.getPort());

            //如果没解析出请求请求地址和端口，则返回错误信息
            if (message.getHost() == null || message.getPort() == null) {
                osIn.write(SERVERERROR.getBytes());
                osIn.flush();
                return;
            }

            //创建与源服务器的连接
            socketOut = new Socket(message.getHost(), Integer.parseInt(message.getPort()));
            socketOut.setKeepAlive(true);

            OutputStream osOut = socketOut.getOutputStream();

            //将线程添加至集合
            synchronized (HttpServer.socketOutMap) {
                if (HttpServer.socketOutMap.containsKey(address)) {
                    throw new RuntimeException("The address had connect the site(" + message.getHost() + ":" + message.getPort() + ")");
                }
                HttpServer.socketOutMap.put(address, socketOut);
            }

            HttpServer.executorPool.execute(new SockOutThread(address));

            if (message.getMethod().equals(Method.CONNECT)) {
                // 将已联通信号返回给请求页面
                osIn.write(AUTHORED.getBytes());
                osIn.flush();
            } else {
                //http请求需要将请求头部也转发出去
                byte[] headerData = message.toString().getBytes();
                osOut.write(headerData);
                osOut.flush();
            }

            readForwardDate(isIn, osOut);

        } catch (IOException e) {
//            e.printStackTrace();
            try {
                socketIn.close();
                if (socketOut != null) {
                    socketOut.close();
                }
            } catch (IOException e1) {
//                e1.printStackTrace();
            }
        } finally {
            SocketUtil.removeSocket(address);
            i("The address(" + address + ") is stop.");
        }

    }

    /**
     * 读取客户端发送过来的数据，发送给服务器端
     *
     * @param isIn
     * @param osOut
     */
    private void readForwardDate(InputStream isIn, OutputStream osOut) {
        byte[] buffer = new byte[2 * 1024];
        try {
            int len;
            while ((len = isIn.read(buffer)) != -1) {
                if (len > 0) {

//                    LogUtil.i(TAG, "给服务 >>>" + new String(buffer, 0, len));

                    osOut.write(buffer, 0, len);
                    osOut.flush();
                }
                if (socketIn.isClosed() || socketOut.isClosed()) {
                    break;
                }
            }
        } catch (Exception e) {
            try {
                socketIn.close();
                socketOut.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void i(String msg) {
        LogUtil.i(TAG, msg);
    }

}
