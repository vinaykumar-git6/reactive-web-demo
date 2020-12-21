package com.learn.reactive.demo.handler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.learn.reactive.demo.constants.ItemConstants;
import com.learn.reactive.demo.document.Item;
import com.learn.reactive.demo.repository.ItemReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
@SpringBootTest
//@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ItemHandlerTest {
	
	@Autowired
	WebTestClient webTestClient;

	@Autowired
	ItemReactiveRepository itemReactiveRepository;

	public List<Item> data() {
		return Arrays.asList(new Item(null, "samsung TV", 400.2), new Item(null, "LG TV", 900.2),
				new Item(null, "Apple TV", 878.2), new Item(null, "Boat TV", 145.2), new Item("ABC", "BOSE TV", 145.2));
	}

	@BeforeEach
	public void setup() {
		itemReactiveRepository.deleteAll().thenMany(Flux.fromIterable(data())).flatMap(itemReactiveRepository::save)
				.doOnNext(item -> System.out.println(" Inserted Item is :: " + item)).blockLast();
	}

	@Test
	public void getAllItems() {
		webTestClient.get().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBodyList(Item.class).hasSize(5);
	}
	
	@Test
	public void getAllItems_Approach2() {
		webTestClient.get().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBodyList(Item.class).hasSize(5).consumeWith(response -> {
					List<Item> items = response.getResponseBody();
					items.forEach((item) -> {
						assertTrue(item.getId() != null);
					});
				});
	}

	@Test
	public void getAllItems_Approach3() {
		Flux<Item> fluxItem = webTestClient.get().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON).returnResult(Item.class).getResponseBody();

		StepVerifier.create(fluxItem).expectSubscription().expectNextCount(5).verifyComplete();
	}
	
	@Test
	public void getOneItem() {
		webTestClient.get().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}"), "ABC").exchange().expectStatus().isOk()
				.expectBody().jsonPath("$.price", 145.2);

	}
	

	@Test
	public void getNotFound() {
		webTestClient.get().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}"), "ABCD").exchange().expectStatus()
				.isNotFound();
	}
	
	@Test
	public void createItem() {

		Item item = new Item(null, "Vinay Leone", 505.2);

		webTestClient.post().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(item), Item.class).exchange().expectStatus().isOk().expectBody()
				.jsonPath("$.description").isEqualTo("Vinay Leone").jsonPath("$.price").isEqualTo(505.2);
	}
	
	@Test
	public void deleteItem() {

		webTestClient.delete().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}"), "ABC")
				.accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody(Void.class);

	}
	
	
	@Test
	public void updateItem() {

		double newPrice = 129.99;
		Item item = new Item(null, "BOSE TV", newPrice);
		webTestClient.put().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}"), "ABC")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(item), Item.class).exchange().expectStatus().isOk().expectBody().jsonPath("$.price")
				.isEqualTo(newPrice);

	}

	@Test
	public void updateItem_nonExistentId() {

		double newPrice = 129.99;
		Item item = new Item(null, "BOSE TV", newPrice);
		webTestClient.put().uri(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}"), "ABCD")
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(item), Item.class).exchange().expectStatus().isNotFound();

	}

}
