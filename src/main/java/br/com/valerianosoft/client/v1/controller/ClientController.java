package br.com.valerianosoft.client.v1.controller;

import br.com.valerianosoft.client.business.ClientBusiness;
import br.com.valerianosoft.client.common.controller.BaseController;
import br.com.valerianosoft.client.common.filter.PageFilter;
import br.com.valerianosoft.client.common.response.Response;
import br.com.valerianosoft.client.v1.filter.ClientFilter;
import br.com.valerianosoft.client.v1.mapper.ClientMapper;
import br.com.valerianosoft.client.v1.request.ClientContactRequest;
import br.com.valerianosoft.client.v1.request.ClientRequest;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
@Validated
public class ClientController extends BaseController {

  private final ClientBusiness business;

  @PostMapping
  public ResponseEntity<Response> create(final @RequestBody @Valid ClientRequest request) {
    final var created = this.business.create(ClientMapper.toEntity(request));
    return this.buildResponse(HttpStatus.CREATED, ClientMapper.toResponse(created));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<Response> show(final @PathVariable("uuid") UUID uuid) {
    final var client = this.business.show(uuid);
    return this.buildResponse(HttpStatus.OK, ClientMapper.toResponse(client));
  }

  @GetMapping
  public ResponseEntity<Response> findAll(final PageFilter pageFilter, final ClientFilter filter) {
    final var clients = this.business.findAll(pageFilter.toPageable(), filter);
    clients.map(ClientMapper::toResponse);

    return this.buildResponse(HttpStatus.OK, clients);
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<Response> update(final @RequestBody @Valid ClientRequest request,
                                         final @PathVariable("uuid") UUID uuid) {

    final var client = this.business.show(uuid);
    final var clientUpdated = this.business.update(ClientMapper.toEntity(request, client));

    return this.buildResponse(HttpStatus.OK, ClientMapper.toResponse(clientUpdated));
  }

  @PatchMapping("/{uuid}/contact")
  public ResponseEntity<Response> updateContact(final @RequestBody @Valid ClientContactRequest request,
                                         final @PathVariable("uuid") UUID uuid) {

    final var client = this.business.show(uuid);
    final var clientUpdated = this.business.update(ClientMapper.toEntity(request, client));

    return this.buildResponse(HttpStatus.OK, ClientMapper.toResponseContact(clientUpdated));
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Response> delete(final @PathVariable("uuid") UUID uuid) {
    this.business.delete(uuid);
    return ResponseEntity.noContent().build();
  }
}
