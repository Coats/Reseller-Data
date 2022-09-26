package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coats_time_formats")
public class TimeFormats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String time_format;
}
