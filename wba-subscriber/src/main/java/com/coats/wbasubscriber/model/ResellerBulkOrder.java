package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "coats_reseller_bulk_order")
public class ResellerBulkOrder {
    @Id
    //@GeneratedValue(strategy = GenerationType.)
    private String id;
    private Integer bulk_order_id;
    private Integer order_line;
    private short source_id;
    private String order_no;
    private long sales_org_id;
    private Integer customer_id;
    private Integer requester_id;
    private Integer ship_to_party_id;
    private Integer buyer_id;
    private String po_number;
    private String surcharge_value;
    private String discount_value;
    private String net_value;
    private String currency;
    private short status_id;
    private String so_number;
    private java.sql.Timestamp created;
    private Integer created_user_id;
    private java.sql.Timestamp updated;
}

