package com.learn.reactive.demo.handler;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class FluxHandlerTest {
	
	@Autowired
	WebTestClient webtestclient;
	
	@Test
	public void flux_approcah() {
		
	Flux<Integer> streamFlux =webtestclient.get().uri("/function/flux")
					 .accept(MediaType.APPLICATION_JSON)
					 .exchange()
					 .expectStatus()
					 .isOk()
					 .returnResult(Integer.class)
					 .getResponseBody();
	
		//System.out.println("no of process : " +Runtime.getRuntime().availableProcessors());
	
		StepVerifier.create(streamFlux)
		.expectSubscription()
		.expectNext(1,2,3,4)
		.verifyComplete();
	}

}
