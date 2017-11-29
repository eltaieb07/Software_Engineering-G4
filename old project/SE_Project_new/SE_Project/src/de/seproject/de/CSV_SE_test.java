package de.seproject.de;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * @author mohamed, georg
 * Create date 25.10.15
 *
 */

public class CSV_SE_test {
	
	/**
	 * imported CSV file is read and the geometries inside this file are inserted line by line into table geometries
	 * @param filename	String, name of the csv file
	 * @param db		DatabaseConnection object, needed to write geometries in database / insert into table geometries
	 * @return			true, if reading of the file was successful
	 */
	public static boolean readFile(String filename, DatabaseConnection db){
		try{
			
			//new BufferedReader, used to "read" csv file:
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line = "";
			String sql = "";
			
			//csv file is read line by line:
			while (br.ready()){
				line = br.readLine();
				//line is separated into attributes: ID,type,x1,y1,x2,y2 (x2,y2 only for lines and points):
				String[] tokens = line.split(",");
				//Define sql statement for the insertion into database:
				//points:
				if (tokens[0].equals("p")) {
					sql = "INSERT INTO GEOMETRIES " +
							"VALUES (NULL, 'p', " + tokens[1] + "," + tokens[2] + ", -999, -999);";
				}
				//lines:
				else if (tokens[0].equals("l")) {
					sql = "INSERT INTO GEOMETRIES " +
							"VALUES (NULL, 'l', " + tokens[1] + "," + tokens[2] + ", " + tokens[3] + ", " + tokens[4] + ");";
				}
				//rectangles:
				else if (tokens[0].equals("r")) {
					sql = "INSERT INTO GEOMETRIES " +
							"VALUES (NULL, 'r', " + tokens[1] + "," + tokens[2] + ", " + tokens[3] + ", " + tokens[4] + ");";
				}
				
				//Execution of sql statement:
				System.out.println(sql);
				db.writeData(sql);
			}
			
			//Save all geometries in database as string, used for the drawing in canvas:
			String dbData = db.getData("SELECT * FROM GEOMETRIES");
			Application.canvas.drawCommand = dbData;
			Application.canvas.checkPoints = true;
			Application.canvas.repaint();
			System.out.println(dbData);
			return true;
		
		//if import was not successful:
		}catch (IOException e){
			JOptionPane.showMessageDialog(null, "Error reading file:"+filename);
			return false;
		}
	}
	
	/**
	 * export geometries in database into csv file
	 * @param filename	String, name of the file that is created, if file exists, content is overwritten
	 * @param db		DatabaseConnection object, needed to get geometries of database to write them into file
	 * @return			true, if writing of the file was successful
	 */
	public static boolean writeFile(String filename, DatabaseConnection db){
		try{
			/** Java-FileWriter to write a file*/
			FileWriter writer = new FileWriter(filename);
			/**
			 * Java-BufferedWriter is to store and then write element to file path
			 */
			BufferedWriter bw = new BufferedWriter(writer);
			
			//Get all the rows and columns in the database as one string:
			String dbData_Points = db.getData("SELECT * FROM GEOMETRIES");
			
			//if there is at least one entry:
			if (dbData_Points != "") {
				
				//Split the single string into the different rows:
				String[] dbData_Points_Entries = dbData_Points.split(";");
				
				//Get count of table entries:
				int count_points_entries = dbData_Points_Entries.length;
				
				//Writing into CSV-File:
				for (int i = 0; i < count_points_entries; i++) {
					
					//Split rows into different columns:
					String[] dbData_Points_Rows = dbData_Points_Entries[i].split(",");
					
					//Write to file, with same layout as input csv-files:
					if (dbData_Points_Rows[1].equals("p")){
						bw.write(dbData_Points_Rows[1] + "," + dbData_Points_Rows[2]+","+dbData_Points_Rows[3]);
					} else {
						bw.write(dbData_Points_Rows[1] + "," + dbData_Points_Rows[2]+","+dbData_Points_Rows[3] + "," + dbData_Points_Rows[4]+","+dbData_Points_Rows[5]);
					}
					bw.newLine();
					bw.flush();
				}
				bw.close(); //close BufferedWriter
			}
			
			
			return true;
			
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Opens FileChooser to select csv file for import or export
	 * @return	String, name of the file that is imported or exported
	 */
	public static String openFileChooser(){
		//at first choose a file, s.JFileChooseDemo
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {
			@Override public boolean accept (File f){
				return f.isDirectory()  ||
					f.getName().toLowerCase().endsWith(".csv");
			}
			@Override public String getDescription(){
				return "Texte (*.csv)";
			}
		});
		int state = fc.showOpenDialog(null);
		if(state == JFileChooser.APPROVE_OPTION){
			String filename = fc.getSelectedFile().getAbsolutePath();
			return filename;
		}
		else {
			JOptionPane.showMessageDialog(null, "selection canceled");
			return "no File";
		}
		
	}
}