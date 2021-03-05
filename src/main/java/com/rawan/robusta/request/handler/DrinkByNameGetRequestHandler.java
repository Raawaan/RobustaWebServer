package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Body;
import com.rawan.robusta.request.data.Method;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.mapping.RequestHandler;
import com.rawan.robusta.response.data.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@RequestHandler(method = Method.GET,url = "/?drink")
@NoArgsConstructor
@AllArgsConstructor
public class DrinkByNameGetRequestHandler implements Handler {
    private  Request request;

    @Override
    public Response getResponse() {
        String drink = request.getParams().get("drink");
        String names = postedData
                .stream()
                .filter(body -> body.getDrink().trim().equals(drink))
                .map(Body::getName)
                .collect(Collectors.joining(","));

        if (names.isEmpty()) {
            return new Response(String.format("%s %s is no one choice %s",start, drink,end));
        } else {
            return new Response(String.format("%s %s loves %s %s",start, names, drink,end));
        }
    }
}
