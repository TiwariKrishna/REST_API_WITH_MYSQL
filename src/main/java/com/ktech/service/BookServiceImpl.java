package com.ktech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktech.entity.Book;
import com.ktech.repo.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bRepository;
	private String message;
	private Book saveBookUpdate;

	@Override
	public String saveBook(Book book) {
		Optional<Book> findByBookName = bRepository.findByBookName(book.getBookName());
		if (findByBookName.isPresent()) {
			message = "Book Already Exist";
		} else {
			message = "Book Save Successfully";
			bRepository.save(book);
		}
		return message;
	}

	@Override
	public Book getBookById(Long userId) {
		Optional<Book> findById = bRepository.findById(userId);
		return findById.get();
	}

	@Override
	public List<Book> getAllBook() {
		List<Book> findAll = bRepository.findAll();
		return findAll;
	}

	@Override
	public Book updateBook(Book book, Long empId) {
		Optional<Book> findById = bRepository.findById(empId);
		System.out.println("ID----FOR UPDATE--");
		System.out.println(findById);
		if (findById.isPresent()) {
			Book bookDetails = findById.get();
			bookDetails.setBookAutherName(book.getBookAutherName());
			bookDetails.setBookName(book.getBookName());
			bookDetails.setBookPrice(book.getBookPrice());
			bookDetails.setBookPublishDate(book.getBookPublishDate());
			saveBookUpdate = bRepository.save(bookDetails);
		}
		return saveBookUpdate;
	}

	@Override
	public String deleteBook(Long userId) {
		Optional<Book> findById = bRepository.findById(userId);
		if (findById.isPresent()) {
			bRepository.deleteById(userId);
			message = "Book Successfully Deleted";
		} else {
			message = "Book Not Exist";
		}
		return message;
	}
}
