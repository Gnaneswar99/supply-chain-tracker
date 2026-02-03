package com.google.supplychain.repository;

import com.google.supplychain.model.Shipment;
import com.google.supplychain.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    List<Shipment> findByStatus(Shipment.ShipmentStatus status);

    List<Shipment> findByShipmentType(Shipment.ShipmentType shipmentType);

    List<Shipment> findBySourceWarehouse(Warehouse warehouse);

    List<Shipment> findByDestinationWarehouse(Warehouse warehouse);

    List<Shipment> findByCarrier(String carrier);

    @Query("SELECT s FROM Shipment s WHERE s.status NOT IN ('DELIVERED', 'CANCELLED')")
    List<Shipment> findActiveShipments();

    @Query("SELECT s FROM Shipment s WHERE s.expectedDeliveryDate < :now AND s.status NOT IN ('DELIVERED', 'CANCELLED')")
    List<Shipment> findDelayedShipments(@Param("now") LocalDateTime now);

    @Query("SELECT s FROM Shipment s WHERE s.shippedDate BETWEEN :startDate AND :endDate")
    List<Shipment> findShipmentsBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT s FROM Shipment s WHERE s.destinationWarehouse.id = :warehouseId AND s.status = 'IN_TRANSIT'")
    List<Shipment> findIncomingShipments(@Param("warehouseId") Long warehouseId);

    @Query("SELECT s FROM Shipment s WHERE s.sourceWarehouse.id = :warehouseId AND s.status = 'IN_TRANSIT'")
    List<Shipment> findOutgoingShipments(@Param("warehouseId") Long warehouseId);

    @Query("SELECT COUNT(s) FROM Shipment s WHERE s.status = :status")
    Long countByStatus(@Param("status") Shipment.ShipmentStatus status);
}