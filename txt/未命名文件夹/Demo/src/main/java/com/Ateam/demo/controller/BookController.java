package com.Ateam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ateam.demo.entity.Book;
import com.Ateam.demo.impl.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService=null;

	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	public List<Book> findAll() {
		System.out.print("图书信息列表");
		List<Book> bookList = bookService.findAll();
		for (Book book : bookList) {
			System.out.print("所有图书信息" + book);
		}
		return bookList;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//public String add(String bookName,String Author,String Price,String Press) {
	public String add(@RequestBody Book book) {
		int result = 0;
		System.out.println("id"+book.getId());
		System.out.println("添加图书");	
		result =  bookService.add(book);
		if(result >0) {
			return "添加成功";
		}else{
			return "添加失败！";
		}
		
	}
	
	
	@RequestMapping(value = "/delect", method = RequestMethod.POST)
	public String delect(@RequestBody Integer id) {
		System.out.println("删除图书");
		int  result = 0;
		result = bookService.delete(id);
		if(result > 0) {
			return "删除成功！";
		}else {
			return "删除失败！";
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update() {
		System.out.println("修改图书");
		return null;
	}
	
	/*
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(String bookName, String Author, String Price, String press, Model model) {
	*/
		/*
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BookServiceImpl dao = (BookServiceImpl) context.getBean("dao");
		Book book = new Book();
		book.setAuthor(Author);
		book.setBookName(bookName);
		book.setPress(press);
		book.setPrice(Price);
		boolean result = dao.add(book);
		if (result) {
			return "添加成功";
		} else {
			return "添加失败";
		}
		*/
		/*
		System.out.print("添加书籍");
		int books = bookService.add(book);
		if (books == 1) {
			return "添加成功";
		} else {
			return "添加失败";
		}
		*/

	

}
