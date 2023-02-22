package com.springjdbc.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.springjdbc.entity.Employee;

public class Main {
   
	//configure the database
	static JdbcTemplate jdbcTemplateOBJ;
	static SimpleDriverDataSource dataSourceOBJ;
	
	//1.creating a connection
	static String USERNAME ="root";
	static String PASSWORD="Pdmicro99@";
	static String URL="jdbc:mysql://localhost:3306/springjdbc";
	
	//establish a connection

	public static SimpleDriverDataSource getDatabaseConnection()
	{
		//2.inform the spring code about data base
		 dataSourceOBJ =new SimpleDriverDataSource();	
		 try {
			//3.setting driver class
			 dataSourceOBJ.setDriver(new com.mysql.cj.jdbc.Driver());
			 dataSourceOBJ.setUrl(URL);
			 dataSourceOBJ.setUsername(USERNAME);
			 dataSourceOBJ.setPassword(PASSWORD);
		 }catch(SQLException sqlException)
		 {
			 sqlException.printStackTrace();
		 }
		 return dataSourceOBJ;
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
	
	
		jdbcTemplateOBJ= new JdbcTemplate(getDatabaseConnection());
		//System.out.println("The Connection :-"+jdbcTemplateOBJ);
		
		if(null!=jdbcTemplateOBJ) 
		{
			//4.sql insert query
			String sqlinsetquery="Insert INTO Employee(name,email,address,phono)"
					+ "values(?,?,?,?)";
			for(int c=1;c<5;c++) {
				jdbcTemplateOBJ.update(sqlinsetquery, "Employee"+ c, "Employee"+c+"@greatlearning.in", "delhi","1111");
			}
			
			//5.sql update
	    String sqlUpdatequery="UPDATE Employee set email = ? where name=?";
	    jdbcTemplateOBJ.update(sqlUpdatequery, "admin@greatlearning.in","Employee2");
		
		//6.sql read- want to select some info
	String sqlSelectQuery="Select *from Employee";
		
		List listEmployee = jdbcTemplateOBJ.query(sqlSelectQuery, new RowMapper() {
			     public Employee mapRow(ResultSet result, int rowNum) throws SQLException{
			         Employee employeeObj = new Employee();
			         employeeObj.setName(result.getString("name"));
			         employeeObj.setEmail(result.getString("email"));
			         employeeObj.setAddress(result.getString("address"));                                               
			         employeeObj.setPhoneno(result.getString("phono"));
			         return employeeObj;
			}
		});
		System.out.println(listEmployee);

		//7.sql delete
		String sqlDeleteQuery="DELETE from Employee where name=?";
		jdbcTemplateOBJ.update(sqlDeleteQuery, "Employee1");
		
		}	
		
		//8.close the connection
		else {
			System.out.println("please check the connection");
		}
		
	}

}
