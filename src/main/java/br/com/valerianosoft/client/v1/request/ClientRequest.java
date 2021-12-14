package br.com.valerianosoft.client.v1.request;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class ClientRequest implements Serializable {
  private static final long serialVersionUID = 4155801269953017148L;

  @NotNull
  @Size(min = 2)
  private String name;

  @NotNull
  @Min(1)
  @Max(999)
  private Integer age;

  @NotNull
  @Size(min = 11, max = 11)
  @Pattern(regexp="^(0|[1-9][0-9]*)$")
  private String document;

  @NotNull
  @Size(min = 10, max = 11)
  @Pattern(regexp="^(0|[1-9][0-9]*)$")
  private String phone;

  @Email
  private String email;

}
