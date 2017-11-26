import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class PointObject extends JFrame {
	
	private double pointX;
	private double pointY;
	
	public PointObject() {
		
		this.pointX = 0;
		this.pointY = 0;
	}
	
	public PointObject(double X, double Y) {
		this.pointX = X;
		this.pointY = Y;
	}
	
	public double getPointX() {
		return pointX;
	}
	public void setPointX(double pointX) {
		this.pointX = pointX;
	}
	public double getPointY() {
		return pointY;
	}
	public void setPointY(double pointY) {
		this.pointY = pointY;
	}

	public static void main(String[] args) {
		PointObject gui = new PointObject();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(200, 200);
		gui.setVisible(true);

	}

}
