package com.myinnovate.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table
@ToString(callSuper = true)
public class B2cOrderDetail extends OrderDetail {
    
    private String category;
    private String twoWay;

    public B2cOrderDetail() {
    }   
}
