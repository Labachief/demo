package com.myinnovate.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myinnovate.demo.entity.B2cOrderDetail;

public interface B2cOrderDetailRepo extends JpaRepository<B2cOrderDetail, Integer> {

}
