package com.miraiyashi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="Message",indexes = {@Index(name = "tacIdx",columnList = "tacIdx,bcIdx")})
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "mIdx")
    private int mIdx;

    @Column()
    private int tacIdx;

    @Column()
    private int bcIdx;

    @Column()
    private int orderNo;

    @Column()
    private String message;

    @Column()
    private int type;

}
