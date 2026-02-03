package com.google.supplychain.repository;

import com.google.supplychain.model.InventoryItem;
import com.google.supplychain.model.Vendor;
import com.google.supplychain.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    Optional<InventoryItem> findBySku(String sku);

    List<InventoryItem> findByVendor(Vendor vendor);

    List<InventoryItem> findByWarehouse(Warehouse warehouse);

    List<InventoryItem> findByCategory(InventoryItem.ItemCategory category);

    List<InventoryItem> findByStatus(InventoryItem.ItemStatus status);

    List<InventoryItem> findByNameContainingIgnoreCase(String name);

    @Query("SELECT i FROM InventoryItem i WHERE i.quantity <= i.minimumStockLevel")
    List<InventoryItem> findLowStockItems();

    @Query("SELECT i FROM InventoryItem i WHERE i.quantity = 0")
    List<InventoryItem> findOutOfStockItems();

    @Query("SELECT i FROM InventoryItem i WHERE i.warehouse.id = :warehouseId")
    List<InventoryItem> findByWarehouseId(@Param("warehouseId") Long warehouseId);

    @Query("SELECT i FROM InventoryItem i WHERE i.vendor.id = :vendorId")
    List<InventoryItem> findByVendorId(@Param("vendorId") Long vendorId);

    @Query("SELECT SUM(i.quantity * i.unitPrice) FROM InventoryItem i WHERE i.warehouse.id = :warehouseId")
    Double calculateTotalValueByWarehouse(@Param("warehouseId") Long warehouseId);

    boolean existsBySku(String sku);
}