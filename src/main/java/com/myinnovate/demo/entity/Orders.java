package com.myinnovate.demo.entity;

import java.util.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Data
@Entity
public class Orders {

    @Id
    private int number;
    private String typeOfBusiness;
    private String typeOfOrder;
    private Date createdDate;
    private Date updatedDate;

    @OneToOne(targetEntity = OrderDetail.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private OrderDetail detail;

    public Orders() {
    }

}
