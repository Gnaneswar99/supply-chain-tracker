package com.google.supplychain.dto;

import com.google.supplychain.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {

    private Long id;
    private String name;
    private String location;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Integer totalCapacity;
    private Integer currentUtilization;
    private Integer availableCapacity;
    private Double utilizationPercentage;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convert Entity to DTO
    public static WarehouseDTO fromEntity(Warehouse warehouse) {
        Integer available = warehouse.getTotalCapacity() != null && warehouse.getCurrentUtilization() != null
                ? warehouse.getTotalCapacity() - warehouse.getCurrentUtilization()
                : null;

        Double utilizationPct = warehouse.getTotalCapacity() != null && warehouse.getCurrentUtilization() != null && warehouse.getTotalCapacity() > 0
                ? (warehouse.getCurrentUtilization() * 100.0) / warehouse.getTotalCapacity()
                : null;

        return WarehouseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .country(warehouse.getCountry())
                .zipCode(warehouse.getZipCode())
                .totalCapacity(warehouse.getTotalCapacity())
                .currentUtilization(warehouse.getCurrentUtilization())
                .availableCapacity(available)
                .utilizationPercentage(utilizationPct)
                .status(warehouse.getStatus() != null ? warehouse.getStatus().name() : null)
                .createdAt(warehouse.getCreatedAt())
                .updatedAt(warehouse.getUpdatedAt())
                .build();
    }

    // Convert DTO to Entity
    public Warehouse toEntity() {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(this.id);
        warehouse.setName(this.name);
        warehouse.setLocation(this.location);
        warehouse.setCity(this.city);
        warehouse.setState(this.state);
        warehouse.setCountry(this.country);
        warehouse.setZipCode(this.zipCode);
        warehouse.setTotalCapacity(this.totalCapacity);
        warehouse.setCurrentUtilization(this.currentUtilization);
        if (this.status != null) {
            warehouse.setStatus(Warehouse.WarehouseStatus.valueOf(this.status));
        }
        return warehouse;
    }
}