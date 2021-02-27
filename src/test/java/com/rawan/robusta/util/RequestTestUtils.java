package com.rawan.robusta.util;


import com.rawan.robusta.request.Body;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestTestUtils {
    private String hostName = "localhost";
    private int portNumber = 8282;

    public String sendGetRequest(String requestUri) throws IOException {
        Socket socket = new Socket(hostName, portNumber);
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        dout.writeBytes("GET"+ requestUri + " HTTP/1.1\n");
        dout.writeBytes("Host: localhost:8282\n");
        dout.writeBytes("Connection: keep-alive\n");
        dout.writeBytes("\n");
        dout.flush();
        return sendRequest(socket);
    }

    public String sendPostRequest(String requestUri, Body body) throws IOException {
        Socket socket = new Socket(hostName, portNumber);
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        dout.writeBytes("POST" + requestUri + " HTTP/1.1\n");
        dout.writeBytes("Host: localhost:8282\n");
        dout.writeBytes("Connection: keep-alive\n");
        dout.writeBytes("\n");
        dout.writeBytes("{\n");
        dout.writeBytes("name: "+body.getName()+"\n");
        dout.writeBytes("drink: "+body.getDrink()+"\n");
        dout.writeBytes("}");
        dout.writeBytes("\n");
        dout.flush();
        return sendRequest(socket);
    }
    private String sendRequest(Socket socket) {
        String fromServer;
        StringBuilder requestBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))) {

            while ((fromServer = in.readLine()) != null) {
                requestBuilder.append(fromServer).append("\r\n");
                if (fromServer.equals("\r\n"))
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return requestBuilder.toString();
    }
}