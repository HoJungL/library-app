package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //API의 진입지점을 말해주는것.
public class CalculatorController {

    @GetMapping("/add")
    //쿼리를 통해서 넘어온 데이터를 함수에 연결하기위해서는 @RequestParam을 써야함!
    public int addTwoNumbers(CalculatorAddRequest request) {
        return request.getNum1() + request.getNum2();
    }


    @PostMapping("/multiply")
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNum1() * request.getNum2();
    }
}
