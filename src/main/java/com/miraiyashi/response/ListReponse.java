package com.miraiyashi.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListReponse<T> extends CommonResponse {
    List<T> data;
}
