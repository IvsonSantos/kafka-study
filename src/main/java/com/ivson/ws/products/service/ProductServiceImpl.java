package com.ivson.ws.products.service;

import com.ivson.ws.core.ProductCreatedEvent;
import com.ivson.ws.products.model.CreateProductRestModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${topic-name}")
    String topicName;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception {

        String productId = UUID.randomUUID().toString();

        // TODO: save product to the database

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
            productId,
            productRestModel.getTitle(),
            productRestModel.getPrice(),
            productRestModel.getQuantity()
        );

        /*
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

         */

        LOGGER.info("*** Before publishing a ProductCreatedEvent");

        ProducerRecord<String, ProductCreatedEvent> producerRecord = new ProducerRecord<>(
        "product-created-events-topic",
            productId,
            productCreatedEvent
        );
        producerRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send(producerRecord).get();

        LOGGER.info("*** Partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("*** Topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("*** Offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("*** Returning productId: {}", productId);

        return productId;
    }

}