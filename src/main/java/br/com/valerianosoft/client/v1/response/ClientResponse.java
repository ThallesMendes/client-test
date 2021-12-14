package br.com.valerianosoft.client.v1.response;

import br.com.valerianosoft.client.common.response.BaseEntityResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class ClientResponse extends BaseEntityResponse implements Serializable {
  private static final long serialVersionUID = 5362957295682615852L;

  private UUID uuid;
  private String name;
  private Integer age;
  private String document;
  private String phone;
  private String email;

}
