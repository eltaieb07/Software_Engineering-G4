package de.seproject.de;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.mysql.jdbc.Statement;
/**
 * @author georg
 *
 */
public class Functionality extends Canvas implements MouseListener, MouseMotionListener {
	
		/**
		 * drawCommand is string, stores all the geometries in database (drawn black)
		 * drawSelect is string, stores all selected geometries (drawn blue)
		 */
		String drawCommand = "";		// String to save geometries in database
		String drawSelect = "";			// String to save selected geometries from database
		String db_name="";				// String to save name of database
		
		// Variable to save the coordinates of the mouse within the corresponding listeners, used to calculate new coordinates to draw, select, move and delete:
		int x_clicked, y_clicked, x_released, y_released, x_pressed, y_pressed, x_dragged, y_dragged, x_moved, y_moved, x1, x2, y1, y2, x_min, y_min, width, height;
		
		// (Boolean) Control variables, used for if-Clause to get the right cases and thereby guarantee that everything is drawn the right way:
		boolean checkPoints, checkSelect, checkRectangle, checkLine_dragged, checkRectangle_dragged;
		
		/** 
		 * Constructor of the class functionality
		 * @param drawCommand				String with all the geometries in database, used to control the drawing of the geometries
		 * @param checkPoints				boolean control variable to check if points or other geometries are in database, used to control the drawing of the geometries
		 * @param checkSelect				boolean control variable to check if geometries are selected
		 * @param checkRectangle			boolean control variable to check if selection rectangle needs to be drawn
		 * @param checkLine_dragged			boolean control variable to check if line is drawn
		 * @param checkRectangle_dragged	boolean control variable to check if rectangle is drawn
		 */
		public Functionality () {
			String drawCommand = "";
	        setBackground (Color.WHITE);
	        setSize(900, 715);				//define size
	        setBounds(118, 0, 900, 715);	//define bounds
	        addMouseMotionListener(this);	//add MouseMotionListener to canvas
	        addMouseListener(this);			//add MouseListener to canvas
	        checkPoints = false;			//default values for boolean control variables false at the beginning!
	        checkSelect = false;
	        checkRectangle = false;
	        checkLine_dragged = false;
	        checkRectangle_dragged = false;
	    } //end of constructor
		
