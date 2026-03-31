package com.myinnovate.demo.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class B2cOrderDetail {
    
    @Id
    private int orderNumber;
    private String category;
    private String shippingMothod;
    private String twoWay;
    private Date createdDate;
    private Date updatedDate;

    public B2cOrderDetail() {
    }   
}
