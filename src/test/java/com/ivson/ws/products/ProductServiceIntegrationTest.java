package com.ivson.ws.products;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

// mock um kafka server aleatorio
@EmbeddedKafka(partitions = 3, count = 3, controlledShutdown = true)
@DirtiesContext // limpa o contexto do spring apos o teste, para que sempre comece com um objeto limpo
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // cria uma instancia desta classe para todos os metodos a testar
@ActiveProfiles("test") //application-test.properties
@SpringBootTest(properties = "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}")
public class ProductServiceIntegrationTest {



}
