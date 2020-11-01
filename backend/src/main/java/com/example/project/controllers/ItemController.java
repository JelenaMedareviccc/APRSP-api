package com.example.project.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dtos.responses.ClientResponse;
import com.example.project.dtos.responses.ItemResponse;
import com.example.project.models.Item;
import com.example.project.services.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	private final ItemService itemService;
	
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<ItemResponse> getItems(){
		return itemService.findAll();
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/itemId/{id}")
	public ItemResponse getItem(@PathVariable("id") Integer id) {
		return itemService.findById(id);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/receipt/{receiptId}")
	public List<ItemResponse> findItemByreceipt(@PathVariable Integer receiptId){
		return itemService.findByReceiptId(receiptId);	
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ItemResponse addItem(@Valid @RequestBody Item item){
		return itemService.create(item);
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public ItemResponse updateItem(@Valid @RequestBody Item item){
			return itemService.update(item);
		
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void deleteItem(@PathVariable("id") Integer id){
			itemService.delete(id);
		
	}
	
	@CrossOrigin
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{id}")
	public List<ItemResponse> getItemByUserId(@PathVariable Integer id){
		return itemService.findByAuthuserId(id);

	}
}
