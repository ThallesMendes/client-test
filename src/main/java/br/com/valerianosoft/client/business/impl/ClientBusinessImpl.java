package br.com.valerianosoft.client.business.impl;

import br.com.valerianosoft.client.business.ClientBusiness;
import br.com.valerianosoft.client.common.exception.EntityNotFoundException;
import br.com.valerianosoft.client.model.Client;
import br.com.valerianosoft.client.repository.ClientRepository;
import br.com.valerianosoft.client.v1.filter.ClientFilter;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientBusinessImpl implements ClientBusiness {

  private final static String ENTITY_NAME = "Client";

  private final ClientRepository repository;

  @Override
  @Transactional
  public Client create(final Client client) {
    return this.repository.save(client);
  }

  @Override
  public Client show(final UUID uuid) {
    return this.repository.findById(uuid).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME));
  }

  @Override
  public Page<Client> findAll(final Pageable pageable, final ClientFilter filter) {

    final var client = new Client();
    client.setDeleted(filter.getDeleted());

    if (Objects.nonNull(filter.getName()) || Objects.nonNull(filter.getDocument())) {
      client.setName(filter.getName());
      client.setDocument(filter.getDocument());
    }

    final var matcher = ExampleMatcher.matchingAll()
        .withStringMatcher(StringMatcher.CONTAINING)
        .withIgnoreCase();

    final var example = Example.of(client, matcher);

    return this.repository.findAll(example, pageable);
  }

  @Override
  @Transactional
  public Client update(final Client client) {
    return this.repository.save(client);
  }

  @Override
  @Transactional
  public void delete(final UUID uuid) {
    this.repository.findById(uuid)
        .ifPresentOrElse(client -> {
                           client.preDelete();
                           this.repository.save(client);
                         },
                         () -> {
                           throw new EntityNotFoundException(ENTITY_NAME);
                         });
  }
}
