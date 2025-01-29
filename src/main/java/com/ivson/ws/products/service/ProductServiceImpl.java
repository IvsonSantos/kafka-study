package com.ivson.ws.products.service;

import com.ivson.ws.products.event.ProductCreatedEvent;
import com.ivson.ws.products.model.CreateProductRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${topic-name}")
    String topicName;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) {

        String productId = UUID.randomUUID().toString();

        // TODO: save product to the database

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
            productId,
            productRestModel.getTitle(),
            productRestModel.getPrice(),
            productRestModel.getQuantity()
        );

        // envia a mensagem para o Kafka de forma assincrona
        // topico, o id da mensagem, o json a ser enviado
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
            kafkaTemplate.send(topicName, productId, productCreatedEvent);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                LOG.info("***** Message sent successfully: {}", result.getRecordMetadata());
            } else {
                LOG.error("***** Message sent failed: {}", exception.getMessage());
            }
        });

        // AQUI QUE DEFINIMOS O SINCRONO
        // bloqueia o thread atual ate que o future seja consluido
        // future.join();

        LOG.info("*** Returning productId: {}", productId);

        return productId;
    }

}