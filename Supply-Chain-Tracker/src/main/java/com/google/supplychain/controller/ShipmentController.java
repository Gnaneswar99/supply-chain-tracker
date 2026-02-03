package com.google.supplychain.controller;

import com.google.supplychain.model.Shipment;
import com.google.supplychain.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<Shipment> createShipment(
            @Valid @RequestBody Shipment shipment,
            @RequestParam Long itemId,
            @RequestParam(required = false) Long sourceWarehouseId,
            @RequestParam(required = false) Long destinationWarehouseId,
            @RequestParam(required = false) Long vendorId) {
        Shipment createdShipment = shipmentService.createShipment(
                shipment, itemId, sourceWarehouseId, destinationWarehouseId, vendorId);
        return new ResponseEntity<>(createdShipment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        Shipment shipment = shipmentService.getShipmentById(id);
        return ResponseEntity.ok(shipment);
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<Shipment> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        Shipment shipment = shipmentService.getShipmentByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(shipment);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Shipment>> getShipmentsByStatus(@PathVariable Shipment.ShipmentStatus status) {
        List<Shipment> shipments = shipmentService.getShipmentsByStatus(status);
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Shipment>> getActiveShipments() {
        List<Shipment> shipments = shipmentService.getActiveShipments();
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/delayed")
    public ResponseEntity<List<Shipment>> getDelayedShipments() {
        List<Shipment> shipments = shipmentService.getDelayedShipments();
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/incoming/{warehouseId}")
    public ResponseEntity<List<Shipment>> getIncomingShipments(@PathVariable Long warehouseId) {
        List<Shipment> shipments = shipmentService.getIncomingShipments(warehouseId);
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/outgoing/{warehouseId}")
    public ResponseEntity<List<Shipment>> getOutgoingShipments(@PathVariable Long warehouseId) {
        List<Shipment> shipments = shipmentService.getOutgoingShipments(warehouseId);
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Shipment>> getShipmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Shipment> shipments = shipmentService.getShipmentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(shipments);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(
            @PathVariable Long id,
            @RequestParam Shipment.ShipmentStatus status) {
        Shipment updatedShipment = shipmentService.updateShipmentStatus(id, status);
        return ResponseEntity.ok(updatedShipment);
    }

    @PatchMapping("/{id}/ship")
    public ResponseEntity<Shipment> markAsShipped(
            @PathVariable Long id,
            @RequestParam String carrier) {
        Shipment updatedShipment = shipmentService.markAsShipped(id, carrier);
        return ResponseEntity.ok(updatedShipment);
    }

    @PatchMapping("/{id}/expected-delivery")
    public ResponseEntity<Shipment> updateExpectedDeliveryDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expectedDate) {
        Shipment updatedShipment = shipmentService.updateExpectedDeliveryDate(id, expectedDate);
        return ResponseEntity.ok(updatedShipment);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Shipment> cancelShipment(@PathVariable Long id) {
        Shipment cancelledShipment = shipmentService.cancelShipment(id);
        return ResponseEntity.ok(cancelledShipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getShipmentCountByStatus(@PathVariable Shipment.ShipmentStatus status) {
        Long count = shipmentService.getShipmentCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalShipmentCount() {
        long count = shipmentService.getTotalShipmentCount();
        return ResponseEntity.ok(count);
    }
}
