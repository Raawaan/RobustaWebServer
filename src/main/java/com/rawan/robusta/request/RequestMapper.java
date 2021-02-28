package com.rawan.robusta.request;

import com.rawan.robusta.request.data.Method;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.handler.*;

public class RequestMapper {

    RequestHandler mapRequests(Request request) {
        if (request.getMethod() == Method.GET) {
            if (request.getParams().keySet().contains("name")) {
                return new NameByDrinkGetRequestHandler(request);
            }
            else if (request.getParams().keySet().contains("drink")){
                return new DrinkByNameGetRequestHandler(request);
            }
        } else if (request.getMethod() == Method.POST) {
            return new PostRequestHandler(request);
        }
        return new DefaultRequestHandler();
    }
}
