package com.miraiyashi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="Luck")
//@Index(name = "idx",columnList = "idx")
public class Luck {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "lcIdx")
    private int lcIdx;

    @Column()
    private double percent;

    @Column()
    private int tcIdx;

    @Column()
    private Date regDt;

}
