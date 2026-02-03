package com.google.supplychain.repository;

import com.google.supplychain.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    List<Warehouse> findByStatus(Warehouse.WarehouseStatus status);

    List<Warehouse> findByCity(String city);

    List<Warehouse> findByCountry(String country);

    List<Warehouse> findByNameContainingIgnoreCase(String name);

    @Query("SELECT w FROM Warehouse w WHERE w.status = 'OPERATIONAL' ORDER BY w.name")
    List<Warehouse> findAllOperationalWarehouses();

    @Query("SELECT w FROM Warehouse w WHERE (w.totalCapacity - w.currentUtilization) > :requiredSpace")
    List<Warehouse> findWarehousesWithAvailableSpace(Integer requiredSpace);

    @Query("SELECT w FROM Warehouse w WHERE w.currentUtilization >= (w.totalCapacity * 0.9)")
    List<Warehouse> findNearlyFullWarehouses();
}