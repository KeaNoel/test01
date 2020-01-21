package com.Ateam.demo.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.Ateam.demo.entity.Book;

@Repository("BookService")
public class BookServiceImpl implements BookService {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Override
	public List<Book> findAll() {
		String selectSql = "select * from book_table";
		return jdbcTemplate.query(selectSql, new BookMapper());
	}

	@Override
	public int add(Book book) {
		int result = 0;

		String addSql = "insert into book_table(id,bookName,Author,Numes,Price,Press) values(?,?,?,?,?,?)";
		
		 result =this.jdbcTemplate.update(addSql,new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, book.getId());
				ps.setString(2, book.getBookName());
				ps.setString(3, book.getAuthor());
				ps.setInt(4, book.getNumes());
				ps.setString(5, book.getPrice());
				ps.setString(6, book.getPress());
			}
			
		});
		 System.out.println("添加图书6"+result);
		return result;
	}
		
		//return jdbcTemplate.update(addSql,new BookMapper(),Integer.class);
		/*
		return jdbcTemplate.update(addSql,
				new Object[] { book.getBookName(), book.getAuthor(), book.getPrice(), book.getPress() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR }) == 1;*/
	

	@Override
	public int delete(Integer id) {
		int result = 0;
		String delSql = "delete from book_table where id = ?";
		//return jdbcTemplate.update(delSql, id) == 1;
		return result;
	}

	@Override
	public boolean update(Book book) {
		String updSql = "update book_table set bookName = ?,Author = ?,Numes = ?,Price = ?,Press =? where id = ?";
		Object stuObj = new Object[] { book.getBookName(), book.getAuthor(), book.getNumes(), book.getPrice(),
				book.getPress(), book.getId() };
		return jdbcTemplate.update(updSql, stuObj) == 1;
	}
	
	
	class BookMapper implements RowMapper<Book> {

		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			Book book = new Book();
			book.setId(rs.getInt(1));
			book.setBookName(rs.getString(2));
			book.setAuthor(rs.getString(3));
			book.setNumes(rs.getInt(4));
			book.setPress(rs.getString(5));
			book.setPrice(rs.getString(6));

			return book;
		}
	}
	

	/*
	 * @Override public void findAll() { String selectSql =
	 * "select * from book_table"; RowMapper<Book> rowMapper = new
	 * BeanPropertyRowMapper<Book>(Book.class); List<Book> list =
	 * jdbcTemplate.query(selectSql,rowMapper,null); System.out.println("查询所有图书信息");
	 * for(Book book :list) { System.out.println(book); }
	 * 
	 * }
	 * 
	 * @Override public void add() { String insertSql =
	 * "insert into book_table values(?,?,?,?,?,?)"; for (int i=0;i<15;i++){
	 * 
	 * Object paremOne[] = {1,"书名","作者",1,"价格","出版社"};
	 * 
	 * jdbcTemplate.update(insertSql,paremOne);
	 * 
	 * } System.out.println("添加图书信息成功！");
	 * 
	 * }
	 * 
	 * @Override public void delete() { String deleteSql =
	 * "delete from book_table where id=?";
	 * 
	 * Object paremTwo[] = {5};
	 * 
	 * jdbcTemplate.update(deleteSql,paremTwo);
	 * 
	 * System.out.println("UserDao中的删除功能实现了");
	 * 
	 * }
	 * 
	 * @Override public void update() { String updateSql =
	 * "update book_table set bookName=? , Author=?, Numes=?, Price=?, Press=? where id =?"
	 * ;
	 * 
	 * Object paremThree[] = {"修改书名","修改作者",1,"修改价格","修改出版社",3};
	 * 
	 * jdbcTemplate.update(updateSql,paremThree);
	 * 
	 * System.out.println("UserDao中的修改功能实现了");
	 * 
	 * }
	 */

}
