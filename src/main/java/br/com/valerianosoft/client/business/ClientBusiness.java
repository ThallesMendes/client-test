package br.com.valerianosoft.client.business;

import br.com.valerianosoft.client.model.Client;
import br.com.valerianosoft.client.v1.filter.ClientFilter;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientBusiness {

  Client create(final Client client);

  Client show(final UUID uuid);

  Page<Client> findAll(final Pageable pageable, final ClientFilter filter);

  Client update(final Client client);

  void delete(final UUID uuid);

}
