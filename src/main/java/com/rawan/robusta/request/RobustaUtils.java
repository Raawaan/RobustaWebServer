package com.rawan.robusta.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.System.out;

public class RobustaUtils {
    private static final String CUSTOM_MESSAGE = "custom-message";
    private static final String OK_RESPONSE = "200 OK";
    private static final String BAD_REQUEST_RESPONSE = "400 Bad robusta.web.server.request";
    private static final String GET_REQUESTS_ONLY_MESSAGE = "Sorry we serve 'GET' requests only!";
    private static final String DEFAULT_MESSAGE = "Boom!";

    public Request handleRequest(BufferedReader in) throws IOException {
        String input;
        StringBuilder requestBuilder = new StringBuilder();
        while (!(input = in.readLine()).isEmpty()) {
            requestBuilder.append(input).append("\r\n");
        }
        Request request = new Request(requestBuilder.toString());
        out.println(request);
        return request;
    }

    public void handleResponse(OutputStream out, Request request) throws IOException {
        if (!request.getMethod().equals(Method.GET.name())) {
            errorMessageResponse(out);
        } else if (request.getParams().containsKey(CUSTOM_MESSAGE)) {
            customMessageResponse(out, request.getParams().get(CUSTOM_MESSAGE));
        } else {
            defaultResponse(out);
        }
    }


    private void defaultResponse(OutputStream out) throws IOException {
        sendMessage(out, OK_RESPONSE, DEFAULT_MESSAGE);
    }

    private void errorMessageResponse(OutputStream out) throws IOException {
        sendMessage(out, BAD_REQUEST_RESPONSE, GET_REQUESTS_ONLY_MESSAGE);
    }

    private void customMessageResponse(OutputStream out, String message) throws IOException {
        sendMessage(out, OK_RESPONSE, message);
    }

    private void sendMessage(OutputStream out, String statusCode, String message) throws IOException {
        out.write(("HTTP/1.1 " + statusCode + "\r\n").getBytes());
        out.write(("ContentType: text/html\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(("<div style=\"text-align:center; margin-top:20%; back\"><h1> <b>" + message + "</b></h1></div>").getBytes());
        out.write("\r\n\r\n".getBytes());
        out.write("\r\n".getBytes());
        out.flush();
    }

}
