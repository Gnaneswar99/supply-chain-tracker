package com.google.supplychain.dto;

import com.google.supplychain.model.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemDTO {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private String category;
    private Integer quantity;
    private Integer minimumStockLevel;
    private BigDecimal unitPrice;
    private BigDecimal totalValue;
    private Boolean isLowStock;
    private String status;

    // Vendor info (flattened)
    private Long vendorId;
    private String vendorName;

    // Warehouse info (flattened)
    private Long warehouseId;
    private String warehouseName;
    private String warehouseCity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convert Entity to DTO
    public static InventoryItemDTO fromEntity(InventoryItem item) {
        InventoryItemDTOBuilder builder = InventoryItemDTO.builder()
                .id(item.getId())
                .sku(item.getSku())
                .name(item.getName())
                .description(item.getDescription())
                .category(item.getCategory() != null ? item.getCategory().name() : null)
                .quantity(item.getQuantity())
                .minimumStockLevel(item.getMinimumStockLevel())
                .unitPrice(item.getUnitPrice())
                .totalValue(item.getTotalValue())
                .isLowStock(item.isLowStock())
                .status(item.getStatus() != null ? item.getStatus().name() : null)
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt());

        // Flatten vendor info
        if (item.getVendor() != null) {
            builder.vendorId(item.getVendor().getId())
                    .vendorName(item.getVendor().getName());
        }

        // Flatten warehouse info
        if (item.getWarehouse() != null) {
            builder.warehouseId(item.getWarehouse().getId())
                    .warehouseName(item.getWarehouse().getName())
                    .warehouseCity(item.getWarehouse().getCity());
        }

        return builder.build();
    }

    // Convert DTO to Entity
    public InventoryItem toEntity() {
        InventoryItem item = new InventoryItem();
        item.setId(this.id);
        item.setSku(this.sku);
        item.setName(this.name);
        item.setDescription(this.description);
        if (this.category != null) {
            item.setCategory(InventoryItem.ItemCategory.valueOf(this.category));
        }
        item.setQuantity(this.quantity);
        item.setMinimumStockLevel(this.minimumStockLevel);
        item.setUnitPrice(this.unitPrice);
        if (this.status != null) {
            item.setStatus(InventoryItem.ItemStatus.valueOf(this.status));
        }
        return item;
    }
}