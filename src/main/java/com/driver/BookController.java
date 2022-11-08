package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        book.setId(this.id);
        //auto increment id
        this.id = this.id+1;
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity getBookById(@PathVariable int id){
        for(Book b : bookList){
            if(b.getId()==id){
                return new ResponseEntity<>(b, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(){
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }

    @GetMapping("/get-book-by-author/{author}")
    public Book getBookByAuthor(@PathVariable String name){
        for(Book book : bookList){
            String bookAuthor = book.getAuthor();
            if(bookAuthor.compareTo(name) == 0)
                return book;
        }
        return null;
    }

    @GetMapping("/get-book-by-genre/{genre}")
    public Book getBookByGenre(@PathVariable String genre){
        for(Book book : bookList){
            String bookGenre = book.getGenre();
            if(bookGenre.compareTo(genre) == 0)
                return book;
        }
        return null;
    }

    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable int id){
        for(int i=0; i<bookList.size(); i++){
            if(bookList.get(i).getId()==id){
                bookList.remove(i);
                return new ResponseEntity<>("success",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping ("/delete-all-books")
    public ResponseEntity deleteAllBooks(){
        bookList = new ArrayList<>();
        return new ResponseEntity<>("All books deleted", HttpStatus.OK);
    }

}
