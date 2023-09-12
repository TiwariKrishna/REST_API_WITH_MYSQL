package com.ktech.service;

import java.util.List;

import com.ktech.entity.Book;

public interface BookService {

	public String saveBook(Book book);

	public Book getBookById(Long userId);

	public List<Book> getAllBook();

	public Book updateBook(Book book,Long id);

	public String deleteBook(Long userId);
}
