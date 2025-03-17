package com.example.product_ms.Controller;

import com.example.product_ms.Models.ProductModel;
import com.example.product_ms.Repository.ProductRepository;
import com.example.product_ms.Services.ProductGrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Transactional
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

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProdutos()
    {
        List<ProductModel> produtos = cursor.findAll();

        if (produtos.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(produtos);
    }



}
