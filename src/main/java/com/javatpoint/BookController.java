package com.javatpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAllNotes() {
        return (List<Book>) bookRepository.findAll();
    }

    @PostMapping("/books")
    public Book createNote(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("books/{id}")
    public Book getNoteById(@PathVariable(value = "id") int bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @PutMapping("/books/{id}")
    public Book updateNote(@PathVariable(value = "id") int bookId, @RequestBody Book bookDetails) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        book.setBookName(bookDetails.getBookName());
        book.setBookAuthor(bookDetails.getBookAuthor());
        book.setBookClass(bookDetails.getBookClass());
        book.setBookPrice(bookDetails.getBookPrice());

        Book updatedBook = bookRepository.save(book);

        return  updatedBook;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") int bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }
}
