package com.example.product_ms.Repository;

import com.example.product_ms.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;



public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

}
