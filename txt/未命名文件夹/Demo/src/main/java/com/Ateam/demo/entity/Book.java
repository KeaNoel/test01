package com.Ateam.demo.entity;

public class Book  {

	private Integer id;

	private String bookName;

	private String Author;

	private Integer Numes;

	private String Price;

	private String Press;


	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public Integer getNumes() {
		return Numes;
	}

	public void setNumes(Integer numes) {
		Numes = numes;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getPress() {
		return Press;
	}

	public void setPress(String press) {
		Press = press;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", bookName=" + bookName + ", Author=" + Author + ", Numes=" + Numes + ", Price="
				+ Price + ", Press=" + Press + "]";
	}

}
