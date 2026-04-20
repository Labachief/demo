package com.myinnovate.demo.controller;

import com.myinnovate.demo.entity.OrderDetail;
import com.myinnovate.demo.entity.Orders;
import com.myinnovate.demo.entity.Shipment;
import com.myinnovate.demo.repo.OrdersRepo;
import com.myinnovate.demo.repo.RouteRepo;
import com.myinnovate.demo.repo.ShipmentRepo;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;


@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private ShipmentRepo shipmentRepo;

    @Autowired
    private RouteRepo routeRepo;

    @GetMapping("/{id}")
    public Optional<Shipment> getShipment(@PathVariable int id) {
        return shipmentRepo.findById(id);
    }

    @PostMapping("/create")
    public Shipment createShipment(@RequestBody Shipment shipment) {
        Shipment shipment1 = shipmentRepo.save(shipment);
        Orders order = ordersRepo.findById(shipment1.getOrderNumber()).orElseThrow(() -> new RuntimeException("Order not found"));
        OrderDetail detail = order.getDetail();
        detail.setTrackingNumber(shipment1.getId());
        ordersRepo.save(order);
        return shipment1;
    }

    @PostMapping("/update")
    @Transactional
    public Shipment updateShipment(@RequestBody Shipment shipment) {
        routeRepo.deleteByShipmentId(shipment.getId());
        return shipmentRepo.save(shipment);
    }

}
