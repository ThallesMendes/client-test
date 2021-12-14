package br.com.valerianosoft.integration;

import java.util.Objects;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestPostgresContainer extends PostgreSQLContainer<TestPostgresContainer> {

  private static final String IMAGE_VERSION = "postgres:latest";
  private static TestPostgresContainer container;

  private TestPostgresContainer() {
    super(IMAGE_VERSION);
  }

  public static TestPostgresContainer getInstance() {
    if (Objects.isNull(container)) {
      container = new TestPostgresContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DATASOURCE_URL", container.getJdbcUrl());
    System.setProperty("DATABASE_USERNAME", container.getUsername());
    System.setProperty("DATABASE_PASSWORD", container.getPassword());
  }

}
