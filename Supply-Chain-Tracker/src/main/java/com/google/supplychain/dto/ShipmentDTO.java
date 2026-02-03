package com.google.supplychain.dto;

import com.google.supplychain.model.Shipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentDTO {

    private Long id;
    private String trackingNumber;
    private Integer quantity;
    private String shipmentType;
    private String status;
    private String carrier;
    private String notes;
    private Boolean isDelayed;

    // Item info (flattened)
    private Long itemId;
    private String itemName;
    private String itemSku;

    // Source warehouse info (flattened)
    private Long sourceWarehouseId;
    private String sourceWarehouseName;
    private String sourceWarehouseCity;

    // Destination warehouse info (flattened)
    private Long destinationWarehouseId;
    private String destinationWarehouseName;
    private String destinationWarehouseCity;

    // Vendor info (flattened)
    private Long vendorId;
    private String vendorName;

    // Dates
    private LocalDateTime shippedDate;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convert Entity to DTO
    public static ShipmentDTO fromEntity(Shipment shipment) {
        ShipmentDTOBuilder builder = ShipmentDTO.builder()
                .id(shipment.getId())
                .trackingNumber(shipment.getTrackingNumber())
                .quantity(shipment.getQuantity())
                .shipmentType(shipment.getShipmentType() != null ? shipment.getShipmentType().name() : null)
                .status(shipment.getStatus() != null ? shipment.getStatus().name() : null)
                .carrier(shipment.getCarrier())
                .notes(shipment.getNotes())
                .isDelayed(shipment.isDelayed())
                .shippedDate(shipment.getShippedDate())
                .expectedDeliveryDate(shipment.getExpectedDeliveryDate())
                .actualDeliveryDate(shipment.getActualDeliveryDate())
                .createdAt(shipment.getCreatedAt())
                .updatedAt(shipment.getUpdatedAt());

        // Flatten item info
        if (shipment.getItem() != null) {
            builder.itemId(shipment.getItem().getId())
                    .itemName(shipment.getItem().getName())
                    .itemSku(shipment.getItem().getSku());
        }

        // Flatten source warehouse info
        if (shipment.getSourceWarehouse() != null) {
            builder.sourceWarehouseId(shipment.getSourceWarehouse().getId())
                    .sourceWarehouseName(shipment.getSourceWarehouse().getName())
                    .sourceWarehouseCity(shipment.getSourceWarehouse().getCity());
        }

        // Flatten destination warehouse info
        if (shipment.getDestinationWarehouse() != null) {
            builder.destinationWarehouseId(shipment.getDestinationWarehouse().getId())
                    .destinationWarehouseName(shipment.getDestinationWarehouse().getName())
                    .destinationWarehouseCity(shipment.getDestinationWarehouse().getCity());
        }

        // Flatten vendor info
        if (shipment.getVendor() != null) {
            builder.vendorId(shipment.getVendor().getId())
                    .vendorName(shipment.getVendor().getName());
        }

        return builder.build();
    }

    // Convert DTO to Entity
    public Shipment toEntity() {
        Shipment shipment = new Shipment();
        shipment.setId(this.id);
        shipment.setTrackingNumber(this.trackingNumber);
        shipment.setQuantity(this.quantity);
        if (this.shipmentType != null) {
            shipment.setShipmentType(Shipment.ShipmentType.valueOf(this.shipmentType));
        }
        if (this.status != null) {
            shipment.setStatus(Shipment.ShipmentStatus.valueOf(this.status));
        }
        shipment.setCarrier(this.carrier);
        shipment.setNotes(this.notes);
        shipment.setShippedDate(this.shippedDate);
        shipment.setExpectedDeliveryDate(this.expectedDeliveryDate);
        shipment.setActualDeliveryDate(this.actualDeliveryDate);
        return shipment;
    }
}