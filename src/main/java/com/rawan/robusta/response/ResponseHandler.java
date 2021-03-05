package com.rawan.robusta.response;

import com.rawan.robusta.request.handler.Handler;
import com.rawan.robusta.response.data.Response;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseHandler {

    public void handleResponse(OutputStream out, Handler handler) throws IOException {
        System.out.println("Sending response from " + Thread.currentThread().getName());
        Response response = handler.getResponse();
        sendMessage(out, response);
    }


    private void sendMessage(OutputStream out, Response response) throws IOException {
        out.write(response.getResponseInByte());
        out.flush();
    }
}
