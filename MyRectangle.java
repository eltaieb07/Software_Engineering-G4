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

	public Rectangle2D.Double makeRectangle() {
	      return new Rectangle2D.Double(px, py, pw, ph);
	}

	
}
