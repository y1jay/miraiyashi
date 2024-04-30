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
@Table(name ="ToonMaster",indexes = {@Index(name = "tIdx",columnList = "tIdx")})
public class ToonMaster {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "tIdx")
    private int tIdx;

    @Column()
    private String toonIcon;

    @Column()
    private String toonThumbNail;

    @Column()
    private String toonWriter;

    @Column()
    private int toonMasterHeart;

    @Column()
    private String toonMasterName;

    @Column()
    private String toonMasterDescription;


    @CreatedDate
    @Column()
    private Date regDt;

    @CreatedDate
    @Column()
    private Date modDt;

    @Column()
    private String regIp;

}
