package com.myinnovate.demo.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class B2bOrderDetail {

    @Id
    private int orderNumber;
    private String shippingMothod;
    private Boolean container;
    private Date createdDate;
    private Date updatedDate;
    
    public B2bOrderDetail() {
    }
}
