package com.ktech.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktech.entity.Book;
import com.ktech.service.BookService;

@RestController
public class BookRestController {

	@Autowired
	private BookService service;
	private String message;
	private Book bookById;
	private Book bookUpdate;

	@PostMapping(value = "/save", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<String> saveBook(@RequestBody Book book) {
		try {
			message = service.saveBook(book);
			if (message.contains("Book Already Exist")) {
				return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			System.out.println("Try Block " + message);
			return new ResponseEntity<String>(message, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());

			System.out.println("Exception  Block " + message);

			return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<Book>> getAllBook() {
		List<Book> allBook = service.getAllBook();
		if (allBook.size() < 1) {
			return new ResponseEntity<List<Book>>(allBook, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Book>>(allBook, HttpStatus.OK);
		}
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getSingleBook(@PathVariable long id) {
		try {
			bookById = service.getBookById(id);
			return new ResponseEntity<Book>(bookById, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Book>(bookById, HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<String> deleteSingleBook(@PathVariable long id) {
		try {
			message = service.deleteBook(id);
			if (message.contains("Book Not Exist")) {
				return new ResponseEntity<String>(message, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<String>(message, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{empId}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable Long empId) {
		System.out.println("my_book :-  " + book);
		try {
			bookUpdate = service.updateBook(book, empId);
			System.out.println("my_bookUpdate :-  " + bookUpdate);
			return new ResponseEntity<Book>(bookUpdate, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Exception :-  " + e.getMessage());
			return new ResponseEntity<Book>(bookUpdate, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
