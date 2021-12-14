package br.com.valerianosoft.client.v1.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientFilter {

  private String name;
  private String document;
  private Boolean deleted;

  public ClientFilter() {
    this.deleted = false;
  }
}
