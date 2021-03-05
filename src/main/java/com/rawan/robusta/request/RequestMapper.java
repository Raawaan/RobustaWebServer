package com.rawan.robusta.request;

import com.rawan.robusta.request.data.Method;
import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.handler.DefaultRequestHandler;
import com.rawan.robusta.request.handler.Handler;
import com.rawan.robusta.request.mapping.RequestHandler;
import com.rawan.robusta.request.mapping.RequestHandlerDTO;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestMapper {
    private List<RequestHandlerDTO> requestHandlerDTOS;

    public RequestMapper() {
        generateRequestHandlersDTOs();
    }

    Handler mapRequests(Request request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Optional<RequestHandlerDTO> optionalMappingDTO = requestHandlerDTOS
                .stream()
                .filter(requestHandlerDTO -> (requestHandlerDTO.getMethod() == request.getMethod() &&
                        (request.getUrl().contains(requestHandlerDTO.getUrl()))))
                .findFirst();

        RequestHandlerDTO requestHandlerDTO = optionalMappingDTO.orElse(new RequestHandlerDTO(
                DefaultRequestHandler.class,
                DefaultRequestHandler.class.getAnnotation(RequestHandler.class).url(),
                DefaultRequestHandler.class.getAnnotation(RequestHandler.class).method()
        ));

        return (Handler) requestHandlerDTO.getClazz().getConstructor(Request.class).newInstance(request);
    }

    private void generateRequestHandlersDTOs() {
        Set<Class<?>> classesAnnotated = getAnnotatedClasses(RequestHandler.class);

        requestHandlerDTOS = classesAnnotated
                .stream()
                .map(aClass -> {
                    try {
                        RequestHandler annotation = ((Class) aClass).newInstance().getClass().getAnnotation(RequestHandler.class);
                        return new RequestHandlerDTO(
                                aClass,
                                annotation.url(),
                                annotation.method()
                        );
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private Set<Class<?>> getAnnotatedClasses(Class<RequestHandler> annotationClass) {
        Reflections reflections = new Reflections(".*");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(annotationClass);
        return annotated;
    }
}
