package br.com.valerianosoft.client.model;

import br.com.valerianosoft.client.common.model.BaseEntity;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid", callSuper = false)
@ToString
@Builder
@Entity
@Table(schema = "public", name = "clients")
public class Client extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -6378809654118859506L;

  @Id
  @Column(name = "uuid", columnDefinition = "uuid")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID uuid;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private Integer age;

  @Column(name = "document")
  private String document;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;


}
