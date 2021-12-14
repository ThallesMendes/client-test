package br.com.valerianosoft.client.common.model;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

  @Column(name = "deleted")
  private Boolean deleted;

  @Column(name = "createdAt")
  private ZonedDateTime createdAt;

  @Column(name = "updatedAt")
  private ZonedDateTime updatedAt;

  @Column(name = "deletedAt")
  private ZonedDateTime deletedAt;

  @PrePersist
  public void prePersist() {
    if (Objects.isNull(this.getCreatedAt())) {
      this.setCreatedAt(ZonedDateTime.now());
    }
    this.setDeleted(false);
  }

  @PreUpdate
  public void preUpdate() {
    this.setUpdatedAt(ZonedDateTime.now());
  }

  public void preDelete() {
    this.setDeleted(true);
    this.setDeletedAt(ZonedDateTime.now());
  }
}
