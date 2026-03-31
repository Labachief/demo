package com.myinnovate.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myinnovate.demo.entity.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {

}
