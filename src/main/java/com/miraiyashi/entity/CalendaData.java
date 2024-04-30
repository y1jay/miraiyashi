package com.miraiyashi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="CalendaData",indexes = {@Index(name = "ganjee",columnList = "kyganjee,kmganjee,kdganjee")})
public class CalendaData {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "cdIdx")
    private int cdIdx;

    @Column()
    private String kyganjee;

    @Column()
    private String kdganjee;

    @Column()
    private String kmganjee;

    @Column()
    private int cdSy;

    @Column()
    private int cdSm;

    @Column()
    private int cdSd;
}
