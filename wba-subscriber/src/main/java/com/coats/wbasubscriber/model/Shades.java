package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_shades")
public class Shades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private BigInteger shade_card_id;
    private String shade_name;
    private String shade_code;
    private Integer color_ratio_r;
    private Integer color_ratio_g;
    private Integer color_ratio_b;
    private BigInteger shade_position;
}
