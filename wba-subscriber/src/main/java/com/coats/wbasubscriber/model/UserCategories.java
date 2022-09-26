package com.coats.wbasubscriber.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coats_user_categories")
public class UserCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
}
