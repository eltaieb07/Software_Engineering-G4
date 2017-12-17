import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.Path2D;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class MyPanel extends JPanel {
	
	public static ArrayList<Point> points = new ArrayList<Point>();
	public static ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
	public static ArrayList<Rectangle2D.Double> rectangles = new ArrayList<Rectangle2D.Double>();
	private Path2D currentShape;
    public static List<Path2D> polygons = new ArrayList<Path2D>();
    private Point lastPoint;
    private Point currentPoint;
	
	Point startDrag_rect, endDrag_rect;    // These points are used to visualise the rectangle during drawing/dragging.
	Point startDrag_line, endDrag_line;    // These points are used to visualise the line during drawing/dragging.
	Point startPoint , endPoint = null;  // These points are for start and end point of line.
	Point pt = null;   // point for selection
	
	
	public MyPanel(){
	
		this.addMouseListener(new MouseAdapter() {
			private int x,y,dx,dy=0;
			public void mousePressed(MouseEvent e){
				//System.out.println("Mouse Pressed at: "+e.getPoint());
				if (ShapeDrawing.current_shape == 1){
					points.add(new Point(e.getX() , e.getY()));
					repaint();
				}
				else if (ShapeDrawing.current_shape == 2) {
					startPoint = e.getPoint();
					startDrag_line = e.getPoint();
				
				}
				else if (ShapeDrawing.current_shape ==3){
					this.x = e.getX();
					this.y = e.getY();
					startDrag_rect = new Point(x,y);
				}
				else if (ShapeDrawing.current_shape ==4) {
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
	                        polygons.add(currentShape);
	                        currentShape = null;
	                        lastPoint = null;
	                        repaint();
	                    }
	                   
	                }
				else if (ShapeDrawing.current_shape == 10){
					
					pt = new Point(e.getX(),e.getY());
					repaint();
					
					
				}
				else if (ShapeDrawing.rectangle.isEnabled()){
					ShapeDrawing.label.setText("Please select a shape from toolbar!");
				}

			}
			public void mouseReleased(MouseEvent e){
				if (ShapeDrawing.current_shape == 2) {
					endPoint = e.getPoint();
					lines.add(new Line2D.Double(startPoint,endPoint));
					startDrag_line = null;
					endDrag_line = null;
					repaint();
				}
				else if (ShapeDrawing.current_shape ==3){
					this.dx = e.getX();
					this.dy = e.getY();
					
					MyRectangle rect = new MyRectangle(x,y,dx,dy);
					Shape r = rect.makeRectangle();//new Rectangle(Math.min(x, dx), Math.min(y, dy), Math.abs(x - dx), Math.abs(y - dy));
					
					rectangles.add((Double) r);
					//addRectangle(rect);
					startDrag_rect = null;
			        endDrag_rect = null;
					repaint();
				}
				else{
					
					
				}
				//System.out.println("Mouse Released at: "+e.getPoint());
				

			}
	      });

	      this.addMouseMotionListener(new MouseMotionAdapter() {
	    	  public void mouseDragged(MouseEvent e){
	    		  endDrag_line = e.getPoint();
		  		  endDrag_rect = new Point(e.getX(), e.getY());
		  		  repaint();
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
	
	/*public void addRectangle(MyRectangle r) {
		// TODO Auto-generated method stub
		rectangle.add(r);
		repaint();
	}*/
	
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
	public void clear_display(){
		points.clear();
		lines.clear();
		rectangles.clear();
		polygons.clear();

		repaint();
	}
	public void update_drawing(){
		repaint();
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		Graphics2D g1 = (Graphics2D) g;  //for rendering of selected objects
		
		g2.setPaint(Color.LIGHT_GRAY);
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      paintBackground(g2);
	      
	      Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN , Color.RED, Color.BLUE, Color.PINK};
	      int colorIndex = 0;

	      g2.setStroke(new BasicStroke(1));
	      //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
		
		//System.out.println("in paint method");
		super.paintComponent(g2);
		
		g.setColor(Color.cyan);
		g.fillOval(60, 50, 10, 10);
		
		for (Point p : points){
			//Point2D pt = new Point(60,50);
			g2.setPaint(Color.RED);
			//r.drawPerfectRect(g2);
			g2.fillOval(p.x,p.y,10,10);
		}
		
		for (Shape l : lines){
			//Point2D pt = new Point(60,50);
			g2.setPaint(Color.RED);
			//r.drawPerfectRect(g2);
			g2.draw(l);
		}
		
		for (Shape r : rectangles){
			
			if (pt!=null && r.contains(pt)){
				g1.setPaint(Color.CYAN);
				//r.drawPerfectRect(g2);
				g1.setStroke(new BasicStroke(3));
				g1.draw(r);
				//System.out.println("Contains");
				//g1.dispose();
				g1.setStroke(new BasicStroke(1));
			}
			else{
				g2.setPaint(Color.BLACK);
				//r.drawPerfectRect(g2);
				g2.draw(r);
			}
		}
		for (Shape shape : polygons) {
        	g2.setColor(Color.BLACK);
            g2.draw(shape);
        }

		//System.out.println(shapes.size());
		if (startDrag_line != null && endDrag_line != null) {
	        g2.setPaint(Color.RED);
	        Shape line = new Line2D.Float(startDrag_line , endDrag_line);
	        g2.draw(line);
	    }
		
		if (startDrag_rect != null && endDrag_rect != null) {
	        g2.setPaint(Color.BLACK);
	        int x = startDrag_rect.x , y = startDrag_rect.y , dx = endDrag_rect.x , dy = endDrag_rect.y ;
	        Shape rect = new Rectangle(Math.min(x, dx), Math.min(y, dy), Math.abs(x - dx), Math.abs(y - dy));
	        //rect.drawPerfectRect(g2);
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
                //System.out.println(lastPoint + " - " + currentPoint);
            	g2.setColor(Color.CYAN);//g2.setColor(new Color(255, 0, 0, 64));
                g2.draw(new Line2D.Float(lastPoint, currentPoint));
            }
        }		
		g2.dispose();

	}
	
}
