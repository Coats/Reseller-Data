package com.coats.wbasubscriber.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "coats_date_formats")
public class DateFormats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date_format;
}