		/**
		 * override the paint method, needed to draw the different geometries in the canvas
		 * @param g	Graphics object
		 *
		 */
		@Override
		public void paint(Graphics g) {			

			if (checkPoints == true) {
				String[] geometryEntryArray = drawCommand.split(";");			//Split string drawCommand -> get every single geometry in database as separate string
				int countEntries = geometryEntryArray.length;					//Count number of entries
				
				for (int i=0; i<countEntries; i++) {							//beginning of for loop
					Graphics2D g2D = (Graphics2D) g;
					g2D.setColor(Color.black);
					String[] geometryData = geometryEntryArray[i].split(",");	//Split string into attribute parts (ID, Type, ...)
					
					//Check for geometry type (Field TYPE -> geometryData[1]):
					//Points:
					if (geometryData[1].equals("p")) {
						x1 = Integer.valueOf(geometryData[2]);
						y1 = Integer.valueOf(geometryData[3]);
						g2D.drawOval(x1, y1, 10, 10);
					//Lines:
					} else if (geometryData[1].equals("l")) {
						x1 = Integer.valueOf(geometryData[2]);
						y1 = Integer.valueOf(geometryData[3]);
						x2 = Integer.valueOf(geometryData[4]);
						y2 = Integer.valueOf(geometryData[5]);
						g2D.drawLine(x1, y1, x2, y2);
					//Rectangles:
					} else if (geometryData[1].equals("r")) {
						//Calculate width and height from both pairs of coordinates (X1/Y1 and X2/Y2) to draw rectangle:
						int width = Integer.valueOf(geometryData[4]) - Integer.valueOf(geometryData[2]);
						int height = Integer.valueOf(geometryData[5]) - Integer.valueOf(geometryData[3]);
						g2D.drawRect(Integer.valueOf(geometryData[2]), Integer.valueOf(geometryData[3]), width, height);
					}
				}																//end of for loop
				
			}
			
			//Drawing of a line -> therefore using the coordinates when the mouse is pressed as well as the last values of the dragged mouse:
			if (checkLine_dragged == true) {
				Graphics2D g2D = (Graphics2D) g;
				g2D.drawLine(x_pressed, y_pressed, x_dragged, y_dragged);
			}
			
			//Drawing of a rectangle -> similar to line drawing, additionally the width and height are calculated from the coordinate pairs:
			if (checkRectangle_dragged == true) {
				Graphics2D g2D = (Graphics2D) g;
				String drawData = this.getDrawData(x_pressed, y_pressed, x_dragged, y_dragged);		//call method getDrawData -> checks coordinate pairs and "sorts" the values!
				String[] drawDataArray = drawData.split(",");
				x_min = Integer.parseInt(drawDataArray[0]);
				y_min = Integer.parseInt(drawDataArray[1]);
				width = Integer.parseInt(drawDataArray[2]);
				height = Integer.parseInt(drawDataArray[3]);
				g2D.drawRect(x_min, y_min, width, height);
			}
			
			//Drawing of rectangle for selection:
			if (checkRectangle == true) {
				String drawData = this.getDrawData(x_pressed, y_pressed, x_dragged, y_dragged);
				String[] drawDataArray = drawData.split(",");
				x_min = Integer.parseInt(drawDataArray[0]);
				y_min = Integer.parseInt(drawDataArray[1]);
				width = Integer.parseInt(drawDataArray[2]);
				height = Integer.parseInt(drawDataArray[3]);
				
				Graphics2D g2D = (Graphics2D) g;
				g2D.drawRect(x_min, y_min, width, height);
			}
			
			//Re-draw selected geometries with blue color to show that they are selected:
			if (checkSelect == true) {
				String[] selectEntryArray = drawSelect.split(";");
				int countEntries = selectEntryArray.length;					//get count of selected geometries
				
				for (int i=0; i<countEntries; i++) {						//beginning of for-loop
					//Graphics g = Application.canvas.getGraphics();
					Graphics2D g2D = (Graphics2D) g;
					g2D.setColor(Color.blue);								//change color of drawn geometries from black to blue
					String[] selectedData = selectEntryArray[i].split(",");
					//selected points -> filled ovals
					if (selectedData[1].equals("p")) {
						x1 = Integer.valueOf(selectedData[2]);
						y1 = Integer.valueOf(selectedData[3]);
						g2D.fillOval(x1, y1, 10, 10);
					//selected lines -> blue lines:
					} else if (selectedData[1].equals("l")) {
						x1 = Integer.valueOf(selectedData[2]);
						y1 = Integer.valueOf(selectedData[3]);
						x2 = Integer.valueOf(selectedData[4]);
						y2 = Integer.valueOf(selectedData[5]);
						g2D.drawLine(x1, y1, x2, y2);
					//selected rectangles -> filled rectangles:
					} else {
						x1 = Integer.valueOf(selectedData[2]);
						y1 = Integer.valueOf(selectedData[3]);
						x2 = Integer.valueOf(selectedData[4]);
						y2 = Integer.valueOf(selectedData[5]);
						String rectData = this.getDrawData(x1, y1, x2, y2);
						String[] rectDataArray = rectData.split(",");
						g2D.fillRect(Integer.parseInt(rectDataArray[0]), Integer.parseInt(rectDataArray[1]), Integer.parseInt(rectDataArray[2]), Integer.parseInt(rectDataArray[3]));						
					}
				}															//end of for-loop
			}
			
		} //end of paint()
		
		//Method so check coordinate pairs (for rectangle) to sort them -> find min and max values to calculate width and height:
		/**
		 * Method to check coordinate pairs, find min-, max-values and width/height for rectangles
		 * @param x1	integer value of x1-coordinate
		 * @param y1	integer value of y1-coordinate
		 * @param x2	integer value of x2-coordinate
		 * @param y2	integer value of y2-coordinate
		 * @return string of the integer-values seperated by "," -> x_min,y_min,width,height
		 */
		public String getDrawData(int x1, int y1, int x2, int y2) {
			int x_min, y_min, width, height;
			if (x1 < x2) {
				x_min = x1;
				width = x2 - x1;
			} else {
				x_min = x2;
				width = x1 - x2;
			}
			if (y1 < y2) {
				y_min = y1;
				height = y2 - y1;
			} else {
				y_min = y2;
				height = y1 - y2;
			}
			String drawData = Integer.toString(x_min) + "," + Integer.toString(y_min) + "," + Integer.toString(width) + "," + Integer.toString(height);
			return drawData;	//String returned, contains both min values and width and height, separated by ","!
		} //end of getDrawData-method

