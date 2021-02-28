package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.response.data.Response;

public class PostRequestHandler implements RequestHandler  {
    private final Request request;

    public PostRequestHandler(Request request) {
        this.request = request;
    }

    @Override
    public Response getResponse() {
        postedData.add(request.getBody());
        return new Response(String.format("Thanks %s, you %s is added!",
                request.getBody().getName(),
                request.getBody().getDrink()));
    }
}
