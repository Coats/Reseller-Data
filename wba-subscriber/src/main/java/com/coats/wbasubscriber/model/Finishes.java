package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coats_finishes")
public class Finishes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String finish_name;
}
