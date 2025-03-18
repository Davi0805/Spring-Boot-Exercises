package com.example.productservice.Repository;

import com.example.productservice.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {


    // TODO: ANALISAR COMO ATUALIZAR LAST PIECES QUANDO CHEGAR EM UM NUMEOR ESPECIFICO
    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId AND p.quantity >= :quantity")
    int decrementStock(UUID productId, int quantity);

}
