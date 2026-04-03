package com.myinnovate.demo.controller;

import com.myinnovate.demo.entity.Orders;
import com.myinnovate.demo.repo.OrdersRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersRepo ordersRepo;

    @GetMapping("")
    public List<Orders> getOrders() {
        return ordersRepo.findByInvalid(false);
    }

    @PostMapping("/delete/{number}")
    public void deleteOrders(@PathVariable int number) {
        Orders order = ordersRepo.findByNumber(number);
        if (order != null) {
            order.setInvalid(true);
            ordersRepo.save(order);
        }
    }
}
