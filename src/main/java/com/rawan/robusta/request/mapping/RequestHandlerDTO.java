package com.rawan.robusta.request.mapping;

import com.rawan.robusta.request.data.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestHandlerDTO {
    private Class clazz;
    private String url;
    private Method method;
}
