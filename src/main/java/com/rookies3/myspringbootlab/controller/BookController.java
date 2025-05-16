package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.controller.dto.PartialBookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO.Response>> getAllBooks() {
        List<BookDTO.Response> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getBookById(@PathVariable Long id) {
        BookDTO.Response book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getBookByIsbn(@PathVariable String isbn) {
        BookDTO.Response book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO.Response>> getBooksByAuthor(@PathVariable String author) {
        List<BookDTO.Response> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDTO.Response>> getBooksByTitle(@PathVariable String title) {
        List<BookDTO.Response> books = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookDTO.Response> createBook(@Valid @RequestBody BookDTO.Request request) {
        BookDTO.Response createdBook = bookService.createBook(request);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.Response> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO.Request request) {
        BookDTO.Response updatedBook = bookService.updateBook(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartialBookDTO.Response> partialUpdateBook(
            @PathVariable Long id,
            @RequestBody PartialBookDTO.PatchRequest request) {
        PartialBookDTO.Response partUpdatedBook = bookService.partialUpdateBook(id, request);
        return ResponseEntity.ok(partUpdatedBook);
    }

    @PatchMapping("/{id}/detail")
    public ResponseEntity<PartialBookDTO.Response> partialUpdateBookDetail(
            @PathVariable Long id,
            @RequestBody PartialBookDTO.BookDetailPatchRequest request) {
        PartialBookDTO.Response partUpdatedBook = bookService.partialUpdateBookDetail(id, request);
        return ResponseEntity.ok(partUpdatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
