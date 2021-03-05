package com.rawan.robusta.request.data;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Request {

    private Method method;
    private String host;
    private String url;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private Body body;

    public Request(String requestData) {
        processRequestData(requestData);
    }

    private void processRequestData(String requestData) {
        String[] bodies = requestData.split("\\{");
        String[] lines = bodies[0].split("\n");
        method = Method.valueOf(lines[0].split(" ")[0]);
        url = lines[0].split(" ")[1];
        processParams(lines[0]);
        processHost(lines[1]);
        processHeaders(lines);
        if (bodies.length > 1) {
            processBody(bodies[1].split("\n"));
        }
    }

    private void processBody(String[] bodyData) {
        body = new Body();
        Arrays.stream(bodyData)
                .filter(s -> s.contains(":"))
                .map(s -> s.replace('"', ' ').replace(',', ' ').trim())
                .map(s -> s.split(":"))
                .forEach(splitted -> {
                    if (splitted[0].trim().equals("name")) {
                        body.setName(splitted[1]);
                    } else if (splitted[0].trim().equals("drink")) {
                        body.setDrink(splitted[1]);
                    }
                });
    }

    private void processParams(String line) {
        String paramLines = line.split(" ")[1];
        if (!paramLines.equals("/") && paramLines.contains("?")) {
            Arrays.stream(paramLines
                            .subSequence(paramLines.indexOf("?") + 1, paramLines.length())
                            .toString()
                            .split("&"))
                    .map(s -> s.split("="))
                    .forEach(strings -> params.put(strings[0], strings[1]));
        }
    }

    private void processHost(String line) {
        String hostLine = line.split(" ")[1];
        host = hostLine
                .chars()
                .filter(f -> f != '\r')
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    private void processHeaders(String[] lines) {
        for (int i = 2; i < lines.length; i++) {
            String[] headerLine = lines[i].chars()
                    .filter(f -> f != '\r')
                    .collect(StringBuilder::new,
                            StringBuilder::appendCodePoint,
                            StringBuilder::append)
                    .toString()
                    .split(": ");
            headers.put(headerLine[0], headerLine[1]);
        }
    }

    @Override
    public String toString() {
        return "Request {\n" +
                "method= " + method + ",\n" +
                "host= " + host + ",\n" +
                "headers= " + headers +
                "params= " + params +
                "body= " + body +
                "\n}";
    }
}
