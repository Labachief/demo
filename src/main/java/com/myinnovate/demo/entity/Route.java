package com.myinnovate.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "shipment_id")
    private Integer shipmentId;
    private Integer routeNumber;
    @Column(name = "from_location")
    private String fromLocation;
    @Column(name = "to_location")
    private String toLocation;
    private String item;
    private String quantity;
    private String unit;
    private String logistics;
    private String carrier;
    private String status;
    private String createdDate;
    private String updatedDate;

    public Route() {
    }
}
