package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Method;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.mapping.RequestHandler;
import com.rawan.robusta.response.data.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RequestHandler(method = Method.GET,url = "/default")
@NoArgsConstructor
@AllArgsConstructor
public class DefaultRequestHandler implements Handler {
    private Request request;

    @Override
    public Response getResponse() {
        return new Response(
        String.format("%s Hello, we don't sever %s %s", start,request.getUrl(),end));
    }
}
