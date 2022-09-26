package com.coats.wbasubscriber.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_customers")
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String customer_code;
    private String customer_name;
    private BigInteger sales_org_id;
    private BigInteger delivery_plant_id;
    private String common_email;
    private String distribution_channel;
    private Integer ecomordertypes_id;
    private Integer reseller_app_enable;
    private String division;
}
