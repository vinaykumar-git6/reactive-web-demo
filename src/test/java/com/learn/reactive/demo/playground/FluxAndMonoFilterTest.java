package com.learn.reactive.demo.playground;

import java.util.Arrays;
import java.util.List;



import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxAndMonoFilterTest {
	
	List<String> names = Arrays.asList("vinay", "Amitha", "Sahil", "Jackson");
	
	@Test
	public void fluxTestFilter() {
		
		Flux<String> stringFlux= Flux.fromIterable(names)
				.filter(s->s.startsWith("J")).log();
		
		StepVerifier.create(stringFlux)
		.expectNext("Jackson")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTestFilterWithLength() {
		
		Flux<String> stringFlux= Flux.fromIterable(names)
				.filter(s->s.length()>6).log();
		
		StepVerifier.create(stringFlux)
		.expectNext("Jackson")
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTestFilter_FilterMapTransform() {
		
		Flux<String> stringFlux= Flux.fromIterable(Arrays.asList("A","B","C","D","E","F","G"))
				.flatMap(s->{
					return Flux.fromIterable(convertToList(s));
				}).log();
		
		StepVerifier.create(stringFlux)
		.expectNextCount(14)
		.verifyComplete();
		
	}
	
	@Test
	public void fluxTestFilter_FilterMapTransform_Parallel() {
		
		Flux<String> stringFlux= Flux.fromIterable(Arrays.asList("A","B","C","D","E","F","G"))
				.window(2)
				.flatMap(s->s.map(this::convertToList).subscribeOn(Schedulers.parallel()))
				.flatMap(s->Flux.fromIterable(s))
				.log();
				
		
		StepVerifier.create(stringFlux)
		.expectNextCount(14)
		.verifyComplete();
		
	}

	public List<String> convertToList(String s) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Arrays.asList(s,"vinayTestingFaltMap");
	}

}
