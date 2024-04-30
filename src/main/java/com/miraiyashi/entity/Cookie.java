package com.miraiyashi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Cookie")
//@Index(name = "idx",columnList = "idx")
public class Cookie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ckIdx")
    private int ckIdx;

    @Column()
    private double percent;

    @Column()
    private String result;

    @Column()
    private Date regDt;
}
