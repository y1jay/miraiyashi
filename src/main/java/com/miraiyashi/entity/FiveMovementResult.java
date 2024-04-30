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
@Table(name ="FiveMovementResult",indexes = {@Index(name = "movement",columnList = "count")})
public class FiveMovementResult {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "fmrIdx")
    private int fmrIdx;

    @Column()
    private String movement;

    @Column()
    private String count;

}
