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
@Table(name ="BotMessage",indexes = {@Index(name = "bIdx",columnList = "bIdx,type,bmIdx,bcIdx")})
//@Index(name = "idx",columnList = "idx")
public class BotMessage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bmIdx")
    private int bmIdx;

    @Column()
    private String message;

    @Column()
    private int type;

    @Column()
    private int bIdx;

    @Column()
    private int bcIdx;

}
