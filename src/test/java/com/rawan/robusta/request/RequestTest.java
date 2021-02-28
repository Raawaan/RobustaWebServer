package com.rawan.robusta.request;

import com.rawan.robusta.request.data.Method;
import com.rawan.robusta.request.data.Request;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class RequestTest {
    private Request request;

    @Before
    public void setup() {
        String requestData = "GET /?custom-message=helloo HTTP/1.1\n" +
                "Host: localhost:8282\n" +
                "Connection: keep-alive\n";
        request = new Request(requestData);
    }

    @Test
    public void paramsProcessedSuccessfully() {
        Map<String, String> expectedParams = new HashMap<String, String>() {{
            put("custom-message", "helloo");
        }};
        assertEquals(expectedParams, request.getParams());
    }

    @Test
    public void headersProcessedSuccessfully() {
        Map<String, String> expectedheaders = new HashMap<String, String>() {{
            put("Connection", "keep-alive");
        }};
        assertEquals(expectedheaders, request.getHeaders());
    }

    @Test
    public void hostProcessedSuccessfully() {
        assertEquals("localhost:8282", request.getHost());
    }

    @Test
    public void methodProcessedSuccessfully() {
        assertEquals(Method.GET.name(), request.getMethod());
    }
}