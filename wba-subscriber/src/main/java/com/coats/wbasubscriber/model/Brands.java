package com.coats.wbasubscriber.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_brands")
@Builder
public class Brands {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private BigInteger id;
   private String brand_name;
}
