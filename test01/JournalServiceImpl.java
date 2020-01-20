package com.ateam.Journal.Impl;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ateam.Journal.entity.Journal;

@Service
public class JournalServiceImpl implements JournalService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String getFormat(String start,String end) {
		String format = "YYYY-MM-DD HH24:MI:SS";
		
        try {
        	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		// 开始时间
		Date startTime = df.parse(start);
		// 结束时间		
		Date endTime = df.parse(end);

		long count = (endTime.getTime() - startTime.getTime())/1000;
		//System.out.println(count);			
						
			if(getSecond(startTime,"Min") <= count) {
				
				if(getSecond(startTime,"H") <= count) {
					
					if(getSecond(startTime,"D") <= count) {
						
						if(getSecond(startTime,"M") <= count) {
								
							if(getSecond(startTime,"Y") <= count) {
								
								format = "YYYY-MM";										
								
							}else {format = "YYYY-MM-DD";}
							
						}else {format = "YYYY-MM-DD HH24:00";}
						
					}else {format = "YYYY-MM-DD HH24:MI";}
					
				}
				
			}

        } catch (Exception e) {
           // System.out.println(e);
        }
        
       return format;
    }
	
	//获得秒数
		private long getSecond(Date startTimestamp, String dateType) {		
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTimestamp);
			long result = 0;
			switch (dateType) {
			case "Y":
				cal.add(Calendar.YEAR, 1);
				result = (cal.getTime().getTime() - startTimestamp.getTime()) / 1000;
				break;
			case "M":
				cal.add(Calendar.MONTH, 1);
				result = (cal.getTime().getTime() - startTimestamp.getTime()) / 1000;
				break;
			case "D":
				result = 86400;
				break;
			case "H":
				result = 3600;
				break;
			case "Min":
				result = 60;
				break;

			}
			return result;
		}
		
		//最大显示值
		private List<Journal> maxDisPlayValues(List<Journal> oldlist,int count) {	
			 List<Journal> newList = new ArrayList();
			 
			 try {
			 
			 if(oldlist.size()> count && count !=0) {
				 int length = (int) Math.round((oldlist.size() / count)+0.5);
				 for(int i=0;i <oldlist.size();i++) {
					 if(i% length ==0) {
						 newList.add(oldlist.get(i));
					 }
				 }
			 }
			 }catch(Exception e){
				 System.out.print(e.getMessage());
			 }
			 if(newList !=null && !newList.isEmpty()) {
				 return newList;
			 }else {
				 return oldlist;
			 }
		}
	public List<Journal> maxDisPlayValues(Journal jcp){
		List<Journal> result= null;
		int count=jcp.getMaxdisplayvalues();
		List<Journal> maxDisPlayValues =maxDisPlayValues(result,count);
      return maxDisPlayValues;
	}
		

	@Override
	public List<Journal> JournalCurvePicture(Journal jcp) {	
			
		String starts =String.valueOf(jcp.getArecd1Start());
		
		String end =String.valueOf(jcp.getArecd1Stop());
		
		String getFormat = getFormat(starts,end);
				
		System.out.println("test");
		String sql = "";
		List<Journal> result = null;
		if (jcp.getArecd1Start() != null && jcp.getArecd1Stop() != null && jcp.getArecd2() != null) {// &&
																										// jcp.getMaxdisplayvalues()
																										// != null) {
			
			System.out.println("test3");
			System.out.println("请求的数据字段为：" + "" + jcp.getArecd1Start() + "" + "" + jcp.getArecd1Stop() + "" + ""
					+ jcp.getArecd2() + "");
//			sql = "SELECT \n" + " CAST(arecd4->'ai0' AS CHARACTER(10))as ai0,\n"
//					+ " CAST(arecd4->'di1' AS CHARACTER(2))as di1,\n" + " CAST(arecd4->'ai2' AS CHARACTER(5))as ai2,\n"
//					+ " CAST(arecd4->'di0status' AS CHARACTER(6))as di0,\n"
//					+ " CAST(arecd4->'di1status' AS CHARACTER(2))as di1,\n"
//					+ " CAST(arecd4->'i2c0' AS CHARACTER(5))as i2c0,\n"
//					+ " CAST(arecd4->'i2c1' AS CHARACTER(5))as i2c1\n"
//					+ " from arecd where arecd1 BETWEEN ? and ? and arecd2=?";
			
			sql = "SELECT arecd1,arecd2,AVG(CAST(CAST(ai0 AS text)AS numeric))ai0,AVG(CAST(CAST(ai1 AS text)AS numeric))ai1,AVG(CAST(CAST(ai2 AS text)AS numeric))ai2,\n" + 
					"AVG(di0)di0,AVG(di1)di1,AVG(CAST(CAST(i2c0 AS text) AS numeric)) i2c0,AVG(CAST(CAST(i2c1 AS text)AS numeric)) i2c1 \n" + 
					"FROM( \n" + 
					"SELECT to_char(arecd1,?)arecd1,arecd2,arecd4->'ai0'AS ai0,arecd4->'ai1' AS ai1,arecd4->'ai2'AS ai2,arecd4 ->'i2c0'AS i2c0,arecd4->'i2c1'AS i2c1, \n" + 
					"case when(arecd4 ->'di0status')='true'  then 1 else 0 end AS di0,\n" + 
					"case when(arecd4 ->'di1status')='true'  then 1 else 0 end AS di1\n" +
					"\n"+
					"FROM ARECD WHERE arecd1 BETWEEN ? AND ? AND arecd2 = ?\n"+
					")A GROUP BY arecd1,arecd2"+
					" ORDER BY arecd1;\n";

			// 响应部分
			RowMapper<Journal> rows = new RowMapper<Journal>() {

				@Override
				public Journal mapRow(ResultSet rs, int rowNum) throws SQLException {
					Journal journal = new Journal();
					journal.setArecd1Start(rs.getTimestamp("arecd1"));
					journal.setArecd1Stop(rs.getTimestamp("arecd1"));
					journal.setAi0(rs.getString("ai0"));
					journal.setDi1(rs.getString("di1"));
					journal.setAi2(rs.getString("ai2"));
					journal.setDi0(rs.getString("di0"));
					journal.setDi1status(rs.getString("di1"));
					journal.setI2c0(rs.getString("i2c0"));
					journal.setI2c1(rs.getString("i2c1"));
					return journal;
				}

			};
			
			// 请求
			result = this.jdbcTemplate.query(sql, new PreparedStatementSetter() {					
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, getFormat);//时间区间
					ps.setTimestamp(2, jcp.getArecd1Start());// 开始时间
					ps.setTimestamp(3, jcp.getArecd1Stop());// 结束时间
					ps.setString(4, jcp.getArecd2());// 设备编号	
				   //ps.setInt(5, maxDisPlayValues(null, 0));// 最大显示值
				   ps.setArray(5, (Array) maxDisPlayValues(null,0));
				}

			}, rows);
		}
		return result;
	}

}
