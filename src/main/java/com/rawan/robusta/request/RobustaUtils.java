package com.rawan.robusta.request;

import com.rawan.robusta.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import static java.lang.System.out;

public class RobustaUtils {

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
        out.write(new Response(message).getResponseInByte());
        out.flush();
    }

}
