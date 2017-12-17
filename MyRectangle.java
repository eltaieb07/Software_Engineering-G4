import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MyRectangle{
	
	private int x,y,x2,y2, px,py,pw,ph= 0;
	
public MyRectangle(int x, int y, int a, int b){
		super();
		/*this.x = x;
		this.y = y;

        this.x2 = a;
        this.y2 = b;*/
        this.px = Math.min(x,a);
        this.py = Math.min(y,b);
        this.pw =Math.abs(x-a);
        this.ph =Math.abs(y-b);

}
	/*public void drawPerfectRect(Graphics2D g2) {
        this.px = Math.min(x,x2);
        this.py = Math.min(y,y2);
        this.pw =Math.abs(x-x2);
        this.ph =Math.abs(y-y2);
        //System.out.println(px+" , "+py+" , "+pw+" , "+ph);
        //g.setColor(Color.BLACK);
        g2.drawRect(px, py, pw, ph);
    }*/
	public Rectangle2D.Double makeRectangle() {
	      return new Rectangle2D.Double(px, py, pw, ph);
	}
	/*@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(double x, double y) {
		if (x>this.px && y>this.py && x<(px+pw)&& y<(py+ph))
			return true;
		else
		// TODO Auto-generated method stub
		return false;
		
	}
	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public java.awt.Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}*/
	
}
