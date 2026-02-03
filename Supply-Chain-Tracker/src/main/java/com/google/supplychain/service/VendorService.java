package com.google.supplychain.service;

import com.google.supplychain.model.Vendor;
import com.google.supplychain.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorService {


    private final VendorRepository vendorRepository;
    public Vendor createVendor(Vendor vendor) {
        if (vendorRepository.existsByEmail(vendor.getEmail())) {
            throw new IllegalArgumentException("Vendor with this email already exists");
        }
        return vendorRepository.save(vendor);
    }

    @Transactional(readOnly = true)
    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vendor> getActiveVendors() {
        return vendorRepository.findAllActiveVendors();
    }

    @Transactional(readOnly = true)
    public List<Vendor> searchVendorsByName(String name) {
        return vendorRepository.findByNameContainingIgnoreCase(name);
    }

    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        Vendor existingVendor = getVendorById(id);

        existingVendor.setName(updatedVendor.getName());
        existingVendor.setContactPerson(updatedVendor.getContactPerson());
        existingVendor.setEmail(updatedVendor.getEmail());
        existingVendor.setPhone(updatedVendor.getPhone());
        existingVendor.setAddress(updatedVendor.getAddress());
        existingVendor.setStatus(updatedVendor.getStatus());

        return vendorRepository.save(existingVendor);
    }

    public Vendor updateVendorStatus(Long id, Vendor.VendorStatus status) {
        Vendor vendor = getVendorById(id);
        vendor.setStatus(status);
        return vendorRepository.save(vendor);
    }

    public void deleteVendor(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new EntityNotFoundException("Vendor not found with id: " + id);
        }
        vendorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long getVendorCount() {
        return vendorRepository.count();
    }
}