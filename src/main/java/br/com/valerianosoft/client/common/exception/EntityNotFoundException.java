package br.com.valerianosoft.client.common.exception;

import static java.text.MessageFormat.format;

public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 7652546597849773781L;

  public EntityNotFoundException(final String entity) {
    super(format("Entity {0} not found", entity));
  }
}
