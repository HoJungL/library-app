package com.group.libraryapp.dto.calculator.request;

import lombok.Data;

@Data // lombok 있어야함 ㅎㅎ... setting에서 annotation 수정하기.
public class CalculatorAddRequest {

    private final int num1;
    private final int num2;
}
