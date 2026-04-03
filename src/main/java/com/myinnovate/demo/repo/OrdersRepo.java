package com.myinnovate.demo.repo;

import com.myinnovate.demo.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {

    public Orders findByNumber(int number);

    public List<Orders> findByInvalid(Boolean invalid);

    public void deleteByNumber(int number);
}
