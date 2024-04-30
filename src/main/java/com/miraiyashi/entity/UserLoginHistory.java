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
@Table(name = "UserLoginHistory",indexes = {@Index(name = "ulhIdx",columnList = "ulhIdx")})
public class UserLoginHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ulhIdx")
    private int ulhIdx;

    @Column()
    private int idx;

    @Column()
    private String deviceUuid;

    @CreatedDate
    @Column()
    private Date loginDt;

    @Column()
    private String loginIp;


}


