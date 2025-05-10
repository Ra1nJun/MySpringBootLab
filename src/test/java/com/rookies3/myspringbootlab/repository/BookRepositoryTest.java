package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void testDeleteBook(){
        List<Book> listBook = bookRepository.findByAuthor("철수");
        if(listBook.isEmpty()){
            throw new RuntimeException("Book Not Found");
        }
        bookRepository.deleteAll(listBook);
    }

    @Test
    void testUpdateBook(){
        List<Book> listBook = bookRepository.findByAuthor("홍길동");
        if(listBook.isEmpty()){
            throw new RuntimeException("Book Not Found");
        }

        listBook.forEach(book -> book.setAuthor("철수"));
        bookRepository.saveAll(listBook);
        assertThat(listBook).extracting("author").containsOnly("철수");
    }

    @Test
    void testFindByAuthor(){
        List<Book> listBook = bookRepository.findByAuthor("홍길동");
        if(listBook.isEmpty()){
            throw new RuntimeException("Book Not Found");
        }
        assertThat(listBook).extracting("author").containsOnly("홍길동");
    }

    @Test
    void testFindByIsbn(){
        Optional<Book> optionalBook = bookRepository.findByIsbn("9788956746432");

        if(optionalBook.isPresent()){
            Book existBook = optionalBook.get();
            assertThat(existBook.getIsbn()).isEqualTo("9788956746432");
        }

    }

    @Test
    void testCreateBook(){
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        book.setPublishDate(LocalDate.parse("2025-05-07"));

        Book addBook = bookRepository.save(book);

        assertThat(addBook).isNotNull();
        assertThat(addBook.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    void testCreateBook2(){
        Book book = new Book();
        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");
        book.setPrice(35000);
        book.setPublishDate(LocalDate.parse("2025-04-30"));

        Book addBook = bookRepository.save(book);

        assertThat(addBook).isNotNull();
        assertThat(addBook.getTitle()).isEqualTo("JPA 프로그래밍");
    }

}