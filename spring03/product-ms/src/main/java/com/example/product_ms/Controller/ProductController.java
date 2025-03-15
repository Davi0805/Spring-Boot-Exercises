package com.example.product_ms.Controller;

import com.example.product_ms.Models.ProductModel;
import com.example.product_ms.Repository.ProductRepository;
import com.example.product_ms.Services.ProductGrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductRepository cursor;
    private final ProductGrpcService grpc;

    @Autowired
    public ProductController(ProductRepository repository, ProductGrpcService grpcservice)
    {
        this.cursor = repository;
        this.grpc = grpcservice;
    }

    @PostMapping
    public ResponseEntity<ProductModel> criarProduto(@RequestBody ProductModel product)
    {
        try {
            ProductModel produto = cursor.save(product);
            return ResponseEntity.ok(produto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }



}
