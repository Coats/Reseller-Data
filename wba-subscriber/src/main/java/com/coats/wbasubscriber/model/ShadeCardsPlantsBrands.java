package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "coats_shade_cards_plants_brands")
public class ShadeCardsPlantsBrands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger shade_card_plant_id;
    private BigInteger brand_id;
}
