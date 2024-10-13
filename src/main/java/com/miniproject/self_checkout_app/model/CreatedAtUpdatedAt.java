package com.miniproject.self_checkout_app.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class CreatedAtUpdatedAt {

    @Column(updatable = false, nullable = false)
    protected LocalDate createdAt;

    @Column(nullable = false)
    protected LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
