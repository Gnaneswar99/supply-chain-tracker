package com.google.supplychain.service;

import com.google.supplychain.model.Warehouse;
import com.google.supplychain.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = true)
    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Warehouse> getOperationalWarehouses() {
        return warehouseRepository.findAllOperationalWarehouses();
    }

    @Transactional(readOnly = true)
    public List<Warehouse> getWarehousesByCity(String city) {
        return warehouseRepository.findByCity(city);
    }

    @Transactional(readOnly = true)
    public List<Warehouse> getWarehousesWithAvailableSpace(Integer requiredSpace) {
        return warehouseRepository.findWarehousesWithAvailableSpace(requiredSpace);
    }

    @Transactional(readOnly = true)
    public List<Warehouse> getNearlyFullWarehouses() {
        return warehouseRepository.findNearlyFullWarehouses();
    }

    public Warehouse updateWarehouse(Long id, Warehouse updatedWarehouse) {
        Warehouse existingWarehouse = getWarehouseById(id);

        existingWarehouse.setName(updatedWarehouse.getName());
        existingWarehouse.setLocation(updatedWarehouse.getLocation());
        existingWarehouse.setCity(updatedWarehouse.getCity());
        existingWarehouse.setState(updatedWarehouse.getState());
        existingWarehouse.setCountry(updatedWarehouse.getCountry());
        existingWarehouse.setZipCode(updatedWarehouse.getZipCode());
        existingWarehouse.setTotalCapacity(updatedWarehouse.getTotalCapacity());
        existingWarehouse.setStatus(updatedWarehouse.getStatus());

        return warehouseRepository.save(existingWarehouse);
    }

    public Warehouse updateUtilization(Long id, Integer newUtilization) {
        Warehouse warehouse = getWarehouseById(id);
        warehouse.setCurrentUtilization(newUtilization);
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouseStatus(Long id, Warehouse.WarehouseStatus status) {
        Warehouse warehouse = getWarehouseById(id);
        warehouse.setStatus(status);
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new EntityNotFoundException("Warehouse not found with id: " + id);
        }
        warehouseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long getWarehouseCount() {
        return warehouseRepository.count();
    }
}