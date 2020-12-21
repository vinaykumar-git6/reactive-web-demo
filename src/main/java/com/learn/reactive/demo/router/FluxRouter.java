package com.learn.reactive.demo.router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import com.learn.reactive.demo.handler.FluxHandler;

@Configuration
public class FluxRouter {
	
	@Bean
	public RouterFunction<ServerResponse> router(FluxHandler handler){
		return RouterFunctions.route(GET("/function/flux").and(accept(MediaType.APPLICATION_JSON)),handler::fluxHandler)
				.andRoute(GET("/function/mono").and(accept(MediaType.APPLICATION_JSON)),handler::monoHandler);
		
	}
	

}
