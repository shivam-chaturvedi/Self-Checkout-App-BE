package com.miniproject.self_checkout_app.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class UserCart extends CreatedAtUpdatedAt{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private boolean isActive=true;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	@JsonBackReference("user-store-cart")
	private StoreCart storeCart;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy ="userCart",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference("cart-items")
	private List<CartItem> items;  
	
	@OneToOne(mappedBy = "userCart")
    @JsonBackReference("cart-transaction")
	private UserTransaction transaction;
	
	@Override
	public String toString() {
		return "{Id:"+ id+"}";
	}
	
}
