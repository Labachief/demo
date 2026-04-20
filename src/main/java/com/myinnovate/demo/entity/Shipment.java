package com.myinnovate.demo.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderNumber;
    private String shipperMethod;
    private String status;
    private String note;
    private String createdDate;
    private String updatedDate;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "transfer_hubs", columnDefinition = "jsonb") 
    private List<String> transferHubs;

    @OneToMany(
        targetEntity = Route.class,
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "shipment_id", referencedColumnName = "id")
    private List<Route> routes;

    public Shipment() {
    }
}
