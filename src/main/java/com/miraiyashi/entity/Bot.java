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
@Table(name ="Bot",indexes = {@Index(name = "bIdx",columnList = "bIdx")})
//@Index(name = "idx",columnList = "idx")
public class Bot {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "bIdx")
    private int bIdx;

    @Column()
    private String botImage;

    @Column()
    private String botName;


}
