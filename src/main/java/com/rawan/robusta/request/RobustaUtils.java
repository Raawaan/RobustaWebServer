package com.rawan.robusta.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import static java.lang.System.out;

public class RobustaUtils {

    private static final String OK_RESPONSE = "200 OK";

    private RequestMapper requestMapper;

    public RobustaUtils(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    public Request handleRequest(BufferedReader in) throws IOException {
        String input;
        StringBuilder requestBuilder = new StringBuilder();
        while (!(input = in.readLine()).isEmpty()) {
            requestBuilder.append(input).append("\r\n");
        }
        while (in.ready() && !(input = in.readLine()).contains("}")) {
            requestBuilder.append(input).append("\r\n");
        }


        if (!requestBuilder.toString().isEmpty()) {
            Request request = new Request(requestBuilder.toString());
            out.println(request);
            return request;
        }
        return null;
    }

    public void handleResponse(OutputStream out, Request request) throws IOException {
        System.out.println("Sending response from " + Thread.currentThread().getName());
        String response = requestMapper.mapRequests(request);
        sendMessage(out, response);
    }


    private void sendMessage(OutputStream out, String message) throws IOException {
        out.write(("HTTP/1.1 " + RobustaUtils.OK_RESPONSE + "\r\n" +
                "ContentType: text/html\r\n" +
                "\r\n" +
                "<div style=\"text-align:center; margin-top:20%; back\"><h1> <b>" + message + "</b></h1></div>" +
                "\r\n\r\n" +
                "\r\n").getBytes());
        out.flush();
    }

}
