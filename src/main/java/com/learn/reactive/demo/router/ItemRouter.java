package com.learn.reactive.demo.router;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.learn.reactive.demo.constants.ItemConstants;
import com.learn.reactive.demo.handler.ItemHandler;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
@Configuration
public class ItemRouter {
	
	@Bean
	public RouterFunction<ServerResponse> itemRouter1(ItemHandler itemHandler){
		
		return RouterFunctions.route(GET(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).and(accept(MediaType.APPLICATION_JSON)),itemHandler::getAllItems)
				.andRoute(GET(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1+"/{id}").and(accept(MediaType.APPLICATION_JSON)),itemHandler::getOneItem)
				.andRoute(POST(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).and(accept(MediaType.APPLICATION_JSON)),itemHandler::createItem)
				.andRoute(DELETE(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1+"/{id}").and(accept(MediaType.APPLICATION_JSON)),itemHandler::deleteItem)
				.andRoute(PUT(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1+"/{id}").and(accept(MediaType.APPLICATION_JSON)),itemHandler::updateItem);
		
	}

}
