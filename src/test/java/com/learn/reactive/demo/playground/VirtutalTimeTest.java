package com.learn.reactive.demo.playground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

public class VirtutalTimeTest {
	
	@Test
	public void testtinWithoutVirtualTime() {
		
		Flux<Long> inteFlux=Flux.interval(Duration.ofSeconds(1)).take(3);
		StepVerifier.create(inteFlux)
		.expectSubscription()
		.expectNext(0L,1L,2L)
		.verifyComplete();
		
	}
	
	@Test
	public void testtinWithVirtualTime() {
		
		VirtualTimeScheduler.getOrSet();
		
		Flux<Long> inteFlux=Flux.interval(Duration.ofSeconds(1)).take(3);
		
		StepVerifier.withVirtualTime(()-> inteFlux.log())
									.expectSubscription()
									.thenAwait(Duration.ofSeconds(3))
									.expectNext(0L,1L,2L)
									.verifyComplete();
							
		
		
	}
}


