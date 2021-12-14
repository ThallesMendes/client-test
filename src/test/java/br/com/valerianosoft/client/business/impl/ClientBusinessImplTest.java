package br.com.valerianosoft.client.business.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.valerianosoft.client.business.ClientBusiness;
import br.com.valerianosoft.client.common.exception.EntityNotFoundException;
import br.com.valerianosoft.client.model.Client;
import br.com.valerianosoft.client.repository.ClientRepository;
import br.com.valerianosoft.client.v1.filter.ClientFilter;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ClientBusinessImplTest {

  private ClientBusiness business;

  @Mock
  private ClientRepository repository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    this.business = new ClientBusinessImpl(this.repository);
  }

  @Test
  @DisplayName("should create client")
  void shouldCreate() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    when(this.repository.save(any(Client.class))).thenReturn(client);

    final var retrieved = this.business.create(client);

    verify(this.repository, times(1)).save(any(Client.class));

    assertNotNull(retrieved);
    assertEquals(client.getUuid(), retrieved.getUuid());
  }

  @Test
  @DisplayName("should show existing client")
  void shouldShowExistingClient() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    when(this.repository.findById(any(UUID.class))).thenReturn(Optional.of(client));

    final var retrieved = this.business.show(client.getUuid());

    verify(this.repository, times(1)).findById(any(UUID.class));

    assertNotNull(retrieved);
    assertEquals(client.getUuid(), retrieved.getUuid());
  }

  @Test
  @DisplayName("should show client when not found")
  void shouldShowClientWhenNotFound() {
    when(this.repository.findById(any(UUID.class))).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> this.business.show(UUID.randomUUID()));

    verify(this.repository, times(1)).findById(any(UUID.class));
  }

  @Test
  @DisplayName("should find all clients when filter is empty")
  @SuppressWarnings("unchecked")
  void shouldFindAllWhenFilterIsEmpty() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    final var page = new PageImpl<>(Collections.singletonList(client));
    final var pageable = PageRequest.of(0, 5);

    final var exampleCapt = ArgumentCaptor.forClass(Example.class);

    when(this.repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

    final var retrieved = this.business.findAll(pageable, new ClientFilter());

    verify(this.repository, times(1))
        .findAll(exampleCapt.capture(), any(Pageable.class));

    final var exampleValue = (Example<Client>) exampleCapt.getValue();

    assertNotNull(retrieved);
    assertNotNull(exampleValue);
    assertEquals(1L, retrieved.getTotalElements());
    assertEquals(client.getUuid(), retrieved.getContent().get(0).getUuid());
    assertNull(exampleValue.getProbe().getDocument());
    assertNull(exampleValue.getProbe().getName());
    assertFalse(exampleValue.getProbe().getDeleted());

  }

  @Test
  @DisplayName("should find all clients when filter contains name")
  @SuppressWarnings("unchecked")
  void shouldFindAllWhenFilterContainsName() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    final var page = new PageImpl<>(Collections.singletonList(client));
    final var pageable = PageRequest.of(0, 5);
    final var filter = new ClientFilter();
    filter.setName("T");

    final var exampleCapt = ArgumentCaptor.forClass(Example.class);

    when(this.repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

    final var retrieved = this.business.findAll(pageable, filter);

    verify(this.repository, times(1))
        .findAll(exampleCapt.capture(), any(Pageable.class));

    final var exampleValue = (Example<Client>) exampleCapt.getValue();

    assertNotNull(retrieved);
    assertNotNull(exampleValue);
    assertEquals(1L, retrieved.getTotalElements());
    assertEquals(client.getUuid(), retrieved.getContent().get(0).getUuid());
    assertNull(exampleValue.getProbe().getDocument());
    assertNotNull(exampleValue.getProbe().getName());
    assertEquals("T", exampleValue.getProbe().getName());
    assertFalse(exampleValue.getProbe().getDeleted());

  }

  @Test
  @DisplayName("should find all clients when filter contains document")
  @SuppressWarnings("unchecked")
  void shouldFindAllWhenFilterContainsDocument() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    final var page = new PageImpl<>(Collections.singletonList(client));
    final var pageable = PageRequest.of(0, 5);
    final var filter = new ClientFilter();
    filter.setDocument("0");

    final var exampleCapt = ArgumentCaptor.forClass(Example.class);

    when(this.repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

    final var retrieved = this.business.findAll(pageable, filter);

    verify(this.repository, times(1))
        .findAll(exampleCapt.capture(), any(Pageable.class));

    final var exampleValue = (Example<Client>) exampleCapt.getValue();

    assertNotNull(retrieved);
    assertNotNull(exampleValue);
    assertEquals(1L, retrieved.getTotalElements());
    assertEquals(client.getUuid(), retrieved.getContent().get(0).getUuid());
    assertNotNull(exampleValue.getProbe().getDocument());
    assertEquals("0", exampleValue.getProbe().getDocument());
    assertNull(exampleValue.getProbe().getName());
    assertFalse(exampleValue.getProbe().getDeleted());

  }

  @Test
  @DisplayName("should find all clients when filter with deleted true")
  @SuppressWarnings("unchecked")
  void shouldFindAllWhenFilterWithDeletedTrue() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    final var page = new PageImpl<>(Collections.singletonList(client));
    final var pageable = PageRequest.of(0, 5);
    final var filter = new ClientFilter();
    filter.setDeleted(true);

    final var exampleCapt = ArgumentCaptor.forClass(Example.class);

    when(this.repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

    final var retrieved = this.business.findAll(pageable, filter);

    verify(this.repository, times(1))
        .findAll(exampleCapt.capture(), any(Pageable.class));

    final var exampleValue = (Example<Client>) exampleCapt.getValue();

    assertNotNull(retrieved);
    assertNotNull(exampleValue);
    assertEquals(1L, retrieved.getTotalElements());
    assertEquals(client.getUuid(), retrieved.getContent().get(0).getUuid());
    assertNull(exampleValue.getProbe().getDocument());
    assertNull(exampleValue.getProbe().getName());
    assertTrue(exampleValue.getProbe().getDeleted());

  }

  @Test
  @DisplayName("should find all clients when filter contains name and document")
  @SuppressWarnings("unchecked")
  void shouldFindAllWhenFilterContainsNameAndDocument() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    final var page = new PageImpl<>(Collections.singletonList(client));
    final var pageable = PageRequest.of(0, 5);
    final var filter = new ClientFilter();
    filter.setName("T");
    filter.setDocument("0");

    final var exampleCapt = ArgumentCaptor.forClass(Example.class);

    when(this.repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

    final var retrieved = this.business.findAll(pageable, filter);

    verify(this.repository, times(1))
        .findAll(exampleCapt.capture(), any(Pageable.class));

    final var exampleValue = (Example<Client>) exampleCapt.getValue();

    assertNotNull(retrieved);
    assertNotNull(exampleValue);
    assertEquals(1L, retrieved.getTotalElements());
    assertEquals(client.getUuid(), retrieved.getContent().get(0).getUuid());
    assertNotNull(exampleValue.getProbe().getDocument());
    assertEquals("0", exampleValue.getProbe().getDocument());
    assertNotNull(exampleValue.getProbe().getName());
    assertEquals("T", exampleValue.getProbe().getName());
    assertFalse(exampleValue.getProbe().getDeleted());

  }

  @Test
  @DisplayName("should update client")
  void shouldUpdate() {
    final var client = Client.builder()
        .uuid(UUID.randomUUID())
        .build();

    when(this.repository.save(any(Client.class))).thenReturn(client);

    final var retrieved = this.business.update(client);

    verify(this.repository, times(1)).save(any(Client.class));

    assertNotNull(retrieved);
    assertEquals(client.getUuid(), retrieved.getUuid());
  }

  @Test
  @DisplayName("should delete exists client")
  void shouldDeleteExistsClient() {

    when(this.repository.findById(any(UUID.class)))
        .thenReturn(Optional.of(mock(Client.class)));
    when(this.repository.save(any(Client.class)))
        .thenReturn(mock(Client.class));

    this.business.delete(UUID.randomUUID());

    verify(this.repository, times(1)).findById(any(UUID.class));
    verify(this.repository, times(1)).save(any(Client.class));

  }

  @Test
  @DisplayName("should delete not found client")
  void shouldDeleteNotFoundClient() {

    when(this.repository.findById(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> this.business.delete(UUID.randomUUID()));

    verify(this.repository, times(1)).findById(any(UUID.class));
    verify(this.repository, times(0)).save(any(Client.class));

  }
}