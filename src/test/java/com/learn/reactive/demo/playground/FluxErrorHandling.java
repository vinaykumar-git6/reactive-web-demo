package com.learn.reactive.demo.playground;

import java.time.Duration;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.Test;

import com.learn.reactive.demo.exception.CustomException;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

public class FluxErrorHandling {

	@Test
	public void fluxErrorHandler1() {
		Flux<String> fluxString = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Error occured")))
				.concatWith(Flux.just("D")).onErrorResume(e -> {
					System.out.println("Exception occured" + e);
					return Flux.just("default", "defalut2");

				});

		StepVerifier.create(fluxString.log()).expectSubscription().expectNext("A", "B", "C") // .expectError(RuntimeException.class)
				.expectNext("default", "defalut2").verifyComplete();

	}

	@Test
	public void fluxErrorHandler_OnErrorReturn() {
		Flux<String> fluxString = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Error occured")))
				.concatWith(Flux.just("D")).onErrorReturn("default");

		StepVerifier.create(fluxString.log()).expectSubscription().expectNext("A", "B", "C") // .expectError(RuntimeException.class)
				.expectNext("default").verifyComplete();

	}

	@Test
	public void fluxErrorHandler_OnErrorMap() {
		Flux<String> fluxString = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Error occured")))
				.concatWith(Flux.just("D")).onErrorMap((e) -> new CustomException(e));

		StepVerifier.create(fluxString.log()).expectSubscription().expectNext("A", "B", "C")
				.expectError(CustomException.class).verify();

	}

	@Test
	public void fluxErrorHandler_OnErrorMapWithRetry() {
		Flux<String> fluxString = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Error occured")))
				.concatWith(Flux.just("D")).onErrorMap((e) -> new CustomException(e)).retry(2);

		StepVerifier.create(fluxString.log()).expectSubscription().expectNext("A", "B", "C").expectNext("A", "B", "C")
				.expectNext("A", "B", "C").expectError(CustomException.class).verify();

	}

	@Test
	public void fluxErrorHandler_OnErrorMapWithRetryWithBackoff() {
		Flux<String> fluxString = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Error occured")))
				.concatWith(Flux.just("D")).onErrorMap((e) -> new CustomException(e))
				.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))).onErrorMap((e) -> new CustomException(e));

		StepVerifier.create(fluxString.log()).expectSubscription().expectNext("A", "B", "C").expectNext("A", "B", "C")
				.expectNext("A", "B", "C").expectNext("A", "B", "C").expectError(CustomException.class).verify();

	}
}
