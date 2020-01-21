package com.Ateam.demo.impl;

import java.util.List;

import com.Ateam.demo.entity.Book;

public interface BookService {
	public List<Book> findAll();

	public int add(Book book);

	public int delete(Integer id);

	public boolean update(Book book);


}
