package com.google.supplychain.controller;

import com.google.supplychain.model.Vendor;
import com.google.supplychain.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@Valid @RequestBody Vendor vendor) {
        Vendor createdVendor = vendorService.createVendor(vendor);
        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        Vendor vendor = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Vendor>> getActiveVendors() {
        List<Vendor> vendors = vendorService.getActiveVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vendor>> searchVendors(@RequestParam String name) {
        List<Vendor> vendors = vendorService.searchVendorsByName(name);
        return ResponseEntity.ok(vendors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @Valid @RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(id, vendor);
        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Vendor> updateVendorStatus(
            @PathVariable Long id,
            @RequestParam Vendor.VendorStatus status) {
        Vendor updatedVendor = vendorService.updateVendorStatus(id, status);
        return ResponseEntity.ok(updatedVendor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getVendorCount() {
        long count = vendorService.getVendorCount();
        return ResponseEntity.ok(count);
    }
}