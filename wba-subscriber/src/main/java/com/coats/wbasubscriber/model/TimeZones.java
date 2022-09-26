package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coats_timezones")
public class TimeZones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String timezone;
}
