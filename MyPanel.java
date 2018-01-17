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
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.Path2D;


public class MyPanel extends JPanel {
	
	public static ArrayList<Point> points = new ArrayList<Point>();
	public static ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
	public static ArrayList<Rectangle2D.Double> rectangles = new ArrayList<Rectangle2D.Double>();
	public static List<ShapeItems> shapes = new ArrayList<ShapeItems>();
	public static List<ShapeItems> selected_shapes = new ArrayList<ShapeItems>();
	private Path2D currentShape;
    public static List<Path2D> polygons = new ArrayList<Path2D>();
    private Point lastPoint;
    private Point currentPoint;
	
	Point startDrag_rect, endDrag_rect;    // These points are used to visualise the rectangle during drawing/dragging.
	Point startDrag_line, endDrag_line;    // These points are used to visualise the line during drawing/dragging.
	Point startPoint , endPoint = null;  // These points are for start and end point of line.
	Point pt = null;   // point for selection
	ShapeItems selected = null;
	Color default_Color = Color.BLACK;
	Shape range;
	
	public MyPanel(){
	
		this.addMouseListener(new MouseAdapter() {
			private int x,y,dx,dy=0;
			public void mousePressed(MouseEvent e){
				//System.out.println("Mouse Pressed at: "+e.getPoint());
				if (ShapeDrawing.current_shape == 1){
					points.add(new Point(e.getX() , e.getY()));
					shapes.add(new ShapeItems("point", new Point(e.getX() , e.getY()) , default_Color));
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
	                        shapes.add(new ShapeItems("polygon", currentShape , default_Color));
	                        currentShape = null;
	                        lastPoint = null;
	                        repaint();
	                    }
	                   
	            }
				else if(ShapeDrawing.current_shape == 5){
					this.x = e.getX();
					this.y = e.getY();
					startDrag_rect = new Point(x,y);
				}
				else if (ShapeDrawing.current_shape == 10){     
					Color old_color= null;
					pt = new Point(e.getX(),e.getY());
					/*for (ShapeItems item : shapes) {              //This is for changing the color and not to be used here in this block
						
                        if (!item.getName().equals("point") && item.getShape().contains(pt)) {
                        	old_color = item.getColor();
                            item.setColor(Color.CYAN);
                            //System.out.println(Color.CYAN);
                        }
                        else{
                        	item.setColor(item.getColor());
                        }
                    }
                    repaint();*/
					for (ShapeItems item : shapes) {              //This is for changing the color and not to be used here in this block
                    	if((item.getName().equals("rectangle") || item.getName().equals("polygon"))&& item.getShape().contains((Point2D)pt)){
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

				else if (ShapeDrawing.rectangle.isEnabled()){
					ShapeDrawing.label.setText("Please select a shape from toolbar!");
				}

			}
			public void mouseReleased(MouseEvent e){
				if (ShapeDrawing.current_shape == 2) {
					endPoint = e.getPoint();
					lines.add(new Line2D.Double(startPoint,endPoint));
					shapes.add(new ShapeItems("line", new Line2D.Double(startPoint,endPoint),default_Color));
					startDrag_line = null;
					endDrag_line = null;
					repaint();
				}
				else if (ShapeDrawing.current_shape ==3){
					this.dx = e.getX();
					this.dy = e.getY();
					
					//MyRectangle rect = new MyRectangle(x,y,dx,dy);
					//Shape r = rect.makeRectangle();
					Shape r = new Rectangle2D.Double(Math.min(x, dx), Math.min(y, dy), Math.abs(x - dx), Math.abs(y - dy));
					shapes.add(new ShapeItems("rectangle", r,default_Color));
					//rectangles.add( r);
					//addRectangle(rect);
					startDrag_rect = null;
			        endDrag_rect = null;
					repaint();
				}
				else if(ShapeDrawing.current_shape ==5){   // for range query 
					this.dx = e.getX();
					this.dy = e.getY();
					//MyRectangle rect = new MyRectangle(x,y,dx,dy);
					//r = rect.makeRectangle();
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

	      this.addMouseMotionListener(new MouseMotionAdapter() {
	    	  public void mouseDragged(MouseEvent e){
	    		  endDrag_line = e.getPoint();
		  		  endDrag_rect = new Point(e.getX(), e.getY());
		  		  repaint();
		  		  
		  		if(ShapeDrawing.current_shape ==5){
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
		shapes.clear();
		selected_shapes.clear();
		range=null;

		repaint();
	}
	public void update_drawing(){
		repaint();
	}
	public void delete(){
		for (Iterator<ShapeItems> iter = shapes.iterator() ; iter.hasNext();){
			
			if (selected!=null && iter.next().equals(selected)){

				iter.remove();
				break;
			}
			iter.next();
		}
		repaint();
	}

	public void move(){
		if (selected != null){
			System.out.println(ShapeDrawing.moveID);
			
			if (selected.getName().equals("point")){
				Point p = null;
				switch (ShapeDrawing.moveID){
					case "up":
						p = (Point) selected.getPoint();
						//p.translate(p.x, p.y-10);
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
				switch (ShapeDrawing.moveID){
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
				switch (ShapeDrawing.moveID){
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
				switch (ShapeDrawing.moveID){
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
			ShapeDrawing.label.setText("Please select the object first using Select toolbar");
		}

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
		
		//g.setColor(Color.cyan);
		//g.fillOval(60, 50, 10, 10);
		
		
		/*for (Point p : points){
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
        }*/
		
		
		
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
				g1.setPaint(Color.CYAN);
				//r.drawPerfectRect(g2);
				g1.setStroke(new BasicStroke(3));
				g1.draw(s.getShape());
				//System.out.println("Contains");
				//g1.dispose();
				g1.setStroke(new BasicStroke(1));
				
			}
			else if (pt!=null && s.getName().equals("line")){
				if (((Line2D.Double) s.getShape()).ptLineDist(pt)<5){
					
					g1.setPaint(Color.CYAN);
					//r.drawPerfectRect(g2);
					g1.setStroke(new BasicStroke(3));
					g1.draw(s.getShape());
					//System.out.println("Contains");
					//g1.dispose();
					g1.setStroke(new BasicStroke(1));
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
 
        if (range!=null){
        	g2.setColor(Color.BLACK);
            g2.fill(range);
            for (ShapeItems s : selected_shapes){
    			g2.setPaint(Color.CYAN);
    			g2.fillOval(s.getPoint().x,s.getPoint().y ,10,10);
    		}
        }
		
		
        
		g2.dispose();

	}
	
}