import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class PointObject extends JFrame {
	
	/*Edtited by Eltaieb*/
	
	private int pointID;
	private String pointNumber;
	private double pointX;
	private double pointY;
	
	public PointObject() {
		
		/*Edtited by Eltaieb*/
		/**
		 * class constructor
		 * pointID
		 * pointNumber
		 */
		this.pointID = 0;
		this.pointNumber = "";
		this.pointX = 0;
		this.pointY = 0;
	}
	
	/*Edtited by Eltaieb*/
	/**
	 * class constructor method
	 * @param id point ID
	 * @param NR point number
	 * @param X x coordinate
	 * @param Y y coordinate
	 */
	public PointObject(int id, String NMBR, double X, double Y) {
		this.pointID = id;
		this.pointX = X;
		this.pointY = Y;
		this.pointNumber = NMBR;
	}
	
	/*Edtited by Eltaieb*/
	// getter and setter methods for pointID & pointNumber
	public int getPointID() {
		return pointID;
	}
	
	public void setPointID(int pointID) {
		this.pointID = pointID;
	}
	
	public String getPointNumber() {
		return pointNumber;
	}
	
	public String setPointNumber(String poitNumber) {
		this.pointNumber = pointNumber;
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
