package de.seproject.de;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

 /**
 * @author rajesh, georg
 * Create date 15.11.15
 *
 */

public class DatabaseConnection extends JFrame
{
	/**
	 * @param connection	Connection, database connection, used to create statements / execute sql statements!
	 * @param db_name		String, name of database
	 */
	Connection connection;
	String db_name; //latest db_name
	
	/**
	 * Constructor of the class DatabaseConnection,
	 * example database of MySQL called sakila is used as temporary db to check if connection is working
	 */
	public DatabaseConnection() 
	{
		this.db_name = "sakila";
	}
	
	//connection to Database:
	/**
	 * connection to database is established
	 * @param host		String, in this case "localhost:3306" (3306 is port)
	 * @param database	String, name of the database you want to connect to, at the beginning "sakila"
	 * @param user		String, name of the user, in this case "user"
	 * @param password	String, password, in this case "riazdh"
	 * @return 			boolean, true if connection was successful, otherwise false
	 */
	public boolean connectToDatabase (String host, String database, String user, String password)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionCommand = "jdbc:mysql://"+host+"/"+database+"?user="+user+"&password="+password;
			connection = DriverManager.getConnection(connectionCommand);
			System.out.println("Connection successful: jdbc:mysql://"+host+"/"+database+"?user="+user+"&password="+password);
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Connection failed!");
			return false;
		}
	}
	
	/**
	 * Create a database, using string as sql statement
	 * @param sql	String, sql statement that is executed
	 * @return		boolean, true if execution of statement was successful
	 */
	public boolean createDatabase(String sql){
		try {
			Statement s = connection.createStatement();
			String sql_statement = "CREATE DATABASE " + sql;
			s.execute(sql_statement);
			JOptionPane.showMessageDialog(DatabaseConnection.this, "Successfully created database "+sql, "Created Database", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Database creation successful");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(DatabaseConnection.this, "Failed to create database "+sql, "Created Database", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Database creation failed");
			return false;
		}
	}
	
	//creates new table points [id,x,y], lines does not work:
	/**
	 * one table called geometries is created, all types of geometries can be stored inside this table,
	 * attributes: ID of type integer, Type of type varchar [(p)oint,(l)ine,(r)ectangle], coordinates of type integer [X1/Y1, X2/Y2]
	 * @param sql_statement	String to create table
	 * @return no return
	 */
	public void createTable(){
		try {
			Statement s = connection.createStatement();
			String sql_statement = "";
			sql_statement = "CREATE TABLE GEOMETRIES ("
			        + " ID INTEGER PRIMARY KEY AUTO_INCREMENT,"
			        + " TYPE VARCHAR(255),"
			        + " X1 INTEGER NOT NULL,"
			        + " Y1 INTEGER NOT NULL,"
			        + " X2 INTEGER NOT NULL,"
			        + " Y2 INTEGER NOT NULL);";
			s.execute(sql_statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//check for entries in table, if no entries -> return of value 0, else count of entries using query SELECT COUNT(*) FROM POINTS, used to make sure unique ids are used
	/**
	 * method to get the number of entries and thereby derive the id
	 * @deprecated not used anymore, was created before primary key was auto_increment(ed)
	 * @param table	String, name of the table
	 * @return integer value of the highest ID-value
	 */
	public int getID(String table)
	{
		try {
			int count; //Count of table entries
			
			Statement s = connection.createStatement();
			String sql = "SELECT COUNT(*) FROM " + table;
			ResultSet rs= s.executeQuery(sql);
			if(rs.next()) //Expecting one row.
			{
			    count = rs.getInt(1);
			}
			else
				count = 0;
			
			JOptionPane.showMessageDialog(null, "Count:"+String.valueOf(count));
			
			return count;
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	// get all the point entries in the table and return them as one string: e.g. 1,5,5;2,10,15;3,21,35; [with id,x,y;]
	/**
	 * SQL statement is executed and then all the table entries are saved as one string
	 * @param sql	String of the sql statement that needs to be executed
	 * @return	String that includes all geometries in table, separated by ";", e.g. "id,type,x1,y1,x2,y2;id,type,x1,y1,x2,y2;..."
	 */
	public String getData (String sql)
	{
		try {
			String dbData = "";
			Statement s = connection.createStatement();
			if (s.execute(sql))
			{
				ResultSet rs = s.getResultSet();
				int z = rs.getMetaData().getColumnCount();
				while (rs.next())
				{
					for (int i = 1; i <= z; i++)
					{
						if (i != z)
						{
							dbData = dbData + rs.getString(i)+",";
							System.out.print(rs.getString(i)+", ");
						}
						else
						{
							dbData = dbData + rs.getString(i)+";";
							System.out.print(rs.getString(i)+"\n");
						}				
					}
				}
				return dbData;
			}
			else {
				System.out.println(s.getUpdateCount());
				return dbData;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	//insert one point in table points:
	/**
	 * Geometries are written in the database and inserted into table geometries
	 * @param sql	String, insert statement for the insertion of geometries in table
	 * @return		boolean, true if insertion was successful
	 */
	public boolean writeData(String sql)
	{
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	//delete one point from table points:
	/**
	 * Changes coordinates for geometries in table
	 * @param sql	String, update statement to change attributes in table or delete statement to delete entries
	 * @return		boolean, true if execution of statement was successful
	 */
	public boolean changeData(String sql)
	{
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
	}
	
}
