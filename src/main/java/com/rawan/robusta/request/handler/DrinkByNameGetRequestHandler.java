package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Body;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.response.data.Response;

import java.util.stream.Collectors;

public class DrinkByNameGetRequestHandler  implements RequestHandler {
    private final Request request;

    public DrinkByNameGetRequestHandler(Request request) {
        this.request = request;

    }

    @Override
    public Response getResponse() {
        String drink = request.getParams().get("drink");
        String names = postedData
                .stream()
                .filter(body -> body.getDrink().trim().equals(drink))
                .map(Body::getName)
                .collect(Collectors.joining(","));

        if (names.isEmpty()) {
            return new Response(String.format("%s is no one choice", drink));
        } else {
            return new Response(String.format("%s loves %s", names, drink));
        }
    }
}
