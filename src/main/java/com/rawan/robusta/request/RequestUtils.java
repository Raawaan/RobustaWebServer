package com.rawan.robusta.request;

import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.handler.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.System.out;

public class RequestUtils {

    private RequestMapper requestMapper;

    public RequestUtils(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    public RequestHandler getRequestHandler(BufferedReader in) throws IOException {

        StringBuilder requestBuilder = readRequest(in);

        if (!requestBuilder.toString().isEmpty()) {
            Request request = new Request(requestBuilder.toString());
            out.println(request);
            return requestMapper.mapRequests(request);
        }
        return null;
    }

    private StringBuilder readRequest(BufferedReader in) throws IOException {
        String input;
        StringBuilder requestBuilder = new StringBuilder();
        while (!(input = in.readLine()).isEmpty()) {
            requestBuilder.append(input).append("\r\n");
        }
        while (in.ready() && !(input = in.readLine()).contains("}")) {
            requestBuilder.append(input).append("\r\n");
        }
        return requestBuilder;
    }


}
