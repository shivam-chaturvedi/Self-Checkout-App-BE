package com.miniproject.self_checkout_app.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserTransaction extends CreatedAtUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonBackReference("user-transactions") // Resolves cyclic references for serialization
    private User user;
    
    @OneToOne
    @JsonBackReference("cart-transaction") // Resolves cyclic references for serialization
    private UserCart userCart;

    private String currency = "INR";

    @Setter(AccessLevel.NONE)
    private String receipt;

    @Column(nullable = false)
    private Double amount=0.0; 

    private String status = "Pending";
    
    @PrePersist
    protected void onTransactionCreate() {
        this.receipt = "REC-" + UUID.randomUUID().toString();
        for(CartItem item:this.userCart.getItems()) {
        	this.amount+=item.getAmount();
        }
    }
    
    @PreUpdate
    protected void onTransactionUpdate() {
    	for(CartItem item:this.userCart.getItems()) {
        	this.amount+=item.getAmount();
        }
    }
    
    @Override
    public String toString() {
    	return "{"+status+"}";
    }
}
