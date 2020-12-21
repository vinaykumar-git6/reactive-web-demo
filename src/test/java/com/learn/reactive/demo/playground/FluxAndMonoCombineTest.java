package com.learn.reactive.demo.playground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoCombineTest {
	
	@Test
	public void combineUsingMerge() {

		Flux<String> stringFlux1 = Flux.just("A","B","C");
		Flux<String> stringFlux2 = Flux.just("D","E","F");
		
		Flux<String> mergeFlux = Flux.merge(stringFlux1,stringFlux2);

		StepVerifier.create(mergeFlux)
					.expectSubscription()
					.expectNext("A","B","C","D","E","F")
					.verifyComplete();

	}
	
	@Test
	public void combineUsingMerge_UsingDelay() {

		Flux<String> stringFlux1 = Flux.just("A","B","C");
		Flux<String> stringFlux2 = Flux.just("D","E","F");
		
		Flux<String> mergeFlux = Flux.merge(stringFlux1,stringFlux2);

		StepVerifier.create(mergeFlux)
					.expectNextCount(6)
					//.expectNext("A","B","C","D","E","F")
					.verifyComplete();

	}
	
	@Test
	public void combineUsingConcat_withDealay() {

		Flux<String> stringFlux1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
		Flux<String> stringFlux2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));
		
		Flux<String> mergeFlux = Flux.concat(stringFlux1,stringFlux2);

		StepVerifier.create(mergeFlux.log())
					.expectNext("A","B","C","D","E","F")
					.verifyComplete();

	}
	
	@Test
	public void combineUsingZip() {

		Flux<String> stringFlux1 = Flux.just("A","B","C");
		Flux<String> stringFlux2 = Flux.just("D","E","F");
		
		Flux<String> mergeFlux = Flux.zip(stringFlux1,stringFlux2, (t1,t2) -> {
			return t1.concat(t2);
		});

		StepVerifier.create(mergeFlux.log())
					.expectNext("AD","BE","CF")
					.verifyComplete();

	}
}
