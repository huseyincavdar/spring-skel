package com.cepheid.cloud.skel.controller;

import static java.text.MessageFormat.format;

import com.cepheid.cloud.skel.exception.ItemNotFoundException;
import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.model.http.GetItemsResponse;
import com.cepheid.cloud.skel.model.http.ItemDto;
import java.net.URI;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// curl http:/localhost:9443/app/api/1.0/items

@RestController
@RequestMapping("/api/1.0/items")
public class ItemController {

  private final ItemRepository mItemRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public ItemController(ItemRepository itemRepository, ModelMapper modelMapper) {
    this.mItemRepository = itemRepository;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public ResponseEntity<GetItemsResponse> getItems() {
    List<Item> allItems = mItemRepository.findAll();
    return buildResponse(allItems);
  }

  @GetMapping("/{id}")
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public ResponseEntity<ItemDto> getItemById(@PathVariable Long id) {
    return mItemRepository.findById(id)
        .map(item -> modelMapper.map(item, ItemDto.class))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/attributes")
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public ResponseEntity<GetItemsResponse> getItemsByAttributes(@RequestParam("state") State state,
      @RequestParam("name") String name) {
    List<Item> items = mItemRepository.findAllByStateAndNameContainingIgnoreCase(state, name);
    return buildResponse(items);
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody ItemDto request) {
    var item = modelMapper.map(request, Item.class);
    item.setState(State.UNDEFINED);
    mItemRepository.save(item);
    var location = URI.create("/api/1.0/items/" + item.getId());
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody ItemDto request) {
    var optionalDbItem = mItemRepository.findById(id);
    if (optionalDbItem.isEmpty()) {
      throw new ItemNotFoundException(format("Cannot find the item by id: {0}", id.toString()));
    }
    var dbItem = optionalDbItem.get();
    var item = modelMapper.map(request, Item.class);
    item.setId(dbItem.getId());
    mItemRepository.save(item);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    mItemRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private ResponseEntity<GetItemsResponse> buildResponse(List<Item> dbItems) {
    List<ItemDto> items = dbItems
        .stream()
        .map(item -> modelMapper.map(item, ItemDto.class))
        .collect(Collectors.toList());
    return ResponseEntity.ok(GetItemsResponse.builder().items(items).build());
  }

}
