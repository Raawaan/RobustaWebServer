package com.rawan.robusta.request.handler;

import com.rawan.robusta.response.data.Response;

public class DefaultRequestHandler implements RequestHandler {
    @Override
    public Response getResponse() {
        return new Response("Hello?");
    }
}
