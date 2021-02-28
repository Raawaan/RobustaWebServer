package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Body;
import com.rawan.robusta.response.data.Response;

import java.util.ArrayList;
import java.util.List;

public interface RequestHandler {

    List<Body> postedData = new ArrayList<>();

    Response getResponse();
}

