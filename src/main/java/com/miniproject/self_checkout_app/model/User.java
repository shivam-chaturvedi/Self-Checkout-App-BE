package com.miniproject.self_checkout_app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends CreatedAtUpdatedAt{
	@Id
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role="USER"; //default role is USER

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // Defines the One-to-Many relationship
    @JsonManagedReference
    private List<UserTransaction> transactions;
}
