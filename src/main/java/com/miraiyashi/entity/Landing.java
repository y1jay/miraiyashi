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
@Table(name ="Landing", indexes = {@Index(name = "idx",columnList = "idx")})
//@Index(name = "idx",columnList = "idx")
public class Landing {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "lIdx")
    private int lIdx;

    @Column()
    private int idx;

    @Column()
    private String landingCode;

    @CreatedDate
    @Column()
    private Date regDt;

    @Column()
    private String regIp;
}
