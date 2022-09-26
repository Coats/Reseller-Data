package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "coats_reseller_bulk_order_line")
public class ResellerBulkOrderLine {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String order_id;
    private Integer bulk_order_id;
    private Integer line_no;
    private String customer_material_no;
    private long article_id;
    private long brand_id;
    private long ticket_id;
    private long length_id;
    private long finish_id;
    private long shade_id;
    private String coats_material_no;
    private long ordered_quantity;
    private long adjusted_quantity;
    private long produced_quantity;
    private long so_line_number;
    private short status_id;
    private java.sql.Date required_date;
    //private Date required_date;
    private String price;
    private Integer bulkorder_id;

}

