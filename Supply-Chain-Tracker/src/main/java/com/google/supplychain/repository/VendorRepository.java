package com.google.supplychain.repository;

import com.google.supplychain.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByEmail(String email);

    List<Vendor> findByStatus(Vendor.VendorStatus status);

    List<Vendor> findByNameContainingIgnoreCase(String name);

    @Query("SELECT v FROM Vendor v WHERE v.status = 'ACTIVE' ORDER BY v.name")
    List<Vendor> findAllActiveVendors();

    boolean existsByEmail(String email);
}