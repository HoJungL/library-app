package com.group.libraryapp.dto.book.request;

import lombok.Data;

@Data
public class BookLoanRequest {

    private String userName;
    private String bookName;
}
