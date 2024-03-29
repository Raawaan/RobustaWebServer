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

    public byte[] getResponseInByte() {
        return ("HTTP/1.1 " + OK_RESPONSE + "\r\n" +
                "ContentType: text/html\r\n" +
                "\r\n" +
                message +
                "\r\n\r\n" +
                "\r\n").getBytes();
    }
}
