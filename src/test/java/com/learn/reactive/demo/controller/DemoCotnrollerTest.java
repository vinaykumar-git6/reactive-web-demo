package com.learn.reactive.demo.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

//@RunWith(SpringRunner.class)
@WebFluxTest
public class DemoCotnrollerTest {
	
	@Autowired
	WebTestClient webtestclient;
	
	@Test
	public void flux_approcah() {
		
	Flux<Integer> streamFlux =webtestclient.get().uri("/fluxint")
					 .accept(MediaType.APPLICATION_JSON_UTF8)
					 .exchange()
					 .expectStatus()
					 .isOk()
					 .returnResult(Integer.class)
					 .getResponseBody();
	
		StepVerifier.create(streamFlux)
		.expectSubscription()
		.expectNext(1,2,3,4)
		.verifyComplete();
	}
	
	@Test
	public void flux_approcah2() {
		
	webtestclient.get().uri("/fluxint")
					 .accept(MediaType.APPLICATION_JSON_UTF8)
					 .exchange()
					 .expectStatus()
					 .isOk()
					 .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
					 .expectBodyList(Integer.class)
					 .hasSize(4);
	
	}
	
	@Test
	public void flux_approcah3() {
		
		List<Integer> eexpectedntityExchangeResult= Arrays.asList(1,2,3,4);
		
		EntityExchangeResult<List<Integer>> entityExchangeResult= webtestclient.get().uri("/fluxint")
						 .accept(MediaType.APPLICATION_JSON_UTF8)
						 .exchange()
						 .expectStatus()
						 .isOk()
						 .expectBodyList(Integer.class)
						 .returnResult();
		
		assertEquals(eexpectedntityExchangeResult, entityExchangeResult.getResponseBody());
		
	}
	
	@Test
	public void flux_approcah4() {
		
		List<Integer> eexpectedntityExchangeResult= Arrays.asList(1,2,3,4);
		
		webtestclient.get().uri("/fluxint")
						 .accept(MediaType.APPLICATION_JSON_UTF8)
						 .exchange()
						 .expectStatus()
						 .isOk()
						 .expectBodyList(Integer.class)
						 .consumeWith(response -> {
							 assertEquals(eexpectedntityExchangeResult,response.getResponseBody() );
						 });
	
		
	}
	
	@Test
	public void flux_inifinte() {
		
		Flux<Long> streamFlux =webtestclient.get().uri("/fluxintstream/infinite")
				 .accept(MediaType.APPLICATION_STREAM_JSON)
				 .exchange()
				 .expectStatus()
				 .isOk()
				 .returnResult(Long.class)
				 .getResponseBody();

		StepVerifier.create(streamFlux)
		.expectSubscription()
		.expectNext(0L)
		.expectNext(1L)
		.expectNext(2L)
		.thenCancel()
		.verify();
	}
	
	@Test
	public void mono_test() {
		
		Flux<Integer> streamFlux =webtestclient.get().uri("/monoint")
				 .accept(MediaType.APPLICATION_JSON)
				 .exchange()
				 .expectStatus()
				 .isOk()
				 .returnResult(Integer.class)
				 .getResponseBody();

		StepVerifier.create(streamFlux)
		.expectSubscription()
		.expectNext(1)
		.verifyComplete();
	}
	
	

}
