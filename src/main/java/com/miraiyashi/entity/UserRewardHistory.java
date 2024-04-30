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
@Table(name = "UserRewardHistory",indexes = {@Index(name = "urhIdx",columnList = "urhIdx")})
public class UserRewardHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "urhIdx")
    private int urhIdx;

    @Column
    private int idx;

    @Column
    private int reward;

    // 유료 1 무료 0
   @Column
    private int type;

    @Column
    private int beforeMineral;

    @Column
    private int afterMineral;

    @CreatedDate
    @Column
    private Date regDt;

    @Column
    private String regIp;

}
