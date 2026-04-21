package com.myinnovate.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myinnovate.demo.entity.B2bOrderDetail;
import com.myinnovate.demo.entity.B2cOrderDetail;
import com.myinnovate.demo.entity.Orders;
import com.myinnovate.demo.entity.Route;
import com.myinnovate.demo.entity.Shipment;
import com.myinnovate.demo.repo.B2bOrderDetailRepo;
import com.myinnovate.demo.repo.OrdersRepo;
import com.myinnovate.demo.repo.ShipmentRepo;


@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private B2bOrderDetailRepo b2bOrderDetailRepo;

    @Autowired
    private ShipmentRepo shipmentRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("")
    @Transactional
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Orders order = new Orders();
        order.setNumber(1);
        order.setTypeOfBusiness("B2B"); 
        order.setInvalid(false);

        B2bOrderDetail detail = new B2bOrderDetail(); 
        detail.setOrderNumber(1);
        detail.setContainer(true);
        detail.setShippingMethod("轉運");
        detail.setTrackingNumber(1);

        // 【關鍵點】必須在 Java 物件層級建立關聯
        // 這樣快取中的 order 物件才會持有 detail
        order.setDetail(detail); 

        // 因為你有設定 CascadeType.ALL，只要存 order，detail 就會一起存入
        ordersRepo.save(order); 

        Shipment shipment = new Shipment();
        shipment.setOrderNumber(1);
        shipment.setShipperMethod("轉運");
        shipment.setTransferHubs(
                new ArrayList<>(List.of("台北轉運站", "桃園轉運站", "新竹轉運站", "台中轉運站"))
        );
        shipment.setStatus("已出貨");
        shipmentRepo.save(shipment);
        entityManager.refresh(shipment);

        Route route1 = new Route();
        route1.setShipmentId(shipment.getId());
        route1.setRouteNumber(1);
        route1.setFromLocation("台北轉運站");
        route1.setToLocation("桃園轉運站");
        route1.setItem("手機");
        route1.setQuantity("10");
        route1.setUnit("台");
        route1.setLogistics("貨運");
        route1.setCarrier("黑貓");
        route1.setStatus("運送中");

        Route route2 = new Route();
        route2.setShipmentId(shipment.getId());
        route2.setRouteNumber(2);
        route2.setFromLocation("桃園轉運站");
        route2.setToLocation("新竹轉運站");
        route2.setItem("手機");
        route2.setQuantity("10");
        route2.setUnit("台");
        route2.setLogistics("貨運");
        route2.setCarrier("黑貓");
        route2.setStatus("運送中");

        Route route3 = new Route();
        route3.setShipmentId(shipment.getId());
        route3.setRouteNumber(3);
        route3.setFromLocation("桃園轉運站");
        route3.setToLocation("新竹轉運站");
        route3.setItem("手機");
        route3.setQuantity("10");
        route3.setUnit("台");
        route3.setLogistics("貨運");
        route3.setCarrier("黑貓");
        route3.setStatus("運送中");

        Route route4 = new Route();
        route4.setShipmentId(shipment.getId());
        route4.setRouteNumber(4);
        route4.setFromLocation("新竹轉運站");
        route4.setToLocation("台中轉運站");
        route4.setItem("手機");
        route4.setQuantity("10");
        route4.setUnit("台");
        route4.setLogistics("貨運");
        route4.setCarrier("黑貓");
        route4.setStatus("運送中");
        
        shipment.setRoutes(new ArrayList<>(List.of(route1, route2, route3, route4)));
        shipmentRepo.save(shipment);

        Orders order2 = new Orders();
        order2.setNumber(2);
        order2.setTypeOfBusiness("B2C");
        order2.setInvalid(false);

        B2cOrderDetail detail2 = new B2cOrderDetail(); 
        detail2.setOrderNumber(2);
        detail2.setShippingMethod("直送");
        detail2.setCategory("Electronics");
        detail2.setTwoWay(false);

        // 【關鍵點】必須在 Java 物件層級建立關聯
        // 這樣快取中的 order 物件才會持有 detail
        order2.setDetail(detail2); 

        // 因為你有設定 CascadeType.ALL，只要存 order，detail 就會一起存入
        ordersRepo.save(order2); 

        // 測試：此時從快取拿到的 order 就會有 detail
        Orders order1 = ordersRepo.findById(1).orElseThrow();
        System.out.println("Order Object: " + order1);
        System.out.println("Detail Object: " + order1.getDetail()); // 這裡就不會是 null 了
        System.out.println("Method Object: " + order1.getDetail().getShippingMethod());
        Orders order3 = ordersRepo.findById(2).orElseThrow();
        System.out.println("Order Object: " + order3);
        System.out.println("Detail Object: " + order3.getDetail()); // 這裡就不會是 null 了
        System.out.println("Method Object: " + order3.getDetail().getShippingMethod());
        
        return String.format(template, name);
    }
}
