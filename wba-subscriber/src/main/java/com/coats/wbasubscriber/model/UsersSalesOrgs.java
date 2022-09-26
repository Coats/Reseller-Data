package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_users_sales_orgs")
public class UsersSalesOrgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger user_id;
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger sales_org_id;
}
