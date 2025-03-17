package com.example.product_ms.Repository;

import com.example.product_ms.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    // RETORNA 0 QUANDO NAO TEM ESTOQUE SUFICIENTE
    // E NAO ATUALIZA O ESTOQUE
    @Transactional
    @Modifying
    @Query("UPDATE ProductModel p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId AND p.quantity >= :quantity")
    int decrementStock(UUID productId, int quantity);
}
