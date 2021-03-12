package com.rawan.robusta.request;

import com.rawan.robusta.request.data.Request;
import com.rawan.robusta.request.handler.DefaultRequestHandler;
import com.rawan.robusta.request.handler.Handler;
import com.rawan.robusta.request.mapping.RequestHandler;
import com.rawan.robusta.request.mapping.RequestHandlerDTO;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class RequestMapper extends ClassLoader {
    private List<RequestHandlerDTO> requestHandlerDTOS;
    private Set<Class<?>> classes = new HashSet<>();

    public RequestMapper() {
    }

    @SneakyThrows
    public void addToClasspath() {
        try {
            File dirOfJars = new File("/home/rawan/IdeaProjects/robusta-web-server/lib");
            for (File file : dirOfJars.listFiles()) {
                String path= file.getPath();
            JarFile jarFile = new JarFile(path);
            Enumeration<JarEntry> e = jarFile.entries();

            URL[] urls = {new URL("jar:file:" + path + "!/")};
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);

            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                Class loadedClass = urlClassLoader.loadClass(className);
                if (loadedClass.isAnnotationPresent(RequestHandler.class)) {
                    classes.add(loadedClass);
                }
            }
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    Handler mapRequests(Request request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        addToClasspath();
        generateRequestHandlersDTOs();
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
        classesAnnotated.addAll(classes);
        classesAnnotated.size();
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
