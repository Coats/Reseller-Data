package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String username;
    private String first_name;
    private String last_name;
    private BigInteger customer_id;
    private BigInteger user_type_id;
    private String mobile;
    private Integer timezone_id;
    private Integer date_format_id;
    private Integer time_format_id;
    private Integer status_id;
    private Integer HX_Active;
    private Integer AX_Active;
    private Integer order_delay_alert;
    private Integer customer_payer;



}
