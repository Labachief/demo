package com.myinnovate.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OrderDetail {

    @Id
    protected int orderNumber;
    private String item;
    private int quantity;
    private String unit;
    private String descriptionOfGoods;
    private String shipper;
    private String por;
    private String shipperPhone;
    private String shipperEmail;
    private String consignee;
    private String pod;
    private String consigneePhone;
    private String consigneeEmail;
    private String shippingMethod;
    private Integer trackingNumber;
    private Date eta;
    private String note;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
