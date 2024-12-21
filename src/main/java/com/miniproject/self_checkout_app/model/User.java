package com.miniproject.self_checkout_app.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    
    @Column(nullable = true)
    private String password;
    
    @Column(nullable = false)
    private String role = "USER"; //default role is USER
    
    @Column(nullable = false)
    private Boolean isOauthLoginUser=false; 
    
    @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    private List<CartItem> cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
    private List<UserTransaction> transactions;
}
