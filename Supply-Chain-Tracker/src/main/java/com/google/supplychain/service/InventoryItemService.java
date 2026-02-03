package com.google.supplychain.service;

import com.google.supplychain.model.InventoryItem;
import com.google.supplychain.model.Vendor;
import com.google.supplychain.model.Warehouse;
import com.google.supplychain.repository.InventoryItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;

    public InventoryItem createInventoryItem(InventoryItem item, Long vendorId, Long warehouseId) {
        if (inventoryItemRepository.existsBySku(item.getSku())) {
            throw new IllegalArgumentException("Item with this SKU already exists");
        }

        if (vendorId != null) {
            Vendor vendor = vendorService.getVendorById(vendorId);
            item.setVendor(vendor);
        }

        if (warehouseId != null) {
            Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
            item.setWarehouse(warehouse);
        }

        return inventoryItemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public InventoryItem getInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public InventoryItem getInventoryItemBySku(String sku) {
        return inventoryItemRepository.findBySku(sku)
                .orElseThrow(() -> new EntityNotFoundException("Inventory item not found with SKU: " + sku));
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getItemsByWarehouse(Long warehouseId) {
        return inventoryItemRepository.findByWarehouseId(warehouseId);
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getItemsByVendor(Long vendorId) {
        return inventoryItemRepository.findByVendorId(vendorId);
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getItemsByCategory(InventoryItem.ItemCategory category) {
        return inventoryItemRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getLowStockItems() {
        return inventoryItemRepository.findLowStockItems();
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> getOutOfStockItems() {
        return inventoryItemRepository.findOutOfStockItems();
    }

    @Transactional(readOnly = true)
    public List<InventoryItem> searchItemsByName(String name) {
        return inventoryItemRepository.findByNameContainingIgnoreCase(name);
    }

    public InventoryItem updateInventoryItem(Long id, InventoryItem updatedItem) {
        InventoryItem existingItem = getInventoryItemById(id);

        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setQuantity(updatedItem.getQuantity());
        existingItem.setMinimumStockLevel(updatedItem.getMinimumStockLevel());
        existingItem.setUnitPrice(updatedItem.getUnitPrice());
        existingItem.setStatus(updatedItem.getStatus());

        return inventoryItemRepository.save(existingItem);
    }

    public InventoryItem updateQuantity(Long id, Integer quantity) {
        InventoryItem item = getInventoryItemById(id);
        item.setQuantity(quantity);

        if (quantity == 0) {
            item.setStatus(InventoryItem.ItemStatus.OUT_OF_STOCK);
        } else if (item.isLowStock()) {
            item.setStatus(InventoryItem.ItemStatus.LOW_STOCK);
        } else {
            item.setStatus(InventoryItem.ItemStatus.IN_STOCK);
        }

        return inventoryItemRepository.save(item);
    }

    public InventoryItem addStock(Long id, Integer quantityToAdd) {
        InventoryItem item = getInventoryItemById(id);
        int newQuantity = item.getQuantity() + quantityToAdd;
        return updateQuantity(id, newQuantity);
    }

    public InventoryItem removeStock(Long id, Integer quantityToRemove) {
        InventoryItem item = getInventoryItemById(id);
        int newQuantity = item.getQuantity() - quantityToRemove;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Cannot remove more stock than available");
        }

        return updateQuantity(id, newQuantity);
    }

    public void deleteInventoryItem(Long id) {
        if (!inventoryItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Inventory item not found with id: " + id);
        }
        inventoryItemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Double getTotalInventoryValueByWarehouse(Long warehouseId) {
        Double value = inventoryItemRepository.calculateTotalValueByWarehouse(warehouseId);
        return value != null ? value : 0.0;
    }

    @Transactional(readOnly = true)
    public long getInventoryItemCount() {
        return inventoryItemRepository.count();
    }
}