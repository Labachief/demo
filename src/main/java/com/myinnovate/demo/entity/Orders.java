package com.myinnovate.demo.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Data
@Entity
public class Orders {

    @Id
    private int number;
    private String typeOfBusiness;
    private String typeOfOrder;
    private Boolean invalid;;
    private Date createdDate;
    private Date updatedDate;

    @OneToOne(targetEntity = OrderDetail.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "typeOfBusiness")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = B2bOrderDetail.class, name = "B2B"),
        @JsonSubTypes.Type(value = B2cOrderDetail.class, name = "B2C")
    })
    private OrderDetail detail;

    public Orders() {
    }

    // public void setDetail(OrderDetail detail) {
    //     this.detail = detail;
    //     syncDetailOrderNumber();
    // }

    // @PrePersist
    // @PreUpdate
    // private void syncDetailOrderNumber() {
    //     if (detail != null) {
    //         detail.setOrderNumber(number);
    //     }
    // }

}
