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
@Table(name ="BotCategory",indexes = {@Index(name = "bcIdx",columnList = "bcIdx")})
//@Index(name = "idx",columnList = "idx")
public class BotCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bcIdx")
    private int bcIdx;

    @Column()
    private String title;

    @Column()
    private int mineral;

    @Column() // botIdx
    private int bIdx;

}
