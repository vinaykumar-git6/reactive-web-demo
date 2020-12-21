package com.learn.reactive.demo.playground;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxnadMonoFactoryTest {
	
	List<String> names = Arrays.asList("vinay", "Amitha", "Sahil", "Jackson");
	
	@Test
	public void fluxUsingItr() {
		
		Flux<String> stringFlux= Flux.fromIterable(names);
		StepVerifier.create(stringFlux)
		.expectNext("vinay", "Amitha", "Sahil", "Jackson")
		.verifyComplete();
	}
	
	@Test
	public void fluxUsingArry() {
		
		String[] names=new String[] {"vinay", "Amitha", "Sahil", "Jackson"};
		
		Flux<String> stringFlux= Flux.fromArray(names);
		StepVerifier.create(stringFlux)
		.expectNext("vinay", "Amitha", "Sahil", "Jackson")
		.verifyComplete();
	}
	
	@Test
	public void fluxUsingStream() {
		
		Flux<String> stringFlux= Flux.fromStream(names.stream());
		StepVerifier.create(stringFlux)
		.expectNext("vinay", "Amitha", "Sahil", "Jackson")
		.verifyComplete();
	}
	
	@Test
	public void monoUsingJustOrEmpty() {
		Mono<String> mono= Mono.justOrEmpty(null);
		StepVerifier.create(mono.log())
		.verifyComplete();
		
	}
	
	@Test
	public void monoUsingSupplier() {
		
		Supplier<String> stringSupp= () -> "s-Stirng";
		Mono<String> mono= Mono.fromSupplier(stringSupp);
		StepVerifier.create(mono.log())
		.expectNext("s-Stirng")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxUsingRange() {
		
		Flux<Integer> ingFlux= Flux.range(1, 5).log();
		StepVerifier.create(ingFlux)
		.expectNext(1,2,3,4,5)
		.verifyComplete();
	}

}
