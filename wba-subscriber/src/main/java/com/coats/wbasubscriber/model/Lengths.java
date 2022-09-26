package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coats_lengths")
public class Lengths {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String length_name;
}
