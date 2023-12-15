package com.nisum.challenge.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phone")
@Data
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "number")
    private String number; // TODO Check db types

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "country_code")
    private String countryCode;
}
