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
@Table(name = "UserPaymentHistory",indexes = {@Index(name = "uphIdx",columnList = "uphIdx")})
public class UserPaymentHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "uphIdx")
    private int uphIdx;

    @Column
    private int idx;

    @Column
    private String PaymentType;

    @Column
    private int charge;

    @Column
    private String orderNumber;

    @Column
    private String paymentId;

    @Column
    private String receipt;

    @CreatedDate
    @Column
    private Date regDt;

    @Column
    private String regIp;

}
