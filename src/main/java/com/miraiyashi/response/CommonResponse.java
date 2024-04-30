package com.miraiyashi.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CommonResponse  {
     int code = 200;
     String message ="Success";
}


