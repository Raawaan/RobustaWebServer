package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Body;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.response.data.Response;

import java.util.Optional;

public class NameByDrinkGetRequestHandler implements RequestHandler {
    private final Request request;

    public NameByDrinkGetRequestHandler(Request request) {
        this.request = request;
    }

    @Override
    public Response getResponse() {
        String name = request.getParams().get("name");
        Optional<String> optionalDrink = postedData
                .stream()
                .filter(body -> body.getName().trim().equals(name))
                .map(Body::getDrink)
                .findFirst();
        return optionalDrink
                .map(drink -> new Response(String.format("%s's drink is%s", name, drink)))
                .orElseGet(() ->
                        new Response(String.format("hello %s you didn't add any drinks yet!" +
                                " So for now we recommend flat white!", name)));
    }
}
