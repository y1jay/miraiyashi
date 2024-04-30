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
@Table(name ="ToonComponent",indexes = {@Index(name = "tcIdx",columnList = "tcIdx")})
public class ToonComponent {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "tcIdx")
    private int tcIdx;

    @Column()
    private int tIdx;

    @Column()
    private String toonImage;

    @Column()
    private int toonComponentHeart;

    @Column()
    private int amount;

//    @Column()
//    private Integer toonComponentHeart;

    @Column()
    private String toonComponentName;

    @Column()
    private String toonComponentDescription;

    @CreatedDate
    @Column()
    private Date regDt;

    @Column()
    private String regIp;

}
