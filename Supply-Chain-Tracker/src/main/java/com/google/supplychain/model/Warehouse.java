package com.google.supplychain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Warehouse name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "current_utilization")
    private Integer currentUtilization = 0;

    @Enumerated(EnumType.STRING)
    private WarehouseStatus status = WarehouseStatus.OPERATIONAL;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Integer getAvailableCapacity() {
        return totalCapacity - currentUtilization;
    }

    public enum WarehouseStatus {
        OPERATIONAL,
        MAINTENANCE,
        CLOSED
    }
}