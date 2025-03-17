package com.example.order.DTO;

import com.example.order.Models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    @JsonProperty("products")
    private List<Product> products;
}
