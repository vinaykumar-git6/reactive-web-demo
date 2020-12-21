package com.learn.reactive.demo.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;


import com.learn.reactive.demo.document.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
//@RunWith(SpringRunner.class)
@DirtiesContext
public class ItemReactiveRepositoryTest {
	
	@Autowired
	ItemReactiveRepository itemReactiveRepository;
	
	List<Item> itemList= Arrays.asList(new Item(null, "samsung TV", 400.2),
			new Item(null, "LG TV", 900.2),
			new Item(null, "Apple TV", 878.2),
			new Item(null, "Boat TV", 145.2),
			new Item("ABC", "BOSE TV", 145.2));
	
	@BeforeEach
	public void setup() {
		itemReactiveRepository.deleteAll()
			.thenMany(Flux.fromIterable(itemList))
			.flatMap(itemReactiveRepository::save)
			.doOnNext((item -> {
				System.out.println("Inserted Item is : " + item);
			})).blockLast();
		
	}
	
	@Test
	public void getAllItems() {
		StepVerifier.create(itemReactiveRepository.findAll())
		.expectSubscription()
		.expectNextCount(5)
		.verifyComplete();
	}
	
	@Test
	public void getItemById() {
		StepVerifier.create(itemReactiveRepository.findById("ABC"))
		.expectSubscription()
		.expectNextMatches(item -> item.getDescription().equals("BOSE TV"))
		.verifyComplete();
	}
	
	@Test
	public void getItemByDesc() {
		StepVerifier.create(itemReactiveRepository.findByDescription("BOSE TV"))
		.expectSubscription()
		.expectNextMatches(item -> item.getId().equals("ABC"))
		.verifyComplete();
	}
	
	@Test
	public void saveItem() {
		
		Item item= new Item(null, "Google Home TV", 145.252);
		
		Mono<Item> savedItem= itemReactiveRepository.save(item);
		StepVerifier.create(savedItem.log("SavedItem : "))
					.expectSubscription()
					.expectNextMatches(item1 -> item1.getId()!=null && item1.getId().equals("Google Home TV"));
	
	}
	
	@Test
	public void updateItem() {
		double newPrice=1000.00;
		Mono<Item> updatedItem = itemReactiveRepository.findByDescription("BOSE TV")
								.map( item -> {
									item.setPrice(newPrice);
									return item;
								})
								.flatMap((item) -> {
									return itemReactiveRepository.save(item);
								});
		StepVerifier.create(updatedItem)
			.expectSubscription()
			.expectNextMatches(item1 -> item1.getId()!=null && item1.getPrice()==1000.00);
		
		}
	
	@Test
	public void delteItemById() {
		Mono<Void> deletedItem= itemReactiveRepository.findById("ABC")
							 .map(Item::getId)
							 .flatMap((id) -> {
								 return itemReactiveRepository.deleteById(id);
							 });
						
		StepVerifier.create(deletedItem)
			.expectSubscription()
			.verifyComplete();
		
		StepVerifier.create(itemReactiveRepository.findAll().log("New Item List After Deletetion :::::: "))
		.expectSubscription()
		.expectNextCount(4)
		.verifyComplete();
		
	}
	
	@Test
	public void delteItemByDesc() {
		Mono<Void> deletedItem= itemReactiveRepository.findByDescription("BOSE TV")
							 .flatMap((item) -> {
								 return itemReactiveRepository.delete(item);
							 });
						
		StepVerifier.create(deletedItem)
			.expectSubscription()
			.verifyComplete();
		
		StepVerifier.create(itemReactiveRepository.findAll().log("New Item List After Deletetion :::::: "))
		.expectSubscription()
		.expectNextCount(4)
		.verifyComplete();
		
	}
	
	
	}


