package com.google.supplychain.service;

import com.google.supplychain.model.InventoryItem;
import com.google.supplychain.model.Shipment;
import com.google.supplychain.model.Vendor;
import com.google.supplychain.model.Warehouse;
import com.google.supplychain.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final InventoryItemService inventoryItemService;
    private final WarehouseService warehouseService;
    private final VendorService vendorService;

    public Shipment createShipment(Shipment shipment, Long itemId, Long sourceWarehouseId,
                                   Long destinationWarehouseId, Long vendorId) {
        InventoryItem item = inventoryItemService.getInventoryItemById(itemId);
        shipment.setItem(item);

        if (sourceWarehouseId != null) {
            Warehouse source = warehouseService.getWarehouseById(sourceWarehouseId);
            shipment.setSourceWarehouse(source);
        }

        if (destinationWarehouseId != null) {
            Warehouse destination = warehouseService.getWarehouseById(destinationWarehouseId);
            shipment.setDestinationWarehouse(destination);
        }

        if (vendorId != null) {
            Vendor vendor = vendorService.getVendorById(vendorId);
            shipment.setVendor(vendor);
        }

        return shipmentRepository.save(shipment);
    }

    @Transactional(readOnly = true)
    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Shipment getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with tracking number: " + trackingNumber));
    }

    @Transactional(readOnly = true)
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Shipment> getShipmentsByStatus(Shipment.ShipmentStatus status) {
        return shipmentRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Shipment> getActiveShipments() {
        return shipmentRepository.findActiveShipments();
    }

    @Transactional(readOnly = true)
    public List<Shipment> getDelayedShipments() {
        return shipmentRepository.findDelayedShipments(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<Shipment> getIncomingShipments(Long warehouseId) {
        return shipmentRepository.findIncomingShipments(warehouseId);
    }

    @Transactional(readOnly = true)
    public List<Shipment> getOutgoingShipments(Long warehouseId) {
        return shipmentRepository.findOutgoingShipments(warehouseId);
    }

    @Transactional(readOnly = true)
    public List<Shipment> getShipmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return shipmentRepository.findShipmentsBetweenDates(startDate, endDate);
    }

    public Shipment updateShipmentStatus(Long id, Shipment.ShipmentStatus status) {
        Shipment shipment = getShipmentById(id);
        shipment.setStatus(status);

        if (status == Shipment.ShipmentStatus.DELIVERED) {
            shipment.setActualDeliveryDate(LocalDateTime.now());
            processDelivery(shipment);
        }

        return shipmentRepository.save(shipment);
    }

    private void processDelivery(Shipment shipment) {
        if (shipment.getShipmentType() == Shipment.ShipmentType.INBOUND) {
            inventoryItemService.addStock(shipment.getItem().getId(), shipment.getQuantity());
        } else if (shipment.getShipmentType() == Shipment.ShipmentType.OUTBOUND) {
            inventoryItemService.removeStock(shipment.getItem().getId(), shipment.getQuantity());
        } else if (shipment.getShipmentType() == Shipment.ShipmentType.TRANSFER) {
            inventoryItemService.removeStock(shipment.getItem().getId(), shipment.getQuantity());
            inventoryItemService.addStock(shipment.getItem().getId(), shipment.getQuantity());
        }
    }

    public Shipment markAsShipped(Long id, String carrier) {
        Shipment shipment = getShipmentById(id);
        shipment.setStatus(Shipment.ShipmentStatus.IN_TRANSIT);
        shipment.setShippedDate(LocalDateTime.now());
        shipment.setCarrier(carrier);
        return shipmentRepository.save(shipment);
    }

    public Shipment updateExpectedDeliveryDate(Long id, LocalDateTime expectedDate) {
        Shipment shipment = getShipmentById(id);
        shipment.setExpectedDeliveryDate(expectedDate);
        return shipmentRepository.save(shipment);
    }

    public Shipment cancelShipment(Long id) {
        Shipment shipment = getShipmentById(id);

        if (shipment.getStatus() == Shipment.ShipmentStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel a delivered shipment");
        }

        shipment.setStatus(Shipment.ShipmentStatus.CANCELLED);
        return shipmentRepository.save(shipment);
    }

    public void deleteShipment(Long id) {
        if (!shipmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Shipment not found with id: " + id);
        }
        shipmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Long getShipmentCountByStatus(Shipment.ShipmentStatus status) {
        return shipmentRepository.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public long getTotalShipmentCount() {
        return shipmentRepository.count();
    }
}
