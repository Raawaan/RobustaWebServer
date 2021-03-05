package com.rawan.robusta.request.handler;

import com.rawan.robusta.request.data.Method;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.mapping.RequestHandler;
import com.rawan.robusta.response.data.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RequestHandler(method = Method.GET, url = "/static/index.html")
@NoArgsConstructor
@AllArgsConstructor
public class StaticContentRequestHandler implements Handler {
    private Request request;

    StringBuilder getFileContent() {
        StringBuilder responseBuilder = new StringBuilder();
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(request.getUrl().split("/")[2]).getFile());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null){
                responseBuilder.append(line);

                line = reader.readLine();
            }
        } catch (Exception exception) {
            responseBuilder.append("Error While getting the file");
        }
        return responseBuilder;
    }

    @Override
    public Response getResponse() {
        return new Response(getFileContent().toString());
    }
}
