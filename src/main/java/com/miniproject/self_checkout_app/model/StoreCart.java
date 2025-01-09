package com.miniproject.self_checkout_app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoreCart extends CreatedAtUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isActive = false; // Indicates if the cart is currently active

    // Current user assigned to the store cart
    @OneToOne
    @JoinColumn(nullable = true)
    @JsonBackReference("store-cart-user")
    private User currentUser;

    // Historical user carts related to this store cart
    @OneToMany(mappedBy = "storeCart", cascade = CascadeType.ALL)
    @JsonManagedReference("user-store-cart")
    private List<UserCart> userCarts;
}
