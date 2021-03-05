package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Body;
import com.rawan.robusta.response.data.Response;

import java.util.ArrayList;
import java.util.List;

public interface Handler {
    List<Body> postedData = new ArrayList<>();

    String start = "<div style=\"text-align:center; margin-top:20%; back\"><h1> <b>";
    String end = " </b></h1></div>";

    Response getResponse();
}
