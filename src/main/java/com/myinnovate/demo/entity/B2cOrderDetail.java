package com.myinnovate.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class B2cOrderDetail extends OrderDetail {
    
    private String category;
    private Boolean twoWay;

    public B2cOrderDetail() {
    }   
}
