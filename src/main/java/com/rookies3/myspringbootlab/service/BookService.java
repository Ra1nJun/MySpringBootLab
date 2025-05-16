package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.controller.dto.PartialBookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.exception.ErrorCode;
import com.rookies3.myspringbootlab.repository.BookDetailRepository;
import com.rookies3.myspringbootlab.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    
    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;
    
    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                //.map(book -> BookDTO.BookResponse.from(book))
                .map(BookDTO.Response::fromEntity)
                .toList();  //Stream<BookResponse> => List<BookResponse>
    }
    
    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book","id",id));
        return BookDTO.Response.fromEntity(book);
    }
    
    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,"Book","isbn",isbn));
        return BookDTO.Response.fromEntity(book);
    }
    
    public List<BookDTO.Response> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public List<BookDTO.Response> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }
    
    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        // Validate book isbn is not already in use
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE,
                    request.getIsbn());
        }

        // Create book entity
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        // Create book detail if provided
        if (request.getDetailRequest() != null) {
            BookDetail bookDetail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .book(book)
                    .build();

            book.setBookDetail(bookDetail);
        }

        // Save and return the book
        Book savedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(savedBook);
    }


    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        // Find the book
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));

        // Check if another book already has the Isbn
        if (!book.getIsbn().equals(request.getIsbn()) &&
                bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE,
                    request.getIsbn());
        }

        // Update student basic info
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        // Update student detail if provided
        if (request.getDetailRequest() != null) {
            BookDetail bookDetail = book.getBookDetail();

            // Create new detail if not exists
            if (bookDetail == null) {
                bookDetail = new BookDetail();
                bookDetail.setBook(book);
                book.setBookDetail(bookDetail);
            }

            // Update detail fields
            bookDetail.setDescription(request.getDetailRequest().getDescription());
            bookDetail.setLanguage(request.getDetailRequest().getLanguage());
            bookDetail.setPageCount(request.getDetailRequest().getPageCount());
            bookDetail.setPublisher(request.getDetailRequest().getPublisher());
            bookDetail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            bookDetail.setEdition(request.getDetailRequest().getEdition());
        }

        // Save and return updated book
        Book updatedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(updatedBook);
    }

    @Transactional
    public PartialBookDTO.Response partialUpdateBook(Long id, PartialBookDTO.PatchRequest request){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Book", "id", id));

        if(request.getIsbn() != null){
            if (!book.getIsbn().equals(request.getIsbn()) &&
                    bookRepository.existsByIsbn(request.getIsbn())) {
                throw new BusinessException(ErrorCode.ISBN_DUPLICATE,
                        request.getIsbn());
            }
            book.setIsbn(request.getIsbn());
        }

        updateBookFieldsIfPresent(book, request);

        if (request.getDetailRequest() != null) {
            updateBookDetailIfPresent(book, request.getDetailRequest());
        }

        Book updatedBook = bookRepository.save(book);
        return PartialBookDTO.Response.fromEntity(updatedBook);
    }

    private void updateBookFieldsIfPresent(Book book, PartialBookDTO.PatchRequest request) {
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(request.getPublishDate());
        }
    }

    @Transactional
    public PartialBookDTO.Response partialUpdateBookDetail(Long id, PartialBookDTO.BookDetailPatchRequest detailRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "id", id));

        updateBookDetailIfPresent(book, detailRequest);

        Book updatedBook = bookRepository.save(book);
        return PartialBookDTO.Response.fromEntity(updatedBook);
    }

    private void updateBookDetailIfPresent(Book book, PartialBookDTO.BookDetailPatchRequest detailRequest) {
        BookDetail detail = book.getBookDetail();

        if (detail == null) {
            detail = new BookDetail();
            detail.setBook(book);
            book.setBookDetail(detail);
        }

        if (detailRequest.getDescription() != null) {
            detail.setDescription(detailRequest.getDescription());
        }
        if (detailRequest.getLanguage() != null) {
            detail.setLanguage(detailRequest.getLanguage());
        }
        if (detailRequest.getPageCount() != null) {
            detail.setPageCount(detailRequest.getPageCount());
        }
        if (detailRequest.getPublisher() != null) {
            detail.setPublisher(detailRequest.getPublisher());
        }
        if (detailRequest.getCoverImageUrl() != null) {
            detail.setCoverImageUrl(detailRequest.getCoverImageUrl());
        }
        if (detailRequest.getEdition() != null) {
            detail.setEdition(detailRequest.getEdition());
        }
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,"Book","id",id);
        }
        bookRepository.deleteById(id);
    }
}