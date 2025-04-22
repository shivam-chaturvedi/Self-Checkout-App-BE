package com.miniproject.self_checkout_app.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product extends CreatedAtUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 500, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false, length = 500)
    private String category;
    
    @Column(nullable = false)
    private Long quantity = 0L;
    
    @Column(nullable = true)
    private String rfidTag;
    
    private boolean isAvailable = false;
    
    @PrePersist
    private void onProductCreate() {
        setAvailabilityBasedOnQuantity();
    }

    @PreUpdate
    private void onProductUpdate() {
        setAvailabilityBasedOnQuantity();
    }

    private void setAvailabilityBasedOnQuantity() {
        this.isAvailable = this.quantity > 0;
    }
}
