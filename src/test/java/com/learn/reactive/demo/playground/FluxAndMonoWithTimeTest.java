package com.learn.reactive.demo.playground;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoWithTimeTest {
	
	@Test
	public void infinteSeq() {
		
		Flux<Long> infinteFlux=Flux.interval(Duration.ofMillis(100)).log();
		
		infinteFlux.subscribe((e)->System.err.println("Next value" + e));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	@Test
	public void infiniteSeq_Test() throws InterruptedException {
		
		Flux<Long> finiteFlux=Flux.interval(Duration.ofMillis(100))
								   .take(3)
								.log();
		
		StepVerifier.create(finiteFlux)
					.expectSubscription()
					.expectNext(0L,1L,2L)
					.verifyComplete();
		
		
	}
	
	@Test
	public void infiniteSeqMap_Test() throws InterruptedException {
		
		Flux<Integer> finiteFlux=Flux.interval(Duration.ofMillis(100))
								.map(l->new Integer(l.intValue()))
								   .take(3)
								.log();
		
		StepVerifier.create(finiteFlux)
					.expectSubscription()
					.expectNext(0,1,2)
					.verifyComplete();
		
		
	}
	
	@Test
	public void infiniteSeqMap_Test_withDealy() throws InterruptedException {
		
		Flux<Integer> finiteFlux=Flux.interval(Duration.ofMillis(100))
								.delayElements(Duration.ofSeconds(1))
								.map(l->new Integer(l.intValue()))
								.take(3)
								.log();
		
		StepVerifier.create(finiteFlux)
					.expectSubscription()
					.expectNext(0,1,2)
					.verifyComplete();
		
		
	}
}
