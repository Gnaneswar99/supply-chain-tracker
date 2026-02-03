package com.google.supplychain;

import com.google.supplychain.model.*;
import com.google.supplychain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final VendorRepository vendorRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ShipmentRepository shipmentRepository;

    @Override
    public void run(String... args) throws Exception {
        loadVendors();
        loadWarehouses();
        loadInventoryItems();
        loadShipments();
        System.out.println("✓ Sample data loaded successfully!");
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Dell Technologies");
        vendor1.setContactPerson("Michael Dell");
        vendor1.setEmail("sales@dell.com");
        vendor1.setPhone("1-800-624-9897");
        vendor1.setAddress("Round Rock, Texas, USA");
        vendor1.setStatus(Vendor.VendorStatus.ACTIVE);
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Cisco Systems");
        vendor2.setContactPerson("Chuck Robbins");
        vendor2.setEmail("sales@cisco.com");
        vendor2.setPhone("1-800-553-6387");
        vendor2.setAddress("San Jose, California, USA");
        vendor2.setStatus(Vendor.VendorStatus.ACTIVE);
        vendorRepository.save(vendor2);

        Vendor vendor3 = new Vendor();
        vendor3.setName("HP Enterprise");
        vendor3.setContactPerson("Antonio Neri");
        vendor3.setEmail("sales@hpe.com");
        vendor3.setPhone("1-800-474-6836");
        vendor3.setAddress("Houston, Texas, USA");
        vendor3.setStatus(Vendor.VendorStatus.ACTIVE);
        vendorRepository.save(vendor3);
    }

    private void loadWarehouses() {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setName("Sunnyvale Data Center");
        warehouse1.setLocation("1600 Amphitheatre Parkway");
        warehouse1.setCity("Sunnyvale");
        warehouse1.setState("California");
        warehouse1.setCountry("USA");
        warehouse1.setZipCode("94043");
        warehouse1.setTotalCapacity(10000);
        warehouse1.setCurrentUtilization(7500);
        warehouse1.setStatus(Warehouse.WarehouseStatus.OPERATIONAL);
        warehouseRepository.save(warehouse1);

        Warehouse warehouse2 = new Warehouse();
        warehouse2.setName("Austin Distribution Center");
        warehouse2.setLocation("500 W 2nd Street");
        warehouse2.setCity("Austin");
        warehouse2.setState("Texas");
        warehouse2.setCountry("USA");
        warehouse2.setZipCode("78701");
        warehouse2.setTotalCapacity(8000);
        warehouse2.setCurrentUtilization(3200);
        warehouse2.setStatus(Warehouse.WarehouseStatus.OPERATIONAL);
        warehouseRepository.save(warehouse2);

        Warehouse warehouse3 = new Warehouse();
        warehouse3.setName("New York Fulfillment Center");
        warehouse3.setLocation("85 10th Avenue");
        warehouse3.setCity("New York");
        warehouse3.setState("New York");
        warehouse3.setCountry("USA");
        warehouse3.setZipCode("10011");
        warehouse3.setTotalCapacity(5000);
        warehouse3.setCurrentUtilization(4800);
        warehouse3.setStatus(Warehouse.WarehouseStatus.OPERATIONAL);
        warehouseRepository.save(warehouse3);
    }

    private void loadInventoryItems() {
        Vendor dell = vendorRepository.findByEmail("sales@dell.com").orElse(null);
        Vendor cisco = vendorRepository.findByEmail("sales@cisco.com").orElse(null);
        Vendor hpe = vendorRepository.findByEmail("sales@hpe.com").orElse(null);
        Warehouse sunnyvale = warehouseRepository.findByCity("Sunnyvale").get(0);
        Warehouse austin = warehouseRepository.findByCity("Austin").get(0);

        InventoryItem item1 = new InventoryItem();
        item1.setSku("SRV-DELL-R750");
        item1.setName("Dell PowerEdge R750 Server");
        item1.setDescription("High-performance 2U rack server");
        item1.setCategory(InventoryItem.ItemCategory.SERVERS);
        item1.setQuantity(150);
        item1.setMinimumStockLevel(20);
        item1.setUnitPrice(new BigDecimal("8500.00"));
        item1.setVendor(dell);
        item1.setWarehouse(sunnyvale);
        item1.setStatus(InventoryItem.ItemStatus.IN_STOCK);
        inventoryItemRepository.save(item1);

        InventoryItem item2 = new InventoryItem();
        item2.setSku("NET-CISCO-C9300");
        item2.setName("Cisco Catalyst 9300 Switch");
        item2.setDescription("48-port gigabit network switch");
        item2.setCategory(InventoryItem.ItemCategory.NETWORKING);
        item2.setQuantity(75);
        item2.setMinimumStockLevel(15);
        item2.setUnitPrice(new BigDecimal("4200.00"));
        item2.setVendor(cisco);
        item2.setWarehouse(sunnyvale);
        item2.setStatus(InventoryItem.ItemStatus.IN_STOCK);
        inventoryItemRepository.save(item2);

        InventoryItem item3 = new InventoryItem();
        item3.setSku("STR-HPE-MSA2060");
        item3.setName("HPE MSA 2060 Storage Array");
        item3.setDescription("SAN storage array with 24 SFF bays");
        item3.setCategory(InventoryItem.ItemCategory.STORAGE);
        item3.setQuantity(8);
        item3.setMinimumStockLevel(10);
        item3.setUnitPrice(new BigDecimal("15000.00"));
        item3.setVendor(hpe);
        item3.setWarehouse(austin);
        item3.setStatus(InventoryItem.ItemStatus.LOW_STOCK);
        inventoryItemRepository.save(item3);

        InventoryItem item4 = new InventoryItem();
        item4.setSku("CBL-CAT6A-100");
        item4.setName("CAT6A Ethernet Cable 100ft");
        item4.setDescription("High-speed ethernet cable");
        item4.setCategory(InventoryItem.ItemCategory.CABLES);
        item4.setQuantity(500);
        item4.setMinimumStockLevel(100);
        item4.setUnitPrice(new BigDecimal("45.00"));
        item4.setVendor(cisco);
        item4.setWarehouse(austin);
        item4.setStatus(InventoryItem.ItemStatus.IN_STOCK);
        inventoryItemRepository.save(item4);

        InventoryItem item5 = new InventoryItem();
        item5.setSku("SRV-DELL-R650");
        item5.setName("Dell PowerEdge R650 Server");
        item5.setDescription("Compact 1U rack server");
        item5.setCategory(InventoryItem.ItemCategory.SERVERS);
        item5.setQuantity(0);
        item5.setMinimumStockLevel(15);
        item5.setUnitPrice(new BigDecimal("6500.00"));
        item5.setVendor(dell);
        item5.setWarehouse(sunnyvale);
        item5.setStatus(InventoryItem.ItemStatus.OUT_OF_STOCK);
        inventoryItemRepository.save(item5);
    }

    private void loadShipments() {
        InventoryItem server = inventoryItemRepository.findBySku("SRV-DELL-R750").orElse(null);
        InventoryItem switches = inventoryItemRepository.findBySku("NET-CISCO-C9300").orElse(null);
        Vendor dell = vendorRepository.findByEmail("sales@dell.com").orElse(null);
        Warehouse sunnyvale = warehouseRepository.findByCity("Sunnyvale").get(0);
        Warehouse austin = warehouseRepository.findByCity("Austin").get(0);

        Shipment shipment1 = new Shipment();
        shipment1.setTrackingNumber("TRK-2024-001");
        shipment1.setItem(server);
        shipment1.setQuantity(25);
        shipment1.setVendor(dell);
        shipment1.setDestinationWarehouse(sunnyvale);
        shipment1.setShipmentType(Shipment.ShipmentType.INBOUND);
        shipment1.setStatus(Shipment.ShipmentStatus.IN_TRANSIT);
        shipment1.setCarrier("FedEx");
        shipment1.setShippedDate(LocalDateTime.now().minusDays(2));
        shipment1.setExpectedDeliveryDate(LocalDateTime.now().plusDays(1));
        shipmentRepository.save(shipment1);

        Shipment shipment2 = new Shipment();
        shipment2.setTrackingNumber("TRK-2024-002");
        shipment2.setItem(switches);
        shipment2.setQuantity(10);
        shipment2.setSourceWarehouse(sunnyvale);
        shipment2.setDestinationWarehouse(austin);
        shipment2.setShipmentType(Shipment.ShipmentType.TRANSFER);
        shipment2.setStatus(Shipment.ShipmentStatus.PENDING);
        shipment2.setExpectedDeliveryDate(LocalDateTime.now().plusDays(5));
        shipmentRepository.save(shipment2);

        Shipment shipment3 = new Shipment();
        shipment3.setTrackingNumber("TRK-2024-003");
        shipment3.setItem(server);
        shipment3.setQuantity(5);
        shipment3.setSourceWarehouse(sunnyvale);
        shipment3.setShipmentType(Shipment.ShipmentType.OUTBOUND);
        shipment3.setStatus(Shipment.ShipmentStatus.DELIVERED);
        shipment3.setCarrier("UPS");
        shipment3.setShippedDate(LocalDateTime.now().minusDays(5));
        shipment3.setExpectedDeliveryDate(LocalDateTime.now().minusDays(2));
        shipment3.setActualDeliveryDate(LocalDateTime.now().minusDays(2));
        shipmentRepository.save(shipment3);
    }
}