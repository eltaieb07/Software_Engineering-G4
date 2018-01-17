import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.sql.*;

/**
 * 
 * @author Shahab
 *
 */
public class DatabaseConnectivity {
	
	/**
	 * it is used to store the host where the database is hosted e.g. localhost
	 */
	String host="jdbc:postgresql://127.0.0.1:5432/java_db";
	/**
	 * it stores the User name of the database
	 */
	String name="postgres";
	/**
	 * it stores the password to connect to the database
	 */
	String password="postgres";
	/**
	 * The Connection object to store the connection retrieved by DriverManager
	 */
	Connection con = null;
	/**
	 * It is used to create a prepared Statement for INSERT shapes to database table
	 */
	PreparedStatement stm = null;
	/**
	 * It is used to create a statement object to SELECT shapes from database table
	 */
	Statement st = null;
	
	
	
	/**
	 * 
	 * @param host string representing the hostname of the database
	 * @param username string representing the Username of that database
	 * @param password string to represent the Password for that Username
	 */
	DatabaseConnectivity(String host, String username , String password){
		this.host = host;
		this.name = username;
		this.password = password;
	}
	
	/**
	 * testConnection() function tests the connection with the provided credentials
	 * @return returns true if connection established otherwise returns false
	 */
	public boolean testConnection(){
		try{
			con = DriverManager.getConnection(host,name,password);
			con.close();
			return true;
		}
		catch(Exception e){
			System.out.println("Failed to connect!");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * saveToDb() function reads all the shapes from the shapes array list and saves them in their respective tables in the database
	 */
	public void saveToDb(){
		int poly_count = 0 , rect_count=0 , line_count=0 , point_count=0 ;
		if(!testConnection()){
			return;
		}
		
		int type = 0;
		ArrayList<Double> temp = new ArrayList<>();


		double [] val = new double[6];
		for (ShapeItems s: MyPanel.shapes){
			if (s.getName().equals("point")){
				Point p = s.getPoint();
				temp.add(p.getX());
				temp.add(p.getY());
				if (point_count==0){
					try{
						con = DriverManager.getConnection(host,name,password);
						st = con.createStatement();
						st.executeUpdate("TRUNCATE point");
						System.out.println("TRUNCATE POINT DONE!");
						st.close();
						con.close();
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						System.out.println("truncate Problem");
					}
				}
				
				try{
					con = DriverManager.getConnection(host,name,password);

					stm = con.prepareStatement("INSERT INTO point (name, color, geometry) "+" VALUES (?,?,?)");

					stm.setString(1, s.getName());
					int rp = s.getColor().getRed();
					int gp = s.getColor().getGreen();
					int bp = s.getColor().getBlue();
					
					String color = Integer.toString(rp)+","+Integer.toString(gp)+","+Integer.toString(bp);
					
					stm.setString(2, color);
					//System.out.println(color);
					Double[] myArray = new Double[temp.size()];
					myArray = temp.toArray(myArray);
					Array arr = con.createArrayOf("float4",myArray);
					stm.setArray(3, arr);
					
					stm.executeUpdate();
					//System.out.println("executed");
					stm.close();
					con.close();
					arr.free();
					temp.clear();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println("Problem");
				}
				point_count = point_count+1;
			}
			else
				if (s.getName().equals("line")){
				Line2D.Double l = (java.awt.geom.Line2D.Double) s.getShape();
				
				temp.add(l.getP1().getX());
				temp.add(l.getP1().getY());
				temp.add(l.getP2().getX());
				temp.add(l.getP2().getY());
				if (line_count==0){
					try{
						con = DriverManager.getConnection(host,name,password);
						st = con.createStatement();
						st.executeUpdate("TRUNCATE line");
						System.out.println("TRUNCATE LINE DONE!");
						st.close();
						con.close();
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						System.out.println("truncate Problem");
					}
				}
				
				try{
					con = DriverManager.getConnection(host,name,password);

					stm = con.prepareStatement("INSERT INTO line (name, color, geometry) "+" VALUES (?,?,?)");

					stm.setString(1, s.getName());
					int rl = s.getColor().getRed();
					int gl = s.getColor().getGreen();
					int bl = s.getColor().getBlue();
					
					String color = Integer.toString(rl)+","+Integer.toString(gl)+","+Integer.toString(bl);
					
					stm.setString(2, color);
					//System.out.println(color);
					Double[] myArray = new Double[temp.size()];
					myArray = temp.toArray(myArray);
					Array arr = con.createArrayOf("float4",myArray);
					stm.setArray(3, arr);
					
					stm.executeUpdate();
					//System.out.println("executed");
					stm.close();
					con.close();
					arr.free();
					temp.clear();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println("Problem");
				}
				line_count = line_count+1;

			}
			else

			if (s.getName().equals("rectangle")){
				Rectangle2D.Double r = (Rectangle2D.Double) s.getShape();

				temp.add(r.x);
				temp.add(r.y);
				temp.add(r.getWidth());
				temp.add(r.getHeight());
				
				if (rect_count==0){
					try{
						con = DriverManager.getConnection(host,name,password);
						st = con.createStatement();
						st.executeUpdate("TRUNCATE rectangle");
						System.out.println("TRUNCATE RECTANGLE DONE!");
						st.close();
						con.close();
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						System.out.println("truncate Problem");
					}
				}
				
				try{
					con = DriverManager.getConnection(host,name,password);

					stm = con.prepareStatement("INSERT INTO rectangle (name, color, geometry) "+" VALUES (?,?,?)");

					stm.setString(1, s.getName());
					int rr = s.getColor().getRed();
					int gr = s.getColor().getGreen();
					int br = s.getColor().getBlue();
					
					String color = Integer.toString(rr)+","+Integer.toString(gr)+","+Integer.toString(br);
					stm.setString(2, color);
					//System.out.println(color);
					Double[] myArray = new Double[temp.size()];
					myArray = temp.toArray(myArray);
					Array arr = con.createArrayOf("float4",myArray);
					stm.setArray(3, arr);
					/*if(stm.execute()){
						
						System.out.println("executeable");
					}
					else{
						System.out.println("Not exe");
					}*/
					
					
					stm.executeUpdate();
					//System.out.println("executed");
					stm.close();
					con.close();
					arr.free();
					temp.clear();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println("Problem");
				}
				rect_count = rect_count+1;
			}
			else
			if (s.getName().equals("polygon")){
				
				if (poly_count==0){
					try{
						con = DriverManager.getConnection(host,name,password);
						st = con.createStatement();
						st.executeUpdate("TRUNCATE polygon");
						System.out.println("TRUNCATE POLYGON DONE!");
						st.close();
						con.close();
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						System.out.println("truncate Problem");
					}
				}
				
				PathIterator pi = s.getShape().getPathIterator(null, 0);
				//bufferedWriter.write("polygon" + ',');
				while(!pi.isDone()){
					type = pi.currentSegment(val);
					if (type == PathIterator.SEG_MOVETO) {
						//System.out.println("move to:"+val[0] +" , " +val[1]);
						//bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');
						temp.add(val[0]);
						temp.add(val[1]);

					}
					else if (type == PathIterator.SEG_LINETO) {
						//System.out.println("line to:"+val[0] +" , " +val[1]);
						//bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');
						temp.add(val[0]);
						temp.add(val[1]);

					}
					else if (type == PathIterator.SEG_CLOSE) {
						//System.out.println("close:"+val[0] +" , " +val[1]);
						//bufferedWriter.write(Double.toString(val1) + ',' + Double.toString(val2));
						//temp.add(val[0]);
						//temp.add(val[1]);
					}

					pi.next();
					

				}
				//bufferedWriter.newLine();
				
				
				try{
					con = DriverManager.getConnection(host,name,password);

					stm = con.prepareStatement("INSERT INTO polygon (name, color, geometry) "+" VALUES (?,?,?)");

					stm.setString(1, s.getName());
					int rpo = s.getColor().getRed();
					int gpo = s.getColor().getGreen();
					int bpo = s.getColor().getBlue();
					
					String color = Integer.toString(rpo)+","+Integer.toString(gpo)+","+Integer.toString(bpo);
					
					
					stm.setString(2, color);
					//System.out.println(color);
					Double[] myArray = new Double[temp.size()];
					myArray = temp.toArray(myArray);
					Array arr = con.createArrayOf("float4",myArray);
					stm.setArray(3, arr);

					
					stm.executeUpdate();
					stm.close();
					con.close();
					arr.free();
					temp.clear();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println("Connection is unsuccessful");
				}
				finally{
					//System.out.println("Connected...");
					
				}
				//System.out.println("executeable");
				poly_count = poly_count+1;
			}
		}
		rect_count=0;
		poly_count=0;
		line_count=0;
		point_count=0;
		
	}
	
	
	/**
	 * The function loadDb() is used to retrieve all the shapes from their respective table in the database, and store them in shapes array list
	 */
	public void loadDb(){
		
		if(!testConnection()){
			return;
		}
		
		try{
			con = DriverManager.getConnection(host,name,password);
			st = con.createStatement();
			String sql = "Select * from point";
			
			ResultSet rs = st.executeQuery(sql); 
			if (rs!=null){
				while (rs.next()) {						//start loop to save results
				
					System.out.println(rs.getString("name"));
					//System.out.println(rs.getInt("color"));
					String [] color = rs.getString("color").toString().split(",");
					Array arr = rs.getArray("geometry");
					System.out.println(arr);
					Integer [] s = (Integer[])arr.getArray();

					MyPanel.shapes.add(new ShapeItems (rs.getString("name") , new Point(s[0], s[1]), new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2]))));
				}

		    }
			else{
				stm.close();
				con.close();
			}
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("Problem in Point");
		}

		
		try{
			con = DriverManager.getConnection(host,name,password);
			st = con.createStatement();
			String sql = "Select * from line";
			
			ResultSet rs = st.executeQuery(sql); 
			if (rs!=null){
				while (rs.next()) {						//start loop to save results
				
					System.out.println(rs.getString("name"));
					//System.out.println(rs.getInt("color"));
					String [] color = rs.getString("color").toString().split(",");
					Array arr = rs.getArray("geometry");
					System.out.println(arr);
					Double [] s = (Double[])arr.getArray();

					MyPanel.shapes.add(new ShapeItems (rs.getString("name") , new Line2D.Double(s[0], s[1], s[2], s[3]), new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2]))));
				}

		    }
			else{
				stm.close();
				con.close();
			}
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("Problem in Line");
		}

		
		try{
			con = DriverManager.getConnection(host,name,password);
			st = con.createStatement();
			String sql = "Select * from rectangle";
			
			ResultSet rs = st.executeQuery(sql); 
			if (rs!=null){
				while (rs.next()) {						//start loop to save results
				
					System.out.println(rs.getString("name"));
					//System.out.println(rs.getInt("color"));
					String [] color = rs.getString("color").toString().split(",");
					Array arr = rs.getArray("geometry");
					System.out.println(arr);
					Double [] s = (Double[])arr.getArray();

					MyPanel.shapes.add(new ShapeItems (rs.getString("name") , new Rectangle2D.Double(s[0], s[1], s[2], s[3]), new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2]))));
				}

		    }
			else{
				stm.close();
				con.close();
			}
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("Problem in Rectangle");
		}

		try{
			con = DriverManager.getConnection(host,name,password);
			st = con.createStatement();
			String sql = "Select * from polygon";
			
			ResultSet rs = st.executeQuery(sql); 
			if (rs!=null){
				while (rs.next()) {						//start loop to save results
					System.out.println();
					System.out.println(rs.getString("name"));
					//System.out.println(rs.getString("color"));
					String [] color = rs.getString("color").split(",");
					
					
					Array arr = rs.getArray("geometry");
					System.out.println(arr);
					//System.out.println(arr.getArray().toString().length());
					Double [] s = (Double[])arr.getArray();
					
					Path2D.Double path = new Path2D.Double();
					//System.out.println(s.length);
					path.moveTo(s[0], s[1]);
					for (int i=2 ; i<=s.length; i+=2){
	
						if (i==s.length){
							path.closePath();
							break;
						}
						path.lineTo(s[i],s[i+1]);
						
					}
					MyPanel.shapes.add(new ShapeItems(rs.getString("name") , path , new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2]))));
	
			    }
			}
			else{
				//st.executeUpdate(sql1);
				stm.close();
				con.close();
			}
			
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("Problem in Polygon");
		}

	}
	
}
