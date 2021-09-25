package com.cepheid.cloud.skel;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cepheid.cloud.skel.model.State;
import com.cepheid.cloud.skel.model.http.DescriptionDto;
import com.cepheid.cloud.skel.model.http.ItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import javax.json.JsonObject;
import net.joshka.junit.json.params.JsonFileSource;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = SkelApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemControllerTest {

  private static final String BASE_ENDPOINT = "/api/1.0/items";
  private static final String RESOURCE_ENDPOINT = "/api/1.0/items/{id}";
  private static final String ATTRIBUTES_ENDPOINT = "/api/1.0/items/attributes";

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Order(1)
  @ParameterizedTest
  @JsonFileSource(resources = "/json/get-items-response.json")
  public void testGetItems(JsonObject json) throws Exception {
    var request = get(BASE_ENDPOINT);
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(jsonPath("$.items", hasSize(4)))
        .andExpect(content().json(json.toString(), false))
        .andExpect(status().isOk());
  }

  @Order(2)
  @Test
  public void testCreateItem() throws Exception {
    ItemDto dto = ItemDto.builder()
        .name("Test item")
        .state(State.UNDEFINED)
        .descriptions(List.of(DescriptionDto.builder()
            .description("Test description of test item")
            .build()))
        .build();
    var request = post(BASE_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto));
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isCreated());

  }

  @Order(3)
  @ParameterizedTest
  @JsonFileSource(resources = "/json/get-item-by-id-response.json")
  public void testGetItemById(JsonObject json) throws Exception {
    var request = get(RESOURCE_ENDPOINT, "5");
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(content().json(json.toString(), false))
        .andExpect(status().isOk());
  }

  @Order(4)
  @ParameterizedTest
  @JsonFileSource(resources = "/json/get-items-response-after-create.json")
  public void testGetAllItems_AfterNewItemCreated(JsonObject json) throws Exception {
    var request = get(BASE_ENDPOINT);
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(jsonPath("$.items", hasSize(5)))
        .andExpect(content().json(json.toString(), false))
        .andExpect(status().isOk());
  }

  @Order(5)
  @Test
  public void testUpdateItem() throws Exception {
    ItemDto dto = ItemDto.builder()
        .name("Updated test item")
        .state(State.VALID)
        .descriptions(Collections.emptyList())
        .build();
    var request = put(RESOURCE_ENDPOINT, "5")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto));
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isNoContent());

  }

  @Order(6)
  @ParameterizedTest
  @JsonFileSource(resources = "/json/get-items-response-after-update.json")
  public void testGetItems_AfterLastItemUpdated(JsonObject json) throws Exception {
    var request = get(BASE_ENDPOINT);
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(jsonPath("$.items", hasSize(5)))
        .andExpect(content().json(json.toString(), false))
        .andExpect(status().isOk());
  }

  @Order(7)
  @Test
  public void testDeleteItem() throws Exception {
    var request = delete(RESOURCE_ENDPOINT, "5");
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isNoContent());

  }

  @Order(8)
  @ParameterizedTest
  @JsonFileSource(resources = "/json/get-items-response.json")
  public void testGetItems_AfterLastItemDeleted(JsonObject json) throws Exception {
    var request = get(BASE_ENDPOINT);
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(jsonPath("$.items", hasSize(4)))
        .andExpect(content().json(json.toString(), false))
        .andExpect(status().isOk());
  }

  @Order(9)
  @ParameterizedTest
  @JsonFileSource(resources = "/json/get-items-by-attribute-response.json")
  public void testGetItemsByAttributes(JsonObject json) throws Exception {
    var request = get(ATTRIBUTES_ENDPOINT)
        .param("state", State.VALID.name())
        .param("name", "the");

    mockMvc.perform(request)
        .andDo(print())
        .andExpect(jsonPath("$.items", hasSize(2)))
        .andExpect(content().json(json.toString(), false))
        .andExpect(status().isOk());
  }

  @Order(10)
  @Test
  public void testGetItemById_ItemNotFound() throws Exception {
    var request = get(RESOURCE_ENDPOINT, "225");
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Order(11)
  @Test
  public void testUpdateItem_ItemNotFound() throws Exception {
    ItemDto dto = ItemDto.builder()
        .name("Updated test item")
        .state(State.VALID)
        .descriptions(Collections.emptyList())
        .build();
    var request = put(RESOURCE_ENDPOINT, "225")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto));
    mockMvc.perform(request)
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", is(String.valueOf(NOT_FOUND.value()))))
        .andExpect(jsonPath("$.reason", is("Cannot find the item by id: 225")));
  }

}
