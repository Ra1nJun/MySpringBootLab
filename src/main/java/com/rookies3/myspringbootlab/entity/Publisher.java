package com.rookies3.myspringbootlab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate establishedDate;

    @Column
    private String address;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore @Builder.Default
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book){
        books.remove(book);
        book.setPublisher(null);
    }


}
