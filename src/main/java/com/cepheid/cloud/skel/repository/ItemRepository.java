package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.State;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cepheid.cloud.skel.model.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findAllByStateAndNameContainingIgnoreCase(State state, String name);
}
