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
@Table(name = "UserLuckyHistory",indexes = {@Index(name = "idx",columnList = "idx")})
public class UserLuckyHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ulchIdx")
    private int ulchIdx;

    @Column()
    private int idx;

    // 0: 쿠카 1:행운카드
    @Column()
    private String type;

    @Column()
    private String result;

    @CreatedDate
    @Column()
    private Date regDt;

    @Column()
    private String regIp;


}


