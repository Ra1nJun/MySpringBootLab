package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthor(String author);
    List<Book> findByTitle(String title);
    
    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT s FROM Book s JOIN FETCH s.bookDetail WHERE s.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    @Query("SELECT s FROM Book s JOIN FETCH s.bookDetail WHERE s.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);

    boolean existsByIsbn(String isbn);
}