package comfntech.gateway_service.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

@Component 
public class UserHeaderFilter implements GlobalFilter {
    @Override 
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
        .map(securityContext -> securityContext.getAuthentication())
        .cast(JwtAuthenticationToken.class)
        .map(auth -> {
            String userId = auth.getToken().getSubject();
            ServerHttpRequest req = exchange.getRequest().mutate()
                .header("X-User-Id", userId)
                .build();
            return exchange.mutate().request(req).build();
        })
        .defaultIfEmpty(exchange)
        .flatMap(chain::filter);
    }
    
}
