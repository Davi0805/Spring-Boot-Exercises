package com.example.productservice.Controller;

import com.example.productservice.DTO.FrontEndProductDTO;
import com.example.productservice.Models.Product;
import com.example.productservice.Service.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product produto) {
        try {
            productService.createProduct(produto);
            return ResponseEntity.ok("Produto criado com sucesso");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao criar produto");
        }
    }

    @GetMapping
    public ResponseEntity<List<FrontEndProductDTO>> getAllProducts()
    {
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
