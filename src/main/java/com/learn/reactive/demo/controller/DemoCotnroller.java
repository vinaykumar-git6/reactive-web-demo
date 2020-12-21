package com.learn.reactive.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.util.Duration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DemoCotnroller {
	
	@GetMapping( value = "/flux", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<String> getFlux(){
		return Flux.just("Vinay", "Amy", "Singh", "King").log();
	}

	
	@GetMapping( value = "/fluxint", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Integer> getFluxInt(){
		return Flux.just(1,2,3,4).log();
	}
	
	@GetMapping( value = "/fluxintstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> getFluxIntStrem(){
		return Flux.just(1,2,3,4).delayElements(java.time.Duration.ofSeconds(2)).log();
	}
	
	@GetMapping( value = "/fluxintstream/infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Long> getFluxIntInfiniteStrem(){
		return Flux.interval(java.time.Duration.ofSeconds(1)).log();
	}
	
	@GetMapping( value = "/monoint", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Integer> getmonoInt(){
		return Mono.just(1).log();
	}
}
