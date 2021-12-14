package br.com.valerianosoft.client.common.mapper;

import br.com.valerianosoft.client.common.model.BaseEntity;
import br.com.valerianosoft.client.common.response.BaseEntityResponse;

public abstract class BaseEntityMapper {

  protected static void parseCommonInfo(final BaseEntity entity, final BaseEntityResponse response) {
    response.setDeleted(entity.getDeleted());
    response.setCreatedAt(entity.getCreatedAt());
    response.setUpdatedAt(entity.getUpdatedAt());
    response.setDeletedAt(entity.getDeletedAt());
  }

  protected static void parseCommonInfo(final BaseEntity newEntity, final BaseEntity oldEntity) {
    newEntity.setCreatedAt(oldEntity.getCreatedAt());
    newEntity.setUpdatedAt(oldEntity.getUpdatedAt());
    newEntity.setDeletedAt(oldEntity.getDeletedAt());
    newEntity.setDeleted(oldEntity.getDeleted());
  }

}
