package com.miniproject.self_checkout_app.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User extends CreatedAtUpdatedAt {
    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER"; // Default role is USER

    @Column(nullable = false)
    private Boolean isOauthLoginUser = false; // Indicates if the user logged in using OAuth

    // Current store cart assigned to the user; managed by the store
    @OneToOne(mappedBy = "currentUser")
    @JoinColumn(nullable = true) // Optional, as not all users may have an active store cart
    @JsonManagedReference("store-cart-user")
    private StoreCart storeCart;

    // Virtual carts to store the user's cart history
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-cart") // Resolves cyclic references for cart relationships
    private List<UserCart> cart;

    // User's transaction history
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-transactions") // Resolves cyclic references for transactions
    private List<UserTransaction> transactions;
}
