/**
 * 
 */
package gisObject;

import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D;

/**
 * 
 * @author Shahab Ahmed, Eltaieb Albasheer
 *Drawing Panel Class extends JPanel and registers mouse listeners to it for creating, drawing and painting shapes
 */
public class DrawingPanel extends JPanel {
	/**
	 * serial VersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Array List of type ShapeItems for storing all the shapes e.g. points, lines, rectangles and polygons  
	 */
	public static List<ShapeItems> shapes = new ArrayList<ShapeItems>();
	/**
	 * Array List of type ShapeItems for storing the shapes(points) by drawing rectangle for range query
	 */
	public static List<ShapeItems> selected_shapes = new ArrayList<ShapeItems>();
	/**
	 * it stores the current polygon(closed path) which is being created
	 */
	private Path2D currentShape;
	/**
	 * it stores the last point created by single click for creating Polygon(closed path)
	 */
	private Point lastPoint;
	/**
	 * it stores the current point to which user has moved the mouse on during drawing of Polygon. It is updated when the mouse position is changed.
	 */
	private Point currentPoint;
	/**
	 * startDrag_rect , endDrag_rect : These points are used to store the point from which dragging starts and point where it ends so that Rectangle can be seen during drawing/dragging.
	 */
	Point startDrag_rect, endDrag_rect;    // These points are used to visualise the rectangle during drawing/dragging.
	/**
	 * startDrag_line , endDrag_line: These points are used to store the point from which dragging starts and point where it ends so that Line can be seen during drawing/dragging.
	 */
	Point startDrag_line, endDrag_line;    // These points are used to visualise the line during drawing/dragging.

	/**
	 * it is a point for selection of shapes
	 */
	Point pt = null;   // point for selection

	/**
	 * it stores the selected shape or object
	 */
	ShapeItems selected = null;

	/**
	 * it stores the default color to be given to all the shapes to be drawn
	 */
	Color default_Color = Color.BLACK;
	/**
	 * it stores the rectangular shape for selection of objects by range query
	 */
	Shape range;

