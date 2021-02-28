package com.rawan.robusta.response;

import com.rawan.robusta.request.handler.RequestHandler;
import com.rawan.robusta.response.data.Response;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseHandler {

    public void handleResponse(OutputStream out, RequestHandler requestHandler) throws IOException {
        System.out.println("Sending response from " + Thread.currentThread().getName());
        Response response = requestHandler.getResponse();
        sendMessage(out, response);
    }


    private void sendMessage(OutputStream out, Response response) throws IOException {
        out.write(response.getResponseInByte());
        out.flush();
    }
}
