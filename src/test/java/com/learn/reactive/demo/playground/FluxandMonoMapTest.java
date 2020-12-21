package com.learn.reactive.demo.playground;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxandMonoMapTest {
	
	List<String> names = Arrays.asList("vinay", "Amitha", "Sahil", "Jackson");
	
	@Test
	public void fluxTestMap() {
		
		Flux<String> stringFlux= Flux.fromIterable(names)
				.map(s->s.toUpperCase()).log();
		
		StepVerifier.create(stringFlux)
		.expectNext("VINAY", "AMITHA", "SAHIL", "JACKSON")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTestMap_length() {
		
		Flux<Integer> stringFlux= Flux.fromIterable(names)
				.map(s->s.length()).log();
		
		StepVerifier.create(stringFlux)
		.expectNext(5,6,5,7)
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTestMap_Filer_length() {
		
		Flux<String> stringFlux= Flux.fromIterable(names)
				.filter(s->s.length()>6)
				.map(s->s.toUpperCase()).log();
		
		StepVerifier.create(stringFlux)
		.expectNext("JACKSON")
		.verifyComplete();
		
	}

}
