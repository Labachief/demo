package com.myinnovate.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table
@ToString(callSuper = true)
public class B2bOrderDetail extends OrderDetail {

    private Boolean container;
    
    public B2bOrderDetail() {
    }
}
