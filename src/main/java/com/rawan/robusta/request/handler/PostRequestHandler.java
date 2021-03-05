package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Method;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.mapping.RequestHandler;
import com.rawan.robusta.response.data.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RequestHandler(method = Method.POST,url = "/")
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestHandler implements Handler{
    private  Request request;

    @Override
    public Response getResponse() {
        postedData.add(request.getBody());
        return new Response(String.format("Thanks %s, you %s is added!",
                request.getBody().getName(),
                request.getBody().getDrink()));
    }
}
