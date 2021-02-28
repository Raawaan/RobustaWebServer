package com.rawan.robusta;

import com.rawan.robusta.request.RequestMapper;
import com.rawan.robusta.request.RequestUtils;
import com.rawan.robusta.request.handler.RequestHandler;
import com.rawan.robusta.response.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class RobustaApp {
    public static void main(String[] args) {
        int portNumber = 8282;
        RequestMapper requestMapper = new RequestMapper();
        RequestUtils requestUtils = new RequestUtils(requestMapper);
        ResponseHandler responseHandler = new ResponseHandler();

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                out.println("Loop");
                Socket clientSocket = serverSocket.accept();
                out.println("Accept");

                new Thread(() -> {
                    System.out.println("Hi " + Thread.currentThread().getName());
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                         OutputStream out = clientSocket.getOutputStream()) {

                        RequestHandler requestHandler = requestUtils.getRequestHandler(in);

                        responseHandler.handleResponse(out,requestHandler);

                        clientSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Bye " + Thread.currentThread().getName());
                    }
                }).start();
            }
        } catch (Exception e) {
            out.println("Exception" + e.getLocalizedMessage());
        }
    }
}

