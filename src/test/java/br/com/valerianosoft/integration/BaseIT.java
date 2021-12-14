package br.com.valerianosoft.integration;

import br.com.valerianosoft.client.ClientApplication;
import org.junit.ClassRule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ClientApplication.class)
@ContextConfiguration(initializers = BaseIT.DockerPostgreDataSourceInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class BaseIT {

  public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:latest");

  static {
    postgreDBContainer.start();
  }

  public static class DockerPostgreDataSourceInitializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + postgreDBContainer.getJdbcUrl(),
          "spring.datasource.username=" + postgreDBContainer.getUsername(),
          "spring.datasource.password=" + postgreDBContainer.getPassword(),
          "spring.flyway.url=" + postgreDBContainer.getJdbcUrl(),
          "spring.flyway.user=" + postgreDBContainer.getUsername(),
          "spring.flyway.password=" + postgreDBContainer.getPassword());
    }
  }

}
