package br.com.valerianosoft.client.common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

  private static final long serialVersionUID = -794455227197892354L;

  private String server;
  private String version;
  private List<? extends Serializable> data;
  private Integer page;
  private Integer totalPages;
  private Long totalRows;

}
