package br.com.valerianosoft.client.v1.mapper;

import br.com.valerianosoft.client.common.mapper.BaseEntityMapper;
import br.com.valerianosoft.client.model.Client;
import br.com.valerianosoft.client.v1.request.ClientContactRequest;
import br.com.valerianosoft.client.v1.request.ClientRequest;
import br.com.valerianosoft.client.v1.response.ClientContactResponse;
import br.com.valerianosoft.client.v1.response.ClientResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientMapper extends BaseEntityMapper {

  public Client toEntity(final ClientContactRequest request, final Client client) {
    client.setPhone(request.getPhone());
    client.setEmail(request.getEmail());

    return client;
  }

  public Client toEntity(final ClientRequest request, final Client entity) {
    final var client = toEntity(request);

    client.setUuid(entity.getUuid());
    parseCommonInfo(client, entity);

    return client;
  }

  public Client toEntity(final ClientRequest request) {
    return Client.builder()
        .name(request.getName())
        .age(request.getAge())
        .document(request.getDocument())
        .phone(request.getPhone())
        .email(request.getEmail())
        .build();
  }

  public ClientResponse toResponse(final Client client) {
    final var response = ClientResponse.builder()
        .uuid(client.getUuid())
        .name(client.getName())
        .age(client.getAge())
        .document(client.getDocument())
        .phone(client.getPhone())
        .email(client.getEmail())
        .build();

    parseCommonInfo(client, response);

    return response;
  }

  public ClientContactResponse toResponseContact(final Client client) {
    return ClientContactResponse.builder()
        .uuid(client.getUuid())
        .name(client.getName())
        .phone(client.getPhone())
        .email(client.getEmail())
        .build();
  }

}
