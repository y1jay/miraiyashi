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
@Table(name = "TarotCard")
//@Index(name = "idx",columnList = "idx")
public class TarotCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tacIdx")
    private int tacIdx;

    @Column()
    private String cardImage;

    @Column()
    private String title;

    @Column()
    private int useYn;

}
