package com.ivson.ws.products.controller;

import com.ivson.ws.products.model.CreateProductRestModel;
import com.ivson.ws.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRestModel product) {

        String productId = productService.createProduct(product);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Product created successfully :" + productId);
    }
}
