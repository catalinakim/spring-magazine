package com.lina.springmagazine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsg {
    //HttpStatus httpStatus;
    String result;
    String msg;
}
