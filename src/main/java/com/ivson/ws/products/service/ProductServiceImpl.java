package com.ivson.ws.products.service;

import com.ivson.ws.products.event.ProductCreatedEvent;
import com.ivson.ws.products.model.CreateProductRestModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${topic-name}")
    String topicName;

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
        kafkaTemplate.send(topicName, productId, productCreatedEvent);

        return productId;
    }

}