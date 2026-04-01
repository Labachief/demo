package com.myinnovate.demo.repo;

import com.myinnovate.demo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {

    public Orders findByNumber(int number);
}
