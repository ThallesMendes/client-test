package br.com.valerianosoft.client.common.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError implements Serializable {
  private static final long serialVersionUID = -6342550881832686686L;

  private String message;
}
