package com.project.undefined.company.entity;

import com.project.undefined.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Series series;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Region region;

    public enum Series {
        A, B, C, D, E, F, IPO
    }

    public enum Region {
        SEOUL, ETC
    }
}
