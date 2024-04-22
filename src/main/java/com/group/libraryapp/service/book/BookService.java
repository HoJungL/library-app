package com.group.libraryapp.service.book;

import com.group.libraryapp.repository.book.BookMemoryRepository;
import com.group.libraryapp.repository.book.BookMysqlRepository;
import com.group.libraryapp.repository.book.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookMemoryRepository bookMemoryRepository;

    public BookService(BookMemoryRepository bookMemoryRepository) {
        this.bookMemoryRepository = bookMemoryRepository;
    }

    public void saveBook() {
        bookMemoryRepository.saveBook();
    }
}
