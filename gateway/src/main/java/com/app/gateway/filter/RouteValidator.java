package com.app.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

public class RouteValidator {

    public static final List<String> openApiEndPoints = List.of("/user/register","/user/login","/eureka");

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndPoints.stream().noneMatch(request.getURI().getPath()::contains);
}
