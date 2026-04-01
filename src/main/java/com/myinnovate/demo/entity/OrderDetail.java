package com.myinnovate.demo.entity;

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
    private String descriptionOfGoods;
    private String shipper;
    private String por;
    private String shipperPhone;
    private String shipperEmail;
    private String consignee;
    private String pod;
    private String consigneePhone;
    private String consigneeEmail;
    private String trackingNumber;
    private String shippingMethod;
    private Date eta;
    private String note;
    private Date createdDate;
    private Date updatedDate;
}
