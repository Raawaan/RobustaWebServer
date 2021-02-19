package robusta.web.server.request;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Request {

    private String method;
    private String host;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    public Request(String requestData) {
        processRequestData(requestData);
    }

    private void processRequestData(String requestData) {
        String[] lines = requestData.split("\n");
        method = lines[0].split(" ")[0];
        processParams(lines[0]);
        processHost(lines[1]);
        processHeaders(lines);
    }

    private void processParams(String line) {
        String paramLines = line.split(" ")[1];
        if (!paramLines.equals("/") && paramLines.contains("?")) {
            Arrays.stream(
                    paramLines
                            .subSequence(paramLines.indexOf("?") + 1, paramLines.length())
                            .toString()
                            .split("&")
            )
                    .map(s -> s.split("="))
                    .forEach(strings ->params.put(strings[0], strings[1]));
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
        return "robusta.web.server.request.Request {\n" +
                "method= " + method + ",\n" +
                "host= " + host + ",\n" +
                "headers= " + headers +
                "\n}";
    }
}
