package br.com.valerianosoft.client.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import br.com.valerianosoft.client.business.ClientBusiness;
import br.com.valerianosoft.client.common.exception.EntityNotFoundException;
import br.com.valerianosoft.client.common.response.Response;
import br.com.valerianosoft.client.config.RestControllerAdvice;
import br.com.valerianosoft.client.model.Client;
import br.com.valerianosoft.client.v1.filter.ClientFilter;
import br.com.valerianosoft.client.v1.request.ClientContactRequest;
import br.com.valerianosoft.client.v1.request.ClientRequest;
import br.com.valerianosoft.client.v1.response.ClientContactResponse;
import br.com.valerianosoft.client.v1.response.ClientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class ClientControllerTest {

  @Mock
  private ClientBusiness business;

  private ObjectMapper mapper;

  private ClientController controller;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    this.controller = new ClientController(this.business);
    this.mapper = new ObjectMapper().findAndRegisterModules();
    this.mockMvc = standaloneSetup(this.controller)
        .setControllerAdvice(new RestControllerAdvice())
        .build();
  }

  @Test
  @DisplayName("should create client")
  void create() throws Exception {
    final var request = ClientRequest.builder()
        .name("Create Test")
        .age(25)
        .document("99999999999")
        .phone("99999999999")
        .email("test@test.com")
        .build();

    final var client = buildClient(UUID.randomUUID());

    when(this.business.create(any(Client.class))).thenReturn(client);

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
    assertEquals(client.getUuid(), clientResponse.getUuid());
    assertEquals(client.getName(), clientResponse.getName());
    assertEquals(client.getDocument(), clientResponse.getDocument());
    assertEquals(client.getPhone(), clientResponse.getPhone());
    assertEquals(client.getEmail(), clientResponse.getEmail());
  }

  @Test
  @DisplayName("should create client when name is invalid")
  void createWhenNameIsInvalid() throws Exception {
    final var request = ClientRequest.builder()
        .name("")
        .age(25)
        .document("99999999999")
        .phone("99999999999")
        .email("test@test.com")
        .build();

    when(this.business.create(any(Client.class))).thenReturn(mock(Client.class));

    this.mockMvc
        .perform(
            post("/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("should create client when age is invalid")
  void createWhenAgeIsInvalid() throws Exception {
    final var request = ClientRequest.builder()
        .name("Create Test")
        .age(0)
        .document("99999999999")
        .phone("99999999999")
        .email("test@test.com")
        .build();

    when(this.business.create(any(Client.class))).thenReturn(mock(Client.class));

    this.mockMvc
        .perform(
            post("/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("should create client when document is invalid")
  void createWhenDocumentIsInvalid() throws Exception {
    final var request = ClientRequest.builder()
        .name("Create Test")
        .age(25)
        .document("9")
        .phone("99999999999")
        .email("test@test.com")
        .build();

    when(this.business.create(any(Client.class))).thenReturn(mock(Client.class));

    this.mockMvc
        .perform(
            post("/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("should create client when phone is invalid")
  void createWhenPhoneIsInvalid() throws Exception {
    final var request = ClientRequest.builder()
        .name("Create Test")
        .age(25)
        .document("99999999999")
        .phone("9")
        .email("test@test.com")
        .build();

    when(this.business.create(any(Client.class))).thenReturn(mock(Client.class));

    this.mockMvc
        .perform(
            post("/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("should create client when email is invalid")
  void createWhenEmailIsInvalid() throws Exception {
    final var request = ClientRequest.builder()
        .name("Create Test")
        .age(25)
        .document("99999999999")
        .phone("99999999999")
        .email("test")
        .build();

    when(this.business.create(any(Client.class))).thenReturn(mock(Client.class));

    this.mockMvc
        .perform(
            post("/v1/clients/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("should show client")
  void shouldShow() throws Exception {

    final var client = buildClient(UUID.randomUUID());

    when(this.business.show(any(UUID.class))).thenReturn(client);

    final var response = this.mockMvc
        .perform(get("/v1/clients/".concat(client.getUuid().toString())))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());

    final var clientResponse = this.mapper.convertValue(responseObj.getData().get(0), ClientResponse.class);

    assertNotNull(clientResponse);
    assertEquals(client.getUuid(), clientResponse.getUuid());
    assertEquals(client.getName(), clientResponse.getName());
    assertEquals(client.getDocument(), clientResponse.getDocument());
    assertEquals(client.getPhone(), clientResponse.getPhone());
    assertEquals(client.getEmail(), clientResponse.getEmail());

  }

  @Test
  @DisplayName("should show client not found")
  void shouldShowNotFound() throws Exception {

    final var client = buildClient(UUID.randomUUID());

    when(this.business.show(any(UUID.class))).thenThrow(new EntityNotFoundException("Client"));

    this.mockMvc
        .perform(get("/v1/clients/".concat(client.getUuid().toString())))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("should update client")
  void shouldUpdate() throws Exception {

    final var request = ClientRequest.builder()
        .name("Create Test")
        .age(25)
        .document("99999999999")
        .phone("99999999999")
        .email("test@test.com")
        .build();

    final var client = buildClient(UUID.randomUUID());

    when(this.business.show(any(UUID.class))).thenReturn(client);
    when(this.business.update(any(Client.class))).thenReturn(client);

    final var response = this.mockMvc
        .perform(
            put("/v1/clients/".concat(client.getUuid().toString()))
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
    assertEquals(client.getUuid(), clientResponse.getUuid());
    assertEquals(client.getName(), clientResponse.getName());
    assertEquals(client.getDocument(), clientResponse.getDocument());
    assertEquals(client.getPhone(), clientResponse.getPhone());
    assertEquals(client.getEmail(), clientResponse.getEmail());

  }

  @Test
  @DisplayName("should update client contact")
  void shouldUpdateContact() throws Exception {

    final var request = ClientContactRequest.builder()
        .phone("99999999999")
        .email("test@test.com")
        .build();

    final var client = buildClient(UUID.randomUUID());

    when(this.business.show(any(UUID.class))).thenReturn(client);
    when(this.business.update(any(Client.class))).thenReturn(client);

    final var response = this.mockMvc
        .perform(
            patch("/v1/clients/".concat(client.getUuid().toString().concat("/contact")))
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
    assertEquals(client.getUuid(), clientResponse.getUuid());
    assertEquals(client.getName(), clientResponse.getName());
    assertEquals(client.getPhone(), clientResponse.getPhone());
    assertEquals(client.getEmail(), clientResponse.getEmail());

  }

  @Test
  @DisplayName("should update client contact when phone is invalid")
  void shouldUpdateContactWhenPhoneIsInvalid() throws Exception {

    final var request = ClientContactRequest.builder()
        .phone("9")
        .email("test@test.com")
        .build();

    final var client = buildClient(UUID.randomUUID());

    when(this.business.show(any(UUID.class))).thenReturn(client);
    when(this.business.update(any(Client.class))).thenReturn(client);

    this.mockMvc
        .perform(
            patch("/v1/clients/".concat(client.getUuid().toString().concat("/contact")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

  }

  @Test
  @DisplayName("should update client contact when email is invalid")
  void shouldUpdateContactWhenEmailIsInvalid() throws Exception {

    final var request = ClientContactRequest.builder()
        .phone("99999999999")
        .email("test")
        .build();

    final var client = buildClient(UUID.randomUUID());

    when(this.business.show(any(UUID.class))).thenReturn(client);
    when(this.business.update(any(Client.class))).thenReturn(client);

    this.mockMvc
        .perform(
            patch("/v1/clients/".concat(client.getUuid().toString().concat("/contact")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("should find all clients")
  void shouldFindAll() throws Exception {
    final var client = buildClient(UUID.randomUUID());
    final var pageable = PageRequest.of(0, 5);

    when(this.business.findAll(any(Pageable.class), any(ClientFilter.class)))
        .thenReturn(new PageImpl<>(Collections.singletonList(client), pageable, 1));

    final var response = this.mockMvc.perform(get("/v1/clients"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    final var responseObj = this.mapper.readValue(response, Response.class);

    assertNotNull(responseObj);
    assertFalse(responseObj.getData().isEmpty());

    final var clientResponse = this.mapper.convertValue(responseObj.getData().get(0), ClientResponse.class);

    assertNotNull(clientResponse);
    assertEquals(client.getUuid(), clientResponse.getUuid());
    assertEquals(client.getName(), clientResponse.getName());
    assertEquals(client.getDocument(), clientResponse.getDocument());
    assertEquals(client.getPhone(), clientResponse.getPhone());
    assertEquals(client.getEmail(), clientResponse.getEmail());
  }

  @Test
  @DisplayName("should delete client")
  void shouldDelete() throws Exception {

    doNothing().when(this.business).delete(any(UUID.class));

    this.mockMvc
        .perform(delete("/v1/clients/".concat(UUID.randomUUID().toString())))
        .andExpect(status().isNoContent());
  }

  private Client buildClient(final UUID uuid) {
    return Client.builder()
        .uuid(uuid)
        .name("TEST NAME")
        .document("99999999999")
        .phone("99999999999")
        .email("test@test.com")
        .build();
  }

}