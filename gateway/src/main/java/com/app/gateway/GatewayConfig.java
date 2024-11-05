//package com.app.gateway;
//
//import com.app.security.filter.JwtFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.server.WebFilter;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public com.app.security.filter.JwtFilter jwtFilter() {
//        return new JwtFilter();
//    }
//
//    @Bean
//    public WebFilter jwtWebFilter(JwtFilter jwtFilter) {
//        return (exchange, chain) -> {
//            HttpServletRequest request = (HttpServletRequest) exchange.getRequest();
//            HttpServletResponse response = (HttpServletResponse) exchange.getResponse();
//            FilterChain filterChain = (FilterChain) chain;
//
//            try {
//                jwtFilter.doFilterInternal(request, response, filterChain);
//            } catch (ServletException | IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            return chain.filter(exchange);
//        };
//    }
//}