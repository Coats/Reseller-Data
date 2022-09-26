package com.coats.wbasubscriber.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_sales_orgs")
public class SalesOrgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String sales_org_name;
    private String sap_instance;
    private Integer ecomtypes_id;
    private Integer division;
}
