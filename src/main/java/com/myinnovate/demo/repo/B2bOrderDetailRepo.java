package com.myinnovate.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myinnovate.demo.entity.B2bOrderDetail;

public interface B2bOrderDetailRepo extends JpaRepository<B2bOrderDetail, Integer> {

}
