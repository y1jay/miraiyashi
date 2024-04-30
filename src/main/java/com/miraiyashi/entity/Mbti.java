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
@Table(name ="Mbti", indexes = {@Index(name = "idx",columnList = "idx")})
//@Index(name = "idx",columnList = "idx")
public class Mbti {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "mIdx")
    private int mIdx;

    @Column()
    private int idx;

    @Column()
    private String result;

    @CreatedDate
    @Column()
    private Date regDt;

    @Column()
    private String regIp;
}
