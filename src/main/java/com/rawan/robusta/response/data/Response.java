package com.rawan.robusta.response.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {

    private static final String OK_RESPONSE = "200 OK";

    private String message;

    public byte[] getResponseInByte(){
        return ("HTTP/1.1 " + OK_RESPONSE + "\r\n" +
                "ContentType: text/html\r\n" +
                "\r\n" +
                "<div style=\"text-align:center; margin-top:20%; back\"><h1> <b>" + message + "</b></h1></div>" +
                "\r\n\r\n" +
                "\r\n").getBytes();
    }
}
