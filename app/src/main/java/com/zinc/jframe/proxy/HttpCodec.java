package com.zinc.jframe.proxy;

import android.text.TextUtils;

import com.zinc.jframe.proxy.model.Message;
import com.zinc.jframe.proxy.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by zinc on 2018/5/12.
 */
public class HttpCodec {

    private String TAG = "HttpCodec";

    //回车和换行
    static final String CRLF = "\r\n";
    static final int CR = 13;
    static final int LF = 10;

    public static final String HEAD_HOST = "Host";

    private final ByteBuffer byteBuffer;

    public HttpCodec() {
        this.byteBuffer = ByteBuffer.allocate(10 * 1024);
    }

    /**
     * @Author: zinc
     * @Descrption: 读取状态行
     * @Date 下午6:18 2018/5/12
     */
    public void readStateLine(InputStream is, Message message) throws IOException {

        String stateLine = readLine(is);
        message.setStateLine(stateLine);
//        String[] ele = stateLine.split(" ");

        if (stateLine.startsWith(Method.CONNECT)) {
            message.setMethod(Method.CONNECT);
        } else if (stateLine.startsWith(Method.POST)) {
            message.setMethod(Method.POST);
        } else if (stateLine.startsWith(Method.GET)) {
            message.setMethod(Method.GET);
        } else {
            throw new RuntimeException("It's a not support method.");
        }

        LogUtil.i(TAG, "状态行 》》  "+stateLine);

    }

    /**
     * @Author: zinc
     * @Descrption: 读取头部
     * @Date 下午6:17 2018/5/12
     */
    public void readHeaders(InputStream is, Message message) throws IOException {

        while (true) {
            String line = readLine(is);

            LogUtil.i(TAG, "头参数 》》"+line);

            //如果读到空行 \r\n 响应头读完了
            if (isEmptyLine(line)) {
                break;
            }
            int index = line.indexOf(":");
            if (index > 0) {
                String key = line.substring(0, index);
                //+2 是为了要跳过":"和" "（空格） -2是为了去掉\r\n
                String value = line.substring(index + 2, line.length() - 2);
                message.addHeader(key, value);

                //是否为Host
                if (TextUtils.equals(HEAD_HOST, key)) {
                    String[] hosts = value.split(":");
                    message.setHost(hosts[0]);
                    if (message.getMethod().equals(Method.CONNECT)) {

                        message.setPort(hosts.length >= 2 ? hosts[1] : "443");

                    } else if (message.getMethod().equals(Method.POST) || message.getMethod().equals(Method.GET)) {

                        message.setPort(hosts.length >= 2 ? hosts[1] : "80");

                    }
                }
            }

        }

    }

    private boolean isEmptyLine(String line) {
        return TextUtils.equals(line, CRLF);
    }

    private String readLine(InputStream is) throws IOException {
        //清理byteBuffer
        byteBuffer.clear();
        //标记
        byteBuffer.mark();
        boolean isMaybeEndOfLine = false;
        byte b;
        //一次读一个字节
        while ((b = (byte) is.read()) != -1) {

            byteBuffer.put(b);

            //如果当前读到一个 \r
            if (b == CR) {
                isMaybeEndOfLine = true;
            } else if (isMaybeEndOfLine) {
                //读到 /n , 需要\r\n才能真正表明一行结束
                if (b == LF) {
                    //一行数据 position是长度
                    byte[] lineBytes = new byte[byteBuffer.position()];
                    //重回到mark的一行
                    byteBuffer.reset();
                    //从byteBuffer获得数据
                    byteBuffer.get(lineBytes);

                    //清空数据，并将标记移回开始
                    byteBuffer.clear();
                    byteBuffer.mark();

                    return new String(lineBytes);
                }
                isMaybeEndOfLine = false;
            }
        }
        throw new IOException("Response read Line");
    }

}
