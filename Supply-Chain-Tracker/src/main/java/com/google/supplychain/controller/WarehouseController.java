package com.google.supplychain.controller;

import com.google.supplychain.model.Warehouse;
import com.google.supplychain.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@Valid @RequestBody Warehouse warehouse) {
        Warehouse createdWarehouse = warehouseService.createWarehouse(warehouse);
        return new ResponseEntity<>(createdWarehouse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(warehouse);
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/operational")
    public ResponseEntity<List<Warehouse>> getOperationalWarehouses() {
        List<Warehouse> warehouses = warehouseService.getOperationalWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Warehouse>> getWarehousesByCity(@PathVariable String city) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByCity(city);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/available-space")
    public ResponseEntity<List<Warehouse>> getWarehousesWithAvailableSpace(@RequestParam Integer requiredSpace) {
        List<Warehouse> warehouses = warehouseService.getWarehousesWithAvailableSpace(requiredSpace);
        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/nearly-full")
    public ResponseEntity<List<Warehouse>> getNearlyFullWarehouses() {
        List<Warehouse> warehouses = warehouseService.getNearlyFullWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Long id, @Valid @RequestBody Warehouse warehouse) {
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(id, warehouse);
        return ResponseEntity.ok(updatedWarehouse);
    }

    @PatchMapping("/{id}/utilization")
    public ResponseEntity<Warehouse> updateUtilization(
            @PathVariable Long id,
            @RequestParam Integer utilization) {
        Warehouse updatedWarehouse = warehouseService.updateUtilization(id, utilization);
        return ResponseEntity.ok(updatedWarehouse);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Warehouse> updateWarehouseStatus(
            @PathVariable Long id,
            @RequestParam Warehouse.WarehouseStatus status) {
        Warehouse updatedWarehouse = warehouseService.updateWarehouseStatus(id, status);
        return ResponseEntity.ok(updatedWarehouse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getWarehouseCount() {
        long count = warehouseService.getWarehouseCount();
        return ResponseEntity.ok(count);
    }
}