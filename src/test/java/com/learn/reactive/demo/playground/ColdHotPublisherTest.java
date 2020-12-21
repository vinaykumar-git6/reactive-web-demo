package com.learn.reactive.demo.playground;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class ColdHotPublisherTest {
	
	@Test
	public void coldPublisher() throws InterruptedException {
		Flux<String> inteFlux=Flux.just("A","B","C","D","E","F")
						.delayElements(Duration.ofSeconds(1));
		
		inteFlux.subscribe(s->System.err.println("Sunsriber 1 : "+ s));
		
		Thread.sleep(2000);
		
		inteFlux.subscribe(s->System.err.println("Sunsriber 2 : "+ s));
		
		Thread.sleep(4000);
		
		
	}
	
	@Test
	public void hotPublisher() throws InterruptedException {
		Flux<String> inteFlux=Flux.just("A","B","C","D","E","F")
						.delayElements(Duration.ofSeconds(1));
		
		ConnectableFlux<String> connFlux=inteFlux.publish();
		connFlux.connect();
		
		connFlux.subscribe(s->System.err.println("Sunsriber 1 : "+ s));
		
		Thread.sleep(3000);
		
		connFlux.subscribe(s->System.err.println("Sunsriber 2 : "+ s));
		
		Thread.sleep(4000);
		
		
	}

}
