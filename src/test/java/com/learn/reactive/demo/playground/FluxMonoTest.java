package com.learn.reactive.demo.playground;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxMonoTest {

	@Test
	public void fluxTest_withEvents() {

		Flux<String> fluxEvents = Flux.just("vinay", "Hi Vinay you There!!", "Hope you are in Dubai");

		fluxEvents.subscribe(System.out::println);
	}

	@Test
	public void fluxTest() {

		Flux<String> fluxEvents = Flux.just("vinay", "Hi Vinay you There!!", "Hope you are in Dubai")
				.concatWith(Flux.error(new RuntimeException("Exception Occured"))).log();

		fluxEvents.subscribe(System.out::println, (e) -> System.err.println("Exception is" + e),
				() -> System.out.println("completed"));
	}
	
	@Test
	public void fluxTest_withoutError() {

		Flux<String> fluxEvents = Flux.just("vinay", "Hi Vinay you There!!", "Hope you are in Dubai");

		StepVerifier.create(fluxEvents)
		.expectNext("vinay")
		.expectNext("Hi Vinay you There!!")
		.expectNext("Hope you are in Dubai")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTest_withoutErrorSingleLine() {

		Flux<String> fluxEvents = Flux.just("vinay", "Hi Vinay you There!!", "Hope you are in Dubai");

		StepVerifier.create(fluxEvents)
		.expectNext("vinay", "Hi Vinay you There!!", "Hope you are in Dubai")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTest_withError() {

		Flux<String> fluxEvents = Flux.just("vinay", "Hi Vinay you There!!", "Hope you are in Dubai")
				.concatWith(Flux.error(new RuntimeException("Exception Occured")))
				.log();

		StepVerifier.create(fluxEvents)
		.expectNext("vinay")
		.expectNext("Hi Vinay you There!!")
		.expectNext("Hope you are in Dubai")
		.expectErrorMessage("Exception Occured")
		//.expectError(RuntimeException.class)
		.verify();
		
	}
	
	@Test
	public void fluxElementsTest_withCount() {

		Flux<String> fluxEvents = Flux.just("vinay", "Hi Vinay you There!!", "Hope you are in Dubai")
				.log();

		StepVerifier.create(fluxEvents)
		.expectNextCount(3)
		.verifyComplete();
		
	}


	@Test
	public void MonoTest_withEvents() {

		Mono<String> fluxEvents = Mono.just("vinay");

		fluxEvents.subscribe(System.out::println, (e) -> System.err.println("Exception is" + e),
				() -> System.out.println("completed"));
	}
	
	

}
