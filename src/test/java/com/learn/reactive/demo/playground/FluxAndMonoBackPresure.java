package com.learn.reactive.demo.playground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPresure {
	
	@Test
	public void backpressureTest() {
		
		Flux<Integer> fluxtest=Flux.range(1, 5);
		
		StepVerifier.create(fluxtest.log())
					.expectSubscription()
					.thenRequest(1)
					.expectNext(1)
					.thenRequest(1)
					.expectNext(2)
					.thenCancel()
					.verify();
	}
	
	@Test
	public void backpressureTest_withSubscriber() {
		
		Flux<Integer> fluxtest=Flux.range(1, 5).log();
		
		fluxtest.subscribe((elements) -> System.err.println("Element is :" + elements),
						(e) -> System.err.println("Exception is : " + e), 
						()->System.err.println("completed"),
						(subscription) -> subscription.request(2));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void backpressureTest_cancel() {
		
		Flux<Integer> fluxtest=Flux.range(1, 5).log();
		
		fluxtest.subscribe((elements) -> System.err.println("Element is :" + elements),
						(e) -> System.err.println("Exception is : " + e), 
						()->System.err.println("completed"),
						(subscription) -> subscription.cancel());
	}
	
	@Test
	public void Customised_backpressureTest() {
		
		Flux<Integer> fluxtest=Flux.range(1, 5).log();
		
		fluxtest.subscribe(new BaseSubscriber<Integer>() {
			@Override
			protected void hookOnNext(Integer value){
				request(1);
				System.err.println("value is : " +value);
				if(value==3) {
					cancel();
				}
				
			}

		});
	}

}
