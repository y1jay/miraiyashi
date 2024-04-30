package com.miraiyashi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TarotDto {
    private  String botImage;
    private String botName;
    private String cardImage;
    private String title;
    private String message;
}
