package com.miniproject.self_checkout_app.model;


import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
public class UserTransaction extends CreatedAtUpdatedAt{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	@JsonBackReference
	private User user;
	
	private String currency="INR";
	
	@Setter(AccessLevel.NONE)
	private String receipt;
	
	private Double amount;
	
	private String status="Pending";
	
	
	@PrePersist
	protected void onTransactionCreate() {
		this.receipt="REC-"+UUID.randomUUID().toString();
	}
	
	
}
