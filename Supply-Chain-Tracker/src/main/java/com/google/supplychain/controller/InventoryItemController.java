package com.google.supplychain.controller;

import com.google.supplychain.model.InventoryItem;
import com.google.supplychain.service.InventoryItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @PostMapping
    public ResponseEntity<InventoryItem> createInventoryItem(
            @Valid @RequestBody InventoryItem item,
            @RequestParam(required = false) Long vendorId,
            @RequestParam(required = false) Long warehouseId) {
        InventoryItem createdItem = inventoryItemService.createInventoryItem(item, vendorId, warehouseId);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Long id) {
        InventoryItem item = inventoryItemService.getInventoryItemById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<InventoryItem> getInventoryItemBySku(@PathVariable String sku) {
        InventoryItem item = inventoryItemService.getInventoryItemBySku(sku);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> items = inventoryItemService.getAllInventoryItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<InventoryItem>> getItemsByWarehouse(@PathVariable Long warehouseId) {
        List<InventoryItem> items = inventoryItemService.getItemsByWarehouse(warehouseId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<InventoryItem>> getItemsByVendor(@PathVariable Long vendorId) {
        List<InventoryItem> items = inventoryItemService.getItemsByVendor(vendorId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<InventoryItem>> getItemsByCategory(@PathVariable InventoryItem.ItemCategory category) {
        List<InventoryItem> items = inventoryItemService.getItemsByCategory(category);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryItem>> getLowStockItems() {
        List<InventoryItem> items = inventoryItemService.getLowStockItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<InventoryItem>> getOutOfStockItems() {
        List<InventoryItem> items = inventoryItemService.getOutOfStockItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search")
    public ResponseEntity<List<InventoryItem>> searchItems(@RequestParam String name) {
        List<InventoryItem> items = inventoryItemService.searchItemsByName(name);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateInventoryItem(
            @PathVariable Long id,
            @Valid @RequestBody InventoryItem item) {
        InventoryItem updatedItem = inventoryItemService.updateInventoryItem(id, item);
        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<InventoryItem> updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        InventoryItem updatedItem = inventoryItemService.updateQuantity(id, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/{id}/add-stock")
    public ResponseEntity<InventoryItem> addStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        InventoryItem updatedItem = inventoryItemService.addStock(id, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/{id}/remove-stock")
    public ResponseEntity<InventoryItem> removeStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        InventoryItem updatedItem = inventoryItemService.removeStock(id, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryItemService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/warehouse/{warehouseId}/value")
    public ResponseEntity<Double> getTotalValueByWarehouse(@PathVariable Long warehouseId) {
        Double value = inventoryItemService.getTotalInventoryValueByWarehouse(warehouseId);
        return ResponseEntity.ok(value);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getInventoryItemCount() {
        long count = inventoryItemService.getInventoryItemCount();
        return ResponseEntity.ok(count);
    }
}