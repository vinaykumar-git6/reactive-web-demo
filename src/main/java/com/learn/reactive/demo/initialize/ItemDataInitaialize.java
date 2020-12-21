package com.learn.reactive.demo.initialize;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.learn.reactive.demo.document.Item;
import com.learn.reactive.demo.repository.ItemReactiveRepository;

import reactor.core.publisher.Flux;

@Component
@Profile("!test")
public class ItemDataInitaialize implements CommandLineRunner{
	
	@Autowired
	ItemReactiveRepository itemReactiveRepository;

	@Override
	public void run(String... args) throws Exception {
		initilizedata();
		
	}
	
	public List<Item> data(){
		return Arrays.asList(new Item(null, "samsung TV", 400.2),
				new Item(null, "LG TV", 900.2),
				new Item(null, "Apple TV", 878.2),
				new Item(null, "Boat TV", 145.2),
				new Item("ABC", "BOSE TV", 145.2));
	}

	private void initilizedata() {
		
		itemReactiveRepository.deleteAll()
        .thenMany(Flux.fromIterable(data()))
                .flatMap(itemReactiveRepository::save)
                .thenMany(itemReactiveRepository.findAll())
                .subscribe((item -> {
                    System.out.println("Item inserted from CommandLineRunner : " + item);
                }));
				
	}

}
