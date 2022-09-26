package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_sales_org_materials")
public class SalesOrgMaterials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger sales_org_id;
    private BigInteger plant_id;
    private String article;
    private BigInteger brand_id;
    private BigInteger ticket_id;
    private Integer length_id;
    private Integer finish_id;
}
