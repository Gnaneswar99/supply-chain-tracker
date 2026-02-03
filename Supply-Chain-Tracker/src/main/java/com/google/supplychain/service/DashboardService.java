package com.google.supplychain.service;

import com.google.supplychain.dto.DashboardDTO;
import com.google.supplychain.dto.InventoryItemDTO;
import com.google.supplychain.dto.ShipmentDTO;
import com.google.supplychain.dto.WarehouseDTO;
import com.google.supplychain.model.Shipment;
import com.google.supplychain.model.Vendor;
import com.google.supplychain.repository.InventoryItemRepository;
import com.google.supplychain.repository.ShipmentRepository;
import com.google.supplychain.repository.VendorRepository;
import com.google.supplychain.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final VendorRepository vendorRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ShipmentRepository shipmentRepository;

    public DashboardDTO getDashboardSummary() {
        return DashboardDTO.builder()
                // Vendor stats
                .totalVendors(vendorRepository.count())
                .activeVendors((long) vendorRepository.findByStatus(Vendor.VendorStatus.ACTIVE).size())

                // Warehouse stats
                .totalWarehouses(warehouseRepository.count())
                .operationalWarehouses((long) warehouseRepository.findAllOperationalWarehouses().size())

                // Inventory stats
                .totalInventoryItems(inventoryItemRepository.count())
                .lowStockItems((long) inventoryItemRepository.findLowStockItems().size())
                .outOfStockItems((long) inventoryItemRepository.findOutOfStockItems().size())

                // Shipment stats
                .totalShipments(shipmentRepository.count())
                .activeShipments((long) shipmentRepository.findActiveShipments().size())
                .delayedShipments((long) shipmentRepository.findDelayedShipments(LocalDateTime.now()).size())
                .deliveredShipments(shipmentRepository.countByStatus(Shipment.ShipmentStatus.DELIVERED))

                // Alerts
                .lowStockAlerts(getLowStockAlerts())
                .delayedShipmentAlerts(getDelayedShipmentAlerts())
                .nearlyFullWarehouses(getNearlyFullWarehouses())

                // Recent activity
                .recentShipments(getRecentShipments())
                .recentInventoryItems(getRecentInventoryItems())

                .build();
    }

    private List<InventoryItemDTO> getLowStockAlerts() {
        return inventoryItemRepository.findLowStockItems()
                .stream()
                .map(InventoryItemDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private List<ShipmentDTO> getDelayedShipmentAlerts() {
        return shipmentRepository.findDelayedShipments(LocalDateTime.now())
                .stream()
                .map(ShipmentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private List<WarehouseDTO> getNearlyFullWarehouses() {
        return warehouseRepository.findNearlyFullWarehouses()
                .stream()
                .map(WarehouseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private List<ShipmentDTO> getRecentShipments() {
        return shipmentRepository.findAll()
                .stream()
                .sorted((s1, s2) -> s2.getCreatedAt().compareTo(s1.getCreatedAt()))
                .limit(5)
                .map(ShipmentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private List<InventoryItemDTO> getRecentInventoryItems() {
        return inventoryItemRepository.findAll()
                .stream()
                .sorted((i1, i2) -> i2.getCreatedAt().compareTo(i1.getCreatedAt()))
                .limit(5)
                .map(InventoryItemDTO::fromEntity)
                .collect(Collectors.toList());
    }
}