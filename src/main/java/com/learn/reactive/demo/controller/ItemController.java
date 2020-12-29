package com.learn.reactive.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.reactive.demo.document.Item;
import com.learn.reactive.demo.repository.ItemReactiveRepository;
import com.learn.reactive.demo.constants.ItemConstants;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemController {
	
	@Autowired
	ItemReactiveRepository itemReactiveRepository;
	
	@GetMapping(ItemConstants.ITEM_END_POINT_V1)
	public Flux<Item> getAllItems(){
		
		return itemReactiveRepository.findAll();
		
	}
	
	@GetMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
	public Mono<ResponseEntity<Item>> getOneItem(@PathVariable String id){
		
		return itemReactiveRepository.findById(id)
				.map( (item) -> new ResponseEntity<>(item,  HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		
	}
	
	@GetMapping("")
	public String getOneItems(){
		
		return "health check";
		
	}
	
	@PostMapping(ItemConstants.ITEM_END_POINT_V1)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Item> createItem(@RequestBody Item item){
		
		return itemReactiveRepository.save(item);
				
		
	}
	
	@DeleteMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
	public Mono<Void> deleteeItem(@PathVariable String id){
		
		return itemReactiveRepository.deleteById(id);
				
		
	}
	
	@PutMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
	public Mono<ResponseEntity<Item>> updateItem(@PathVariable String id, @RequestBody Item item){
		
		return itemReactiveRepository.findById(id)
		.flatMap(currentItem -> {
			currentItem.setDescription(item.getDescription());
			currentItem.setPrice(item.getPrice());
			return itemReactiveRepository.save(currentItem);
		}).map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
				
		
	}

}
