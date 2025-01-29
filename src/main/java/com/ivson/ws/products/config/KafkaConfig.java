package com.ivson.ws.products.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${topic-name}")
    String topicName;

    private Map<String, String> configs = Map.of(
        // 1 replica precisa estar sincronizad com sucesso antes de confirmar a escrita
        // quanto mais acima de 1 mais lenta fica, mas maior segurnacao de backup
        "min.insync.replicas", "1"
    );

    @Bean   // poe este metodo no contexto do spring
    public NewTopic createTopic() {
        return TopicBuilder.name(topicName)
                // ate 3 microservicos podem consumir este topic
                .partitions(3)
                // 3 (1) replicas para garantir a disponibilidade, 1 copia em cada um dos 3 brokers
                .replicas(1)
                .configs(configs)
                .build();
    }

}
