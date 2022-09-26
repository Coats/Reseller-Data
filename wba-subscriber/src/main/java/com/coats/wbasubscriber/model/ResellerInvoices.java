package com.coats.wbasubscriber.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_reseller_invoices")
public class ResellerInvoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer order_number;
    private Integer order_id;
    private Integer customer_id;
    private String customer_code;
    private String customer_name;
    private String sap_order_no;
    private Integer sap_order_line_no;
    private String order_type;
    private String po_number;
    private Integer ship_to_party_id;
    private String invoice_no;
    private String total_invoice_value;
    private String currency;
    private String created_date;
    private String payment_due_date;
    private String status;
    private String inv_line_no;
    private String material;
    private String brand_name;
    private String length_name;
    private Integer length_id;
    private String uom;
    private Integer inv_quantity;
    private String net_weight;
    private String gross_weight;
    private String item_net_value;
    private String item_tax_amount;
    private String total_tax_amount;
    private String total_value_dc;
    private String total_value_lc;



}
