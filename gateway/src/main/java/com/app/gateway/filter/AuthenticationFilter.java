package com.app.gateway.filter;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.config> {
    public AuthenticationFilter() {
        super(config.class);
    }

    @Autowired
    private RestTemplate template;

    @Autowired
    private RouteValidator routeValidator;
    @Override
    public GatewayFilter apply(config config) {
        return (exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){

                if(!exchange.getRequest().getHeaders().containsKey("Authorization")){
                    throw new RuntimeException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);

                }
                

            }


            return chain.filter(exchange);
        };
    }

    public static class config {

        // Configuration is in the progress.

    }
}
