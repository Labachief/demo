package com.myinnovate.demo.repo;

import com.myinnovate.demo.entity.Shipment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepo extends JpaRepository<Shipment, Integer> {

    // public Shipment findById(int id);

    // public Shipment findByOrderNumber(int orderNumber);

    // public List<Shipment> findByStatus(String status);
}

