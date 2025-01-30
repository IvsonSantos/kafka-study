package com.ivson.ws.products.service;

import com.ivson.ws.products.model.CreateProductRestModel;

public interface ProductService {

    String createProduct(CreateProductRestModel productRestModel) throws Exception;
}
