package com.zinc.jframe.proxy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zinc
 * @Describe: 报文信息
 * @Date: Created in 下午4:34 2018/5/12
 * @Modified By:
 */

public class Message {

    static final String CRLF = "\r\n";
    static final String SPACE = " ";
    static final String VERSION = "HTTP/1.1";
    static final String COLON = ":";

    private String method;
    private String host;
    private String port;

    private String stateLine;
    private Map<String, String> header = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHost() {
        return host;

    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public String getHeaderValue(String key) {
        return header.get(key);
    }

    public String getStateLine() {
        return stateLine;
    }

    public void setStateLine(String stateLine) {
        this.stateLine = stateLine;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stateLine);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            sb.append(entry.getKey());
            sb.append(COLON);
            sb.append(SPACE);
            sb.append(entry.getValue());
            sb.append(CRLF);
        }

        sb.append(CRLF);
        return sb.toString();
    }

}
