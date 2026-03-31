package com.myinnovate.demo.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Orders {

    @Id
    private int number;
    private String typeOfBusiness;
    private String typeOfOrder;
    private String item;
    private String DescriptionOfGoods;
    private String shipper;
    private String por;
    private String shipperPhone;
    private String shipperEmail;
    private String consignee;
    private String pod;
    private String consigneePhone;
    private String consigneeEmail;
    private String trackingNumber;
    private Date eta;
    private String note;
    private Date createdDate;
    private Date updatedDate;

    public Orders() {
    }

}
