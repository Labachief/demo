package com.myinnovate.demo.repo;

import com.myinnovate.demo.entity.Route;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RouteRepo extends JpaRepository<Route, Integer> {

    // public List<Route> findByShipment_IdOrderByRouteNumber(int shipmentId);

    // public List<Route> findByShipment_OrderNumberOrderByRouteNumber(int orderNumber);

    // public void deleteByShipment_Id(int shipmentId);

    @Transactional
    public void deleteByShipmentId(int shipmentId);
}
