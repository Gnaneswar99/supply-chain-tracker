package com.google.supplychain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {

    // Summary counts
    private Long totalVendors;
    private Long activeVendors;
    private Long totalWarehouses;
    private Long operationalWarehouses;
    private Long totalInventoryItems;
    private Long lowStockItems;
    private Long outOfStockItems;
    private Long totalShipments;
    private Long activeShipments;
    private Long delayedShipments;
    private Long deliveredShipments;

    // Alerts
    private List<InventoryItemDTO> lowStockAlerts;
    private List<ShipmentDTO> delayedShipmentAlerts;
    private List<WarehouseDTO> nearlyFullWarehouses;

    // Recent activity
    private List<ShipmentDTO> recentShipments;
    private List<InventoryItemDTO> recentInventoryItems;
}
