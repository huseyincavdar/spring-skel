package com.cepheid.cloud.skel;

import java.util.Collection;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.cepheid.cloud.skel.model.Item;

@RunWith(SpringRunner.class)
public class ItemControllerTest extends TestBase {

  @Test
  public void testGetItems() throws Exception {
    Builder itemController = getBuilder("/app/api/1.0/items");
    
    Collection<Item> items = itemController.get(new GenericType<Collection<Item>>() {
    });

  }
}
