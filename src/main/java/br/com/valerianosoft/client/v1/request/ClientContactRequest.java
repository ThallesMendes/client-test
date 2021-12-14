package br.com.valerianosoft.client.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientContactRequest implements Serializable {
  private static final long serialVersionUID = 6980731900970375697L;

  @NotNull
  @Size(min = 10, max = 11)
  @Pattern(regexp="^(0|[1-9][0-9]*)$")
  private String phone;

  @NotNull
  @Email
  private String email;

}