		/*Beginning of Listeners:
		 * (non-Javadoc)
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 * Structure:
		 * If-Clauses to select the right case and thereby get right values for control variables
		 * In the end repaint() is called -> canvas is re-drawn by paint() method!
		 */
		/**
		 * mouseDragged-listener, used to get coordinates for the drawing of rectangles and lines,
		 * changes necessary control variables
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			//get coordinates when mouse is dragged -> used to draw lines and rectangles (as well as selection rectangle):
			x_dragged = e.getX();
			y_dragged = e.getY();
			
			//Coordinates saved as text of two text fields -> show coordinates in GUI:
			Application.lblXCoordinate.setText(Integer.toString(x_dragged));
			Application.lblYCoordinate.setText(Integer.toString(y_dragged));
			
			// If-Clause to check if "select" or "delete" is activated,
			// if that is the case -> rectangle is drawn to show in which area the geometries are selected/deleted:
			if (Application.rbSelect == true | Application.rbDelete == true) {
				checkRectangle = true;
			} else {
				checkRectangle = false;
			}
			
			// If-Clause to check if Draw.Line is activated:
			if (Application.rbLine == true) {
				checkLine_dragged = true;
			} else {
				checkLine_dragged = false;
			}
			
			// If-Clause to check if Draw.Rectangle is activated:
			if (Application.rbRectangle == true) {
				checkRectangle_dragged = true;
			} else {
				checkRectangle_dragged = false;
			}
			
			repaint();
			
		} //end of mouseDragged-listener
		
		/**
		 * mouseMoved-listener
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
			x_moved = e.getX();
			y_moved = e.getY();
			
			//Coordinates saved as text of two text fields -> show coordinates in GUI:
			Application.lblXCoordinate.setText(Integer.toString(x_moved));
			Application.lblYCoordinate.setText(Integer.toString(y_moved));
			
			repaint();
			
		} //end of mouseMoved-listener
		
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 * 
		 * a) draw:		check which objects should be drawn (RadioButton in Drawing-Group),
		 * 				insert a new point/line/rectangle into database using writeData(String sql, DatabaseConnection db) method of DatabaseConnection!
		 * 				and then just repaint();
		 * 
		 * b) select:	check if one of the points/lines/rectangles table entries has same X1/Y1- or X2/Y2-values (database select query, selected geometries are saved in string [method getData() of Class DatabaseConnection])
		 * 				if that is the case, these are re-drawn with blue color and filled (ovals/rectangles)
		 * 
		 * c) deselect:	nothing selected anymore -> reset string drawSelect and control variable -> no geometries re-drawn!
		 * 
		 * d) delete: 	coordinate is saved, first select query -> result saved as string, count number of selected geometries, if count > 0  then geometries are selected
		 * 				then iteratively delete entries with delete query and changeData() method of DatabaseConnection class
		 * 
		 * e) move:		geometrie(s) need to be selected, these are then moved to new coordinates -> coordinates of click = X1 and Y1,
		 * 				update query and changeData() method of DatabaseConnection class
		 */
		/**
		 * mouseClicked-listener:
		 * 
		 * a) draw:		check which objects should be drawn (RadioButton in Drawing-Group),
		 * 				insert a new point/line/rectangle into database using writeData(String sql, DatabaseConnection db) method of DatabaseConnection!
		 * 				and then just repaint();
		 * 
		 * b) select:	check if one of the points/lines/rectangles table entries has same X1/Y1- or X2/Y2-values (database select query, selected geometries are saved in string [method getData() of Class DatabaseConnection])
		 * 				if that is the case, these are re-drawn with blue color and filled (ovals/rectangles)
		 * 
		 * c) deselect:	nothing selected anymore -> reset string drawSelect and control variable -> no geometries re-drawn!
		 * 
		 * d) delete: 	coordinate is saved, first select query -> result saved as string, count number of selected geometries, if count > 0  then geometries are selected
		 * 				then iteratively delete entries with delete query and changeData() method of DatabaseConnection class
		 * 
		 * e) move:		geometrie(s) need to be selected, these are then moved to new coordinates -> coordinates of click = X1 and Y1,
		 * 				update query and changeData() method of DatabaseConnection class
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("CLICKED");
			// TODO Auto-generated method stub
			
			
			
			x_clicked = e.getX();
			y_clicked = e.getY();
			
			//Coordinates saved as text of two text fields -> show coordinates in GUI:
			Application.lblXCoordinate.setText(Integer.toString(x_clicked));
			Application.lblYCoordinate.setText(Integer.toString(y_clicked));
			
			
			//drawing of points:
			if (Application.rbPoint == true) {  //&& Application.checkDraw == true) {
				
				//reset select values:
				checkSelect = false;
				drawSelect = "";
				
				//Insert new point into database:
				String sql = "INSERT INTO GEOMETRIES " +
						"VALUES (NULL, 'p', " + Integer.toString(x_clicked) + "," + Integer.toString(y_clicked) + ", -999, -999);";
				System.out.println(sql);
				Application.db.writeData(sql);
				
				//Get all geometries in database as string (getData()) and save them in drawCommand string that controls the drawing:
				String dbData = Application.db.getData("SELECT * FROM GEOMETRIES");
				Application.canvas.drawCommand = dbData;
				this.checkPoints = true;
			}
			
			if (Application.rbSelect == true) {
				//buffer next to points to increase selectable area:
				int x_min = x_clicked-10;
				int x_max = x_clicked+10;
				int y_min = y_clicked-10;
				int y_max = y_clicked+10;
				System.out.println(Integer.toString(x_clicked)+","+Integer.toString(y_clicked));
				String sql = 	"SELECT * FROM GEOMETRIES WHERE (X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max)
								+ " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max) + ") OR (X2>=" + Integer.toString(x_min)
								+ " AND X2<=" + Integer.toString(x_max) + " AND Y2>=" + Integer.toString(y_min) + " AND Y2<=" + Integer.toString(y_max)
								+ ")";
				//String sql = "SELECT * FROM GEOMETRIES WHERE (X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max) + " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max) + ") OR (X2>=" + Integer.toString(x_min) + " AND X2<=" + Integer.toString(x_max) + " AND Y2>=" + Integer.toString(y_min) + " AND Y2<=" + Integer.toString(y_max) + ")";
				//String sql = "SELECT * FROM GEOMETRIES WHERE X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max) + " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max);
				
				//get all selected geometries as string, if nothing is selected -> "" is returned!
				drawSelect = Application.db.getData(sql);
				if (drawSelect != "") {
					checkSelect = true;
				}
				else {
					checkSelect = false;
					drawSelect = "";
				}
				System.out.println(drawSelect);
			}
			
			if (Application.rbDelete == true) {
				
				//reset select values:
				checkSelect = false;
				drawSelect = "";				
				
				//buffer next to points to increase selectable area, to make the selection of geometries easier:
				int x_min = x_clicked-5;
				int x_max = x_clicked+5;
				int y_min = y_clicked-5;
				int y_max = y_clicked+5;
				System.out.println(Integer.toString(x_clicked)+","+Integer.toString(y_clicked));
				String sql = "SELECT * FROM GEOMETRIES WHERE (X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max) + " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max) + ") OR (X2>=" + Integer.toString(x_min) + " AND X2<=" + Integer.toString(x_max) + " AND Y2>=" + Integer.toString(y_min) + " AND Y2<=" + Integer.toString(y_max) + ")";
				//String sql = "SELECT * FROM GEOMETRIES WHERE X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max) + " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max);
				
				//save selected geometries as string, check count, if selected then delete from database,
				//check mouseDragged-listener for further explanation:
				String deleteGeometries = Application.db.getData(sql);
				String[] geometriesEntryArray = deleteGeometries.split(";");
				int countEntries = geometriesEntryArray.length;
				for (int i=0; i<countEntries; i++) {
					String[] points = geometriesEntryArray[i].split(",");
					String id = points[0];
					String sql_statement = "DELETE FROM GEOMETRIES WHERE ID="+id;
					Application.db.changeData(sql_statement);
				}
				String dbData = Application.db.getData("SELECT * FROM GEOMETRIES");
				Application.canvas.drawCommand = dbData;
				
				//check if there are entries left in database after deletion, if not drawCommand = "":
				if (Application.canvas.drawCommand == "") {
					this.checkPoints = false;
				}
			}
			
			//selected geometries are moved to clicked coordinates (X1,Y1):
			if (Application.rbMove == true) {
				
				if (checkSelect == true) {
					String sql_statement = "";
					int delta_x, delta_y;
					
					//get selected geometries as string:
					System.out.println(drawSelect);
					String[] geometriesEntryArray = drawSelect.split(";");
					int countEntries = geometriesEntryArray.length;
					
					//iteratively move geometries:
					for (int i=0; i<countEntries; i++) {
						String[] geometries = geometriesEntryArray[i].split(",");
						String id = geometries[0];
						
						// get absolute difference for x- and y-values of first point:
						delta_x = Integer.parseInt(geometries[2]) - x_clicked;
						delta_y = Integer.parseInt(geometries[3]) - y_clicked;
						//move points:
						if (geometries[1].equals("p")) {
							int x1_new = Integer.parseInt(geometries[2]) - delta_x;
							int y1_new = Integer.parseInt(geometries[3]) - delta_y;
							sql_statement = "UPDATE GEOMETRIES SET X1 = " + Integer.toString(x1_new) + ", Y1 = " + Integer.toString(y1_new) + " WHERE ID = " + id;
							Application.db.changeData(sql_statement);
						//move lines and rectangles:
						} else {
							int x2_new = Integer.parseInt(geometries[4]) - delta_x;
							int y2_new = Integer.parseInt(geometries[5]) - delta_y;
							int width = x2_new - x_clicked;
							int height = y2_new - y_clicked;
							sql_statement = "UPDATE GEOMETRIES SET X1 = " + Integer.toString(x_clicked) + ", Y1 = " + Integer.toString(y_clicked) +  ", X2 = " + Integer.toString(x2_new) + ", Y2 = " + Integer.toString(y2_new) + " WHERE ID = " + id;
							Application.db.changeData(sql_statement);
						}
					}
					//reset select values:
					checkSelect = false;
					drawSelect = "";
					
					//get latest geometry entries and save as string
					String dbData = Application.db.getData("SELECT * FROM GEOMETRIES");
					Application.canvas.drawCommand = dbData;					
				}				
			}
			repaint();
			
		} //end of mouseClicked-listener
		
		/**
		 * mousePressed-listener, saves coordinates when mouse is pressed, used for the drawing of rectangles,
		 * lines as well as the deletion and selection of geometries
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

			x_pressed = e.getX();
			y_pressed = e.getY();
			
			//Coordinates saved as text of two text fields -> show coordinates in GUI:
			Application.lblXCoordinate.setText(Integer.toString(x_pressed));
			Application.lblYCoordinate.setText(Integer.toString(y_pressed));
			
			repaint();
			
		} //end of mousePressed-listener

		/**
		 * mouseReleased-listener, lines/rectangles are inserted into database, selected geometries are stores as string or deleted from database
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			x_released = e.getX();
			y_released = e.getY();
			
			//sort x and y values!
			int x_min, x_max, y_min, y_max;
			
			if (x_pressed > x_released) {
				x_min = x_released;
				x_max = x_pressed;
			} else {
				x_min = x_pressed;
				x_max = x_released;
			}
			
			if (y_pressed > y_released) {
				y_min = y_released;
				y_max = y_pressed;
			} else {
				y_min = y_pressed;
				y_max = y_released;
			}
			
			//Coordinates saved as text of two text fields -> show coordinates in GUI:
			Application.lblXCoordinate.setText(Integer.toString(x_released));
			Application.lblYCoordinate.setText(Integer.toString(y_released));
			
			System.out.println("Clicked:" + Integer.toString(x_pressed)+"/"+Integer.toString(y_pressed)+";Released:"+Integer.toString(x_released)+"/"+Integer.toString(y_released));
			
			//Rectangular selection, select all geometries within rectangle, save selected geometries as string (drawSelect):
			if (Application.rbSelect == true) {	
				
				String sql =	"SELECT * FROM GEOMETRIES WHERE (X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max)
								+ " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max) + ") OR (X2>=" + Integer.toString(x_min)
								+ " AND X2<=" + Integer.toString(x_max) + " AND Y2>=" + Integer.toString(y_min) + " AND Y2<=" + Integer.toString(y_max)
								+ ")";
				drawSelect = Application.db.getData(sql);
				System.out.println(sql);
				System.out.println(drawSelect);
				if (drawSelect != "") {
					checkSelect = true;
				}
				else {
					checkSelect = false;
					drawSelect = "";
				}				
			}
			
			//Rectangular deletion:
			if (Application.rbDelete == true) {

				System.out.println(Integer.toString(x_clicked)+","+Integer.toString(y_clicked));
				String sql =	"SELECT * FROM GEOMETRIES WHERE (X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max)
								+ " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max) + ") OR (X2>=" + Integer.toString(x_min)
								+ " AND X2<=" + Integer.toString(x_max) + " AND Y2>=" + Integer.toString(y_min) + " AND Y2<=" + Integer.toString(y_max) + ")";
				//String sql = "SELECT * FROM GEOMETRIES WHERE X1>=" + Integer.toString(x_min) + " AND X1<=" + Integer.toString(x_max) + " AND Y1>=" + Integer.toString(y_min) + " AND Y1<=" + Integer.toString(y_max);
				
				//save selected geometries as string, check count, if selected then delete from database
				String deleteGeometries = Application.db.getData(sql);
				String[] geometriesEntryArray = deleteGeometries.split(";");
				int countEntries = geometriesEntryArray.length;
				for (int i=0; i<countEntries; i++) {
					String[] points = geometriesEntryArray[i].split(",");
					String id = points[0];
					String sql_statement = "DELETE FROM GEOMETRIES WHERE ID="+id;	//sql statement
					Application.db.changeData(sql_statement);	//deletion of geometries from database
				}
				
				//get latest entries and save as string:
				String dbData = Application.db.getData("SELECT * FROM GEOMETRIES");
				Application.canvas.drawCommand = dbData;
				
				//check if there are entries left in database after deletion, if not drawCommand = "":
				if (Application.canvas.drawCommand == "") {
					this.checkPoints = false;
				}
				
				//reset select values:
				checkSelect = false;
				drawSelect = "";
			}
			
			// Line is drawn when mouse is released after dragging:
			if (Application.rbLine == true & checkLine_dragged == true) {
				//Coordinates when mouse was first pressed and last mouse dragged coordinates:
				String sql = "INSERT INTO GEOMETRIES " +
						"VALUES (NULL, 'l', " + Integer.toString(x_pressed) + "," + Integer.toString(y_pressed) + ", " + Integer.toString(x_released) + "," + Integer.toString(y_released) + ")";
				System.out.println(sql);
				Application.db.writeData(sql);	//write in database
				
				//get all database entries:
				String dbData = Application.db.getData("SELECT * FROM GEOMETRIES");
				Application.canvas.drawCommand = dbData;
				checkLine_dragged = false;
				checkPoints = true;				
			}
			
			// Rectangle is drawn when mouse is released after dragging:
			if (Application.rbRectangle == true & checkRectangle_dragged == true) {
				String drawData = this.getDrawData(x_pressed, y_pressed, x_dragged, y_dragged);
				String[] drawDataArray = drawData.split(",");
				x1 = Integer.parseInt(drawDataArray[0]);
				y1 = Integer.parseInt(drawDataArray[1]);
				x2 = x1 + Integer.parseInt(drawDataArray[2]);
				y2 = y1 + Integer.parseInt(drawDataArray[3]);
				String sql = "INSERT INTO GEOMETRIES " +
						"VALUES (NULL, 'r', " + Integer.toString(x1) + "," + Integer.toString(y1) + ", " + Integer.toString(x2) + "," + Integer.toString(y2) + ")";
				System.out.println(sql);
				Application.db.writeData(sql);	//write to database
				//get all database entries:
				String dbData = Application.db.getData("SELECT * FROM GEOMETRIES");
				Application.canvas.drawCommand = dbData;
				checkRectangle_dragged = false;
				checkPoints = true;		
			}
			
			// set checkSelect_rectangle to false -> not shown anymore:
			checkRectangle = false;
			
			repaint();
			
		} //end of mouseReleased-listener

		/**
		 * mouseEntered-listener
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

			int x = e.getX();
			int y = e.getY();
			
			//Coordinates saved as text of two text fields -> show coordinates in GUI:
			Application.lblXCoordinate.setText(Integer.toString(x));
			Application.lblYCoordinate.setText(Integer.toString(y));
			
			repaint();
			
		} //end of mouseEntered-listener

		/**
		 * mouseExited-listener
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

			int x = e.getX();
			int y = e.getY();
			
			//Coordinates saved as text of two text fields -> show coordinates in GUI:
			Application.lblXCoordinate.setText(Integer.toString(x));
			Application.lblYCoordinate.setText(Integer.toString(y));
			
			repaint();
			
		} //end of mouseExited-listener
		
} //end of functionality class

