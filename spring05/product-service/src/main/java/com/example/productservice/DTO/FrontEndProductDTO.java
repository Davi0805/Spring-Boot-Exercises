package com.example.productservice.DTO;

import com.example.productservice.Models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FrontEndProductDTO {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String urlImage;

    public FrontEndProductDTO(Product copy)
    {
        this.id = copy.getId();
        this.name = copy.getName();
        this.description = copy.getDescription();
        this.price = copy.getPrice();
        this.urlImage = copy.getUrlImage();
    }
}
