package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_multisoldto_users")
public class MultiSoldToUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private Integer status;
    private Integer customer_id;
    private Integer requestor_id;
    private Integer sales_org_id;
}
