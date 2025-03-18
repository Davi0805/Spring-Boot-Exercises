package com.example.productservice.Service;

import com.example.productservice.DTO.FrontEndProductDTO;
import com.example.productservice.Models.Product;
import com.example.productservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public List<FrontEndProductDTO> getAllProducts()
    {
        List<FrontEndProductDTO> result = new ArrayList<>();
        for(Product temp : productRepository.findAll())
        {
            result.add(new FrontEndProductDTO(temp));
        }

        return result;
    }
}
