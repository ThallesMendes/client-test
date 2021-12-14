package br.com.valerianosoft.integration.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import br.com.valerianosoft.client.common.response.Response;
import br.com.valerianosoft.client.v1.request.ClientContactRequest;
import br.com.valerianosoft.client.v1.request.ClientRequest;
import br.com.valerianosoft.client.v1.response.ClientContactResponse;
import br.com.valerianosoft.client.v1.response.ClientResponse;
import br.com.valerianosoft.integration.BaseIT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class ClientIT extends BaseIT {

  private MockMvc mockMvc;
  private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  static UUID clientId;

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    this.mockMvc = webAppContextSetup(context).build();
  }

  @Test
  @Order(1)
  @DisplayName("should create client")
  void shouldCreate() throws Exception {

    final var request = ClientRequest.builder()
        .name("Create Test")
        .age(25)
        .document("99999999999")
        .phone("99999999999")
        .email("test@test.com")
        .build();

    final var response = this.mockMvc
        .perform(
            post("/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());

    final var clientResponse = this.mapper.convertValue(responseObj.getData().get(0), ClientResponse.class);

    assertNotNull(clientResponse);
    assertEquals(request.getName(), clientResponse.getName());
    assertEquals(request.getDocument(), clientResponse.getDocument());
    assertEquals(request.getPhone(), clientResponse.getPhone());
    assertEquals(request.getEmail(), clientResponse.getEmail());
    assertNotNull(clientResponse.getCreatedAt());
    assertNull(clientResponse.getUpdatedAt());
    assertNotNull(clientResponse.getUuid());

    clientId = clientResponse.getUuid();

  }

  @Test
  @Order(2)
  @DisplayName("should show client by id")
  void shouldShow() throws Exception {
    final var response = this.mockMvc
        .perform(
            get("/v1/clients/".concat(clientId.toString())))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());

    final var clientResponse = this.mapper.convertValue(responseObj.getData().get(0), ClientResponse.class);

    assertNotNull(clientResponse);
    assertEquals(clientId, clientResponse.getUuid());
  }

  @Test
  @Order(3)
  @DisplayName("should find all clients")
  void shouldFindAll() throws Exception {
    final var response = this.mockMvc
        .perform(
            get("/v1/clients"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());
    assertEquals(1, responseObj.getData().size());
  }

  @Test
  @Order(4)
  @DisplayName("should update client")
  void shouldUpdate() throws Exception {

    final var request = ClientRequest.builder()
        .name("Create Test Updated")
        .age(26)
        .document("99999999999")
        .phone("99999999999")
        .email("testupdated@test.com")
        .build();

    final var response = this.mockMvc
        .perform(
            put("/v1/clients/".concat(clientId.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());

    final var clientResponse = this.mapper.convertValue(responseObj.getData().get(0), ClientResponse.class);

    assertNotNull(clientResponse);
    assertEquals(request.getName(), clientResponse.getName());
    assertEquals(request.getDocument(), clientResponse.getDocument());
    assertEquals(request.getPhone(), clientResponse.getPhone());
    assertEquals(request.getEmail(), clientResponse.getEmail());
    assertNotNull(clientResponse.getCreatedAt());
    assertNotNull(clientResponse.getUpdatedAt());
    assertNotNull(clientResponse.getUuid());

  }

  @Test
  @Order(5)
  @DisplayName("should update client contact")
  void shouldUpdateContact() throws Exception {

    final var request = ClientContactRequest.builder()
        .phone("89999999999")
        .email("testupdated@test.com")
        .build();

    final var response = this.mockMvc
        .perform(
            patch("/v1/clients/".concat(clientId.toString().concat("/contact")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());

    final var clientResponse = this.mapper.convertValue(responseObj.getData().get(0), ClientContactResponse.class);

    assertNotNull(clientResponse);
    assertEquals(request.getPhone(), clientResponse.getPhone());
    assertEquals(request.getEmail(), clientResponse.getEmail());
    assertNotNull(clientResponse.getUuid());

  }

  @Test
  @Order(6)
  @DisplayName("should delete client")
  void shouldDelete() throws Exception {

    this.mockMvc
        .perform(
            delete("/v1/clients/".concat(clientId.toString()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

  @Test
  @Order(7)
  @DisplayName("should find all clients when deleted is false")
  void shouldFindWhenAllDeletedIsFalse() throws Exception {
    final var response = this.mockMvc
        .perform(
            get("/v1/clients"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertTrue(responseObj.getData().isEmpty());
  }

  @Test
  @Order(8)
  @DisplayName("should find all clients when deleted is true")
  void shouldFindWhenAllDeletedIsTrue() throws Exception {
    final var response = this.mockMvc
        .perform(
            get("/v1/clients?deleted=true"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());
    assertEquals(1, responseObj.getData().size());
  }

  @Test
  @Order(9)
  @DisplayName("should find all clients when deleted is true and name is test")
  void shouldFindWhenAllDeletedIsTrueAndNameIsTest() throws Exception {
    final var response = this.mockMvc
        .perform(
            get("/v1/clients?deleted=true&name=test"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());
    assertEquals(1, responseObj.getData().size());
  }

  @Test
  @Order(10)
  @DisplayName("should find all clients when deleted is true and name is undefined")
  void shouldFindWhenAllDeletedIsTrueAndNameIsUndefined() throws Exception {
    final var response = this.mockMvc
        .perform(
            get("/v1/clients?deleted=true&name=undefined"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertTrue(responseObj.getData().isEmpty());
  }

}
