package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookServiceController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO.BookResponse> create(@Valid @RequestBody
                                                       BookDTO.BookCreateRequest request) {
        BookDTO.BookResponse createdBook = bookService.createBook(request);
        return ResponseEntity.ok(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO.BookResponse>> getBooks(){
        List<BookDTO.BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO.BookResponse> getBookById(@PathVariable Long id){
        BookDTO.BookResponse existBook = bookService.getBookById(id);
        return ResponseEntity.ok(existBook);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.BookResponse> getBookByIsbn(@PathVariable String isbn){
        BookDTO.BookResponse existBook = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(existBook);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO.BookResponse>> getBooksByAuthor(@PathVariable String author) {
        List<BookDTO.BookResponse> existBook = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(existBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> updateBook(@PathVariable Long id,
                                                           @Valid @RequestBody BookDTO.BookUpdateRequest request) {
        BookDTO.BookResponse updatedBook = bookService.updateBook(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return  ResponseEntity.noContent().build();
    }
}
