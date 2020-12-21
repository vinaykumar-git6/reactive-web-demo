package com.learn.reactive.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.learn.reactive.demo.document.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String> {
	
	Mono<Item> findByDescription(String desccription);
	

}
