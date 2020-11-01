package com.example.project.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.project.dtos.responses.ClientResponse;
import com.example.project.dtos.responses.ItemResponse;
import com.example.project.dtos.responses.ReceiptResponse;
import com.example.project.exception.EntityDoesNotExist;
import com.example.project.models.Client;
import com.example.project.models.Item;
import com.example.project.repositories.ItemRepository;
import com.example.project.services.ItemService;


@Service
public class ItemServiceImpl implements ItemService {
	
	private final ItemRepository itemRepository;
	
	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Override
	public List<ItemResponse> findAll() {
		
		return mapToItemResponseList(itemRepository.findAll());
	}

	@Override
	public ItemResponse create(Item item) {
		return mapToItemResponse(itemRepository.save(item));
	}

	@Override
	public void delete(Integer id) {
		
		Item item = itemRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
		itemRepository.delete(item);
	}

	@Override
	public ItemResponse update(Item item) {
		
	itemRepository.findById(item.getItemId())
				.orElseThrow(()-> new EntityDoesNotExist(item.getItemId()));
		
		return mapToItemResponse(itemRepository.save(item));
	}

	@Override
	public ItemResponse findById(Integer id) {
		
		Item item =  itemRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
		return mapToItemResponse(item);
	}

	@Override
	public List<ItemResponse> findByReceiptId(Integer receiptId) {
		return mapToItemResponseList(itemRepository.findByReceiptReceiptId(receiptId));

	}
	
	@Override
	public List<ItemResponse> findByAuthuserId(Integer userId) {
		return mapToItemResponseList(itemRepository.findAllByReceiptClientCompanyAuthuserId(userId));
	}
	
	
    private List<ItemResponse> mapToItemResponseList(List<Item> items) {
        return items.stream().map(this::mapToItemResponse).collect(Collectors.toList());
    }

    private ItemResponse mapToItemResponse(Item item) {
        return ItemResponse.builder()
            .itemId(item.getItemId())
            .price(item.getPrice())
            .measure(item.getMeasure())
            .name(item.getName())
            .receipt(item.getReceipt())
            .totalPrice(item.getTotalPrice())
            .build();
    }
}
