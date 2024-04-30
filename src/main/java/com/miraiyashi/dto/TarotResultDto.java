package com.miraiyashi.dto;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TarotResultDto {
    private  String botImage;
    private String botName;
    private String cardImage;
    private String title;
    private ArrayList<String> message;
}
