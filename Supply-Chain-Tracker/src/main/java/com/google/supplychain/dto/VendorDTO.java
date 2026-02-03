package com.google.supplychain.dto;

import com.google.supplychain.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {

    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convert Entity to DTO
    public static VendorDTO fromEntity(Vendor vendor) {
        return VendorDTO.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .contactPerson(vendor.getContactPerson())
                .email(vendor.getEmail())
                .phone(vendor.getPhone())
                .address(vendor.getAddress())
                .status(vendor.getStatus() != null ? vendor.getStatus().name() : null)
                .createdAt(vendor.getCreatedAt())
                .updatedAt(vendor.getUpdatedAt())
                .build();
    }

    // Convert DTO to Entity
    public Vendor toEntity() {
        Vendor vendor = new Vendor();
        vendor.setId(this.id);
        vendor.setName(this.name);
        vendor.setContactPerson(this.contactPerson);
        vendor.setEmail(this.email);
        vendor.setPhone(this.phone);
        vendor.setAddress(this.address);
        if (this.status != null) {
            vendor.setStatus(Vendor.VendorStatus.valueOf(this.status));
        }
        return vendor;
    }
}