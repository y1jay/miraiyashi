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
@Table(name = "UserTarotHistory",indexes = {@Index(name = "uthIdx",columnList = "uthIdx")})
public class UserTarotHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "uthIdx")
    private int uthIdx;

    @Column
    private int idx;

    @Column
    private String result;

    @Column
    private int tacIdx;

    @CreatedDate
    @Column
    private Date regDt;

    @Column
    private String regIp;

}
