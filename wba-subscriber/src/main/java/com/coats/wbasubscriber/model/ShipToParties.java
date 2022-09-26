package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_ship_to_parties")
public class ShipToParties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger customer_id;
    private String ship_to_party_no;
    private String ship_to_party_name;
    private String address_line1;
    private String address_line2;
    private String street;
    private String city;
    private String state;
    private String zip_code;
}