	/**
	 * MyPanel() is a constructor which registers the JPanel with all the required mouse listeners related to drawing of all the shapes.
	 */
	public DrawingPanel(){

		/**
		 * Adds mouse listener with implementation of mouse pressed and mouse released functions
		 */
		this.addMouseListener(new MouseAdapter() {
			private int x,y,dx,dy=0;

			/**
			 * This function detects starting point for all the shapes e.g. rectangle and line. It creates the point and polygon objects directly in it.
			 */
			public void mousePressed(MouseEvent e){            
				if (GUI.selected_button == 1){
					shapes.add(new ShapeItems("point", new Point(e.getX() , e.getY()) , default_Color));
					repaint();
				}
				else if (GUI.selected_button == 2) {
					this.x = e.getX();
					this.y = e.getY();
					startDrag_line = e.getPoint();
				}
				else if (GUI.selected_button ==3){
					this.x = e.getX();
					this.y = e.getY();
					startDrag_rect = new Point(x,y);
				}
				else if (GUI.selected_button ==4) {
					if (e.getClickCount() == 1) {
						Point p = e.getPoint();
						lastPoint = p;
						if (currentShape == null) {
							currentShape = new Path2D.Float();
							currentShape.moveTo(p.x, p.y);
						} else {
							currentShape.lineTo(p.x, p.y);
						}
						repaint();
					} else if (e.getClickCount() == 2) {
						currentShape.closePath();
						shapes.add(new ShapeItems("polygon", currentShape , default_Color));
						currentShape = null;
						lastPoint = null;
						repaint();
					}
				}
				else if(GUI.selected_button == 5){
					this.x = e.getX();
					this.y = e.getY();
					startDrag_rect = new Point(x,y);
				}
				else if (GUI.selected_button == 10){     
					pt = new Point(e.getX(),e.getY());
					for (ShapeItems item : shapes) {              //This is for changing the color and not to be used here in this block
						if((item.getName().equals("rectangle") || item.getName().equals("polygon")) && item.getShape().contains((Point2D)pt)){
							selected = item;
							System.out.println(selected.getShape());
							break;
						}
						else if (item.getName().equals("line") && ((Line2D.Double) item.getShape()).ptLineDist(pt)<5){
							selected = item;
							break;
						}
						else if (item.getName().equals("point") && item.getPoint().distance(pt)<6){
							selected = item;
							break;
						}
						else{
							selected = null;
						}
					}
					repaint();
				}
				else if (GUI.rectangle.isEnabled()){
					GUI.label.setText("Please select a shape from toolbar!");
				}
			}
			/**
			 * it detects the end points and creates the rectangle and line objects using the existing starting points created in mousePressed() method.
			 */
			public void mouseReleased(MouseEvent e){
				if (GUI.selected_button == 2) {
					this.dx = e.getX();
					this.dy = e.getY();
					shapes.add(new ShapeItems("line", new Line2D.Double(new Point(x,y),new Point(dx,dy)),default_Color));
					startDrag_line = null;
					endDrag_line = null;
					repaint();
				}
				else if (GUI.selected_button ==3){
					this.dx = e.getX();
					this.dy = e.getY();
					Shape r = new Rectangle2D.Double(Math.min(x, dx), Math.min(y, dy), Math.abs(x - dx), Math.abs(y - dy));
					shapes.add(new ShapeItems("rectangle", r,default_Color));
					startDrag_rect = null;
					endDrag_rect = null;
					repaint();
				}
				else if(GUI.selected_button ==5){   // for range query 
					this.dx = e.getX();
					this.dy = e.getY();
					range = new Rectangle2D.Double(Math.min(x, dx), Math.min(y, dy), Math.abs(x - dx), Math.abs(y - dy));
					selected_shapes.clear();
					repaint();
					for (ShapeItems item : shapes){
						if (item.getName().equals("point")){
							if (range.contains(item.getPoint())){
								selected_shapes.add(item);
							}
						}
					}
					startDrag_rect = null;
					endDrag_rect = null;
					repaint();
				}
			}
		});
		/**
		 * Adds mouse motion listener to JPanel with implementation of mouse drag and mouse release functions
		 */
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e){
				endDrag_line = e.getPoint();
				endDrag_rect = new Point(e.getX(), e.getY());
				repaint();
				if(GUI.selected_button ==5){
					endDrag_rect = new Point(e.getX(), e.getY());
					repaint();
				}
			}
			public void mouseMoved(MouseEvent e){
				if (currentShape != null) {
					currentPoint = e.getPoint();
					repaint();
				} else {
					currentPoint = null;
				}
			}

		});
	}
	@SuppressWarnings("unused")
	private void paintBackground(Graphics2D g2){
		for (int i = 0; i < getSize().width; i += 10) {
			Shape line = new Line2D.Float(i, 0, i, getSize().height);
			g2.draw(line);
		}
		for (int i = 0; i < getSize().height; i += 10) {
			Shape line = new Line2D.Float(0, i, getSize().width, i);
			g2.draw(line);
		}
	}
	/**
	 * Clears the display by clearing all the lists containing shapes/objects and by calling the repaint() method.
	 */
	public void clear_display(){
		int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the display");
		if(answer == JOptionPane.YES_OPTION) {
		shapes.clear();
		selected_shapes.clear();
		range=null;
		repaint();
		}
	}
	/**
	 * Redraws the list containing shapes/objects by just calling the repaint() method. This method is used to update the drawing after loading the shapes from CSV File or from Database
	 */
	public void update_drawing(){
		repaint();
	}
	/**
	 * Deletes the selected object by matching it with the one in the list containing all shapes.
	 */
	public void delete(){
		for (ShapeItems item : shapes) {
			if (selected!=null && item.equals(selected)) {
				shapes.remove(item);
				break;
			}
		}
		repaint();
	}
	/**
	 * This function move() is used to move the selected object left, right, up or down
	 */
	public void move(){
		if (selected != null){
			System.out.println(GUI.moveID);
			if (selected.getName().equals("point")){
				Point p = null;
				switch (GUI.moveID){
				case "up":
					p = (Point) selected.getPoint();
					p.setLocation(p.x , p.y-10);
					pt.setLocation(pt.x, pt.y-10);
					selected.setPoint(p);
					break;
				case "down":
					p = (Point) selected.getPoint();
					p.setLocation(p.x, p.y+10);
					pt.setLocation(pt.x, pt.y+10);
					selected.setPoint(p);
					break;
				case "right":
					p = (Point)selected.getPoint();
					p.setLocation(p.x+10, p.y);
					pt.setLocation(pt.x+10, pt.y);
					selected.setPoint(p);
					break;
				case "left":
					p = (Point) selected.getPoint();
					p.setLocation(p.x-10, p.y);
					pt.setLocation(pt.x-10, pt.y);
					selected.setPoint(p);
					break;
				default:
					selected.setPoint(selected.getPoint());
				}
				repaint();
			}
			else if (selected.getName().equals("line")){
				Line2D.Double l = null;
				switch (GUI.moveID){
				case "up":
					l = (Line2D.Double) selected.getShape();
					l.setLine(l.getX1(), l.getY1()-10, l.getX2(), l.getY2()-10);
					pt.setLocation(pt.x, pt.y-10);
					selected.setShape(l);
					break;
				case "down":
					l = (Line2D.Double) selected.getShape();
					l.setLine(l.getX1(), l.getY1()+10, l.getX2(), l.getY2()+10);
					pt.setLocation(pt.x, pt.y+10);
					selected.setShape(l);
					break;
				case "right":
					l = (Line2D.Double) selected.getShape();
					l.setLine(l.getX1()+10, l.getY1(), l.getX2()+10, l.getY2());
					pt.setLocation(pt.x+10, pt.y);
					selected.setShape(l);
					break;
				case "left":
					l = (Line2D.Double) selected.getShape();
					l.setLine(l.getX1()-10, l.getY1(), l.getX2()-10, l.getY2());
					pt.setLocation(pt.x-10, pt.y);
					selected.setShape(l);
					break;
				default:
					selected.setShape(selected.getShape());
				}
				repaint();
			}
			else if (selected.getName().equals("rectangle")){

				Rectangle2D.Double r = null;
				switch (GUI.moveID){
				case "up":
					r = (Rectangle2D.Double) selected.getShape();
					r.setFrame(r.getX(), r.getY()-10, r.getWidth(), r.getHeight());
					pt.setLocation(pt.x, pt.y-10);
					selected.setShape(r);
					break;
				case "down":
					r = (Rectangle2D.Double) selected.getShape();
					r.setFrame(r.getX(), r.getY()+10, r.getWidth(), r.getHeight());
					pt.setLocation(pt.x, pt.y+10);
					selected.setShape(r);
					break;
				case "right":
					r = (Rectangle2D.Double) selected.getShape();
					r.setFrame(r.getX()+10, r.getY(), r.getWidth(), r.getHeight());
					pt.setLocation(pt.x+10, pt.y);
					selected.setShape(r);
					break;
				case "left":
					r = (Rectangle2D.Double) selected.getShape();
					r.setFrame(r.getX()-10, r.getY(), r.getWidth(), r.getHeight());
					pt.setLocation(pt.x-10, pt.y);
					selected.setShape(r);
					break;
				default:
					selected.setShape(selected.getShape());
				}
				repaint();
			}
			else if (selected.getName().equals("polygon")){
				System.out.println(selected.getName());
				AffineTransform at = new AffineTransform();
				at.translate(0, 0);
				switch (GUI.moveID){
				case "up":
					at.translate(0, -10);
					pt.setLocation(pt.x, pt.y-10);
					break;
				case "down":
					at.translate(0, 10);
					pt.setLocation(pt.x, pt.y+10);
					break;
				case "right":
					at.translate(10,0);
					pt.setLocation(pt.x+10, pt.y);
					break;
				case "left":
					at.translate(-10,0);
					pt.setLocation(pt.x-10, pt.y);
					break;
				}
				repaint();
				selected.setShape(at.createTransformedShape(selected.getShape()));
			}
		}
		else{
			GUI.label.setText("Please select the object first using Select toolbar");
		}
	}   
	/**
	 * paint() method is where all the painting code is placed e.g. drawing and filling of shapes/objects based upon their colors
	 */
	public void paint(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;  //for rendering of selected objects
		
		g2.setPaint(Color.LIGHT_GRAY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
		paintBackground(g2);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		for (ShapeItems s : shapes){
			if (s.getName().equals("point")){
				if (pt!=null && s.getPoint().distance(pt)<6){
					g2.setPaint(Color.CYAN);
					g2.fillOval(s.getPoint().x,s.getPoint().y ,10,10);
				}
				else {
					g2.setPaint(s.getColor());
					g2.fillOval(s.getPoint().x,s.getPoint().y ,10,10);
				}
			}
			else if (pt!=null && s.getShape().contains(pt)){
				g2.setPaint(Color.CYAN);
				g2.setStroke(new BasicStroke(3));
				g2.draw(s.getShape());
				g2.setStroke(new BasicStroke(2));
			}
			else if (pt!=null && s.getName().equals("line")){
				if (((Line2D.Double) s.getShape()).ptLineDist(pt)<5){
					g2.setPaint(Color.CYAN);
					g2.setStroke(new BasicStroke(3));
					g2.draw(s.getShape());
					g2.setStroke(new BasicStroke(2));
				}
				else{
					g2.setColor(s.getColor());
					g2.draw(s.getShape());
				}
			}
			else{
				g2.setColor(s.getColor());
				g2.draw(s.getShape());
			}
		}
		if (startDrag_line != null && endDrag_line != null) {
			g2.setPaint(Color.RED);
			Shape line = new Line2D.Float(startDrag_line , endDrag_line);
			g2.draw(line);
		}
		if (startDrag_rect != null && endDrag_rect != null) {
			g2.setPaint(Color.BLACK);
			int x = startDrag_rect.x , y = startDrag_rect.y , dx = endDrag_rect.x , dy = endDrag_rect.y ;
			Shape rect = new Rectangle(Math.min(x, dx), Math.min(y, dy), Math.abs(x - dx), Math.abs(y - dy));
			g2.draw(rect);
		}
		if (lastPoint != null) {
			g2.setColor(Color.RED);
			g2.fillOval(lastPoint.x - 2, lastPoint.y - 2, 4, 4);
		}
		if (currentShape != null) {
			g2.setColor(Color.RED);
			g2.draw(currentShape);
			if (lastPoint != null && currentPoint != null) {
				g2.setColor(Color.CYAN);
				g2.draw(new Line2D.Float(lastPoint, currentPoint));
			}
		}   
		if (range!=null){
			g2.setColor(Color.BLACK);
			g2.fill(range);
			for (ShapeItems s : selected_shapes){
				g2.setPaint(Color.CYAN);
				g2.fillOval(s.getPoint().x,s.getPoint().y ,10,10);
			}
		}
		g2.dispose();
		//g1.dispose();
	}
}