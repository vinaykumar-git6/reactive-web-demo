package com.learn.reactive.demo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Component
public class FluxHandler {
	

	public Mono<ServerResponse> fluxHandler(ServerRequest serverReq){
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Flux.just(1,2,3,4).log(),Integer.class);
		
	}
	
	public Mono<ServerResponse> monoHandler(ServerRequest serverReq){
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(1).log(),Integer.class);
		
	}

}
