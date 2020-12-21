package com.learn.reactive.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import com.learn.reactive.demo.document.Item;
import com.learn.reactive.demo.repository.ItemReactiveRepository;

import reactor.core.publisher.Mono;

@Component
public class ItemHandler {
	
	@Autowired
	ItemReactiveRepository itemReactiveRepository;
	
	static Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	public Mono<ServerResponse> getAllItems(ServerRequest serverReq){
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(itemReactiveRepository.findAll(),Item.class);
		
	}
	
	public Mono<ServerResponse> getOneItem(ServerRequest serverReq){
			String id =serverReq.pathVariable("id");
			Mono<Item> itemMono = itemReactiveRepository.findById(id);
			
			return itemMono.flatMap(item -> ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(fromObject(item)))
					.switchIfEmpty(notFound);
			
		}
	
	public Mono<ServerResponse> createItem(ServerRequest serverReq){
		
		Mono<Item> itemtoBeInserted= serverReq.bodyToMono(Item.class);
		return itemtoBeInserted.flatMap(item -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(itemReactiveRepository.save(item),Item.class));
		
	}
	
	public Mono<ServerResponse> deleteItem(ServerRequest serverReq){
		
		String id =serverReq.pathVariable("id");
		Mono<Void> deletedItem = itemReactiveRepository.deleteById(id);
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(deletedItem,Void.class);
		
		
	}
	
	public Mono<ServerResponse> updateItem(ServerRequest serverReq){
		
		String id =serverReq.pathVariable("id");
		Mono<Item> updatedItem= serverReq.bodyToMono(Item.class).flatMap(item -> {
			Mono<Item> monoItem = itemReactiveRepository.findById(id).flatMap(currentItem ->{
				currentItem.setDescription(item.getDescription());
				currentItem.setPrice(item.getPrice());
				return itemReactiveRepository.save(currentItem);
			});
			return monoItem;
		});
		return updatedItem.flatMap( item -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(updatedItem,Item.class)).switchIfEmpty(notFound);
		
	}

}
