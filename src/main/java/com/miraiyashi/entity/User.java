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
@Table(name ="User", indexes = {@Index(name = "idx",columnList = "idx"),@Index(name = "userId",columnList = "userId")})
//@Index(name = "idx",columnList = "idx")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "idx")
    private int idx;

    @Column()
    private String userId;

    @Column()
    private int userState;

    @Column()
    private int mineral;

    @Column()
    private String joinType;

    @Column()
    private String platform;

    @CreatedDate
    @Column()
    private Date joinDt;

    @Column()
    private String joinIp;

    @Column()
    private String deviceUuid;

    @CreatedDate
    @Column()
    private Date leaveDt;

    @Column()
    private String leaveIp;
}
