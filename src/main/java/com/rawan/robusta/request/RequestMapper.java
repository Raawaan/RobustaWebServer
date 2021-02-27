package com.rawan.robusta.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestMapper {
    private List<Body> postedData = new ArrayList<>();

    public String mapRequests(Request request) {
        if (request.getMethod() == Method.GET) {
            if (request.getParams().keySet().contains("name")) {
                return getDrinkByName(request);
            }
            else if (request.getParams().keySet().contains("drink")){
                return getNameByDrink(request);
            }
        } else if (request.getMethod() == Method.POST) {
            postedData.add(request.getBody());
            return String.format("Thanks %s, you %s is added!",
                    request.getBody().getName(),
                    request.getBody().getDrink());
        }
        return "Hello?";
    }

    private String getDrinkByName(Request request) {
        String name = request.getParams().get("name");
        Optional<String> optionalDrink = postedData
                .stream()
                .filter(body -> body.getName().trim().equals(name))
                .map(Body::getDrink)
                .findFirst();
        if (optionalDrink.isPresent()) {
            return String.format("%s's drink is%s", name, optionalDrink.get());
        } else {
            return String.format("hello %s you didn't add any drinks yet!" +
                    " So for now we recommend flat white!", name);
        }
    }

    private String getNameByDrink(Request request) {
        String drink = request.getParams().get("drink");
        String names = postedData
                .stream()
                .filter(body -> body.getDrink().trim().equals(drink))
                .map(Body::getName)
                .collect(Collectors.joining(","));
        if (names.isEmpty()){
            return String.format("%s is no one choice",drink);

        }else{
            return String.format("%s lovers %s",names,drink);
        }
    }
}
