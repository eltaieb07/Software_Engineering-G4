package administrategraphicalobject;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * to draw line feature
 * @author Eltaieb
 *
 */
public class LineObject extends JFrame {
	private int lineID;
	private String lineNumber;
	private PointObject firstPointObject;
	private PointObject secondPointObject;
	
	/**
	 * Line defined bt 2 points
	 * Line id
	 * Line number
	 * 
	 */
	public LineObject() {
		this.lineID = 0;
		this.lineNumber = String.valueOf(this.lineID);	
		this.firstPointObject = new PointObject();
		this.secondPointObject = new PointObject();
		
	}
	
	/*
	 * 
	 */
	public LineObject(int _id, String _LI_NMBR, PointObject _FP, PointObject _SP) {
		this.lineID = _id;
		this.lineNumber = _LI_NMBR;
		this.firstPointObject = _FP;
		this.secondPointObject = _SP;
	}
	
	/**
	 * each pair of X and Y in points
	 * @param _id
	 * @param _X1
	 * @param _Y1
	 * @param _X2
	 * @param _Y2
	 */
	public LineObject(int _id, double _X1, double _Y1, double _X2, double _Y2) {
		this.lineID = _id;
		this.lineNumber = String.valueOf(_id);
		this.firstPointObject = new PointObject(_X1, _Y1);
		this.secondPointObject = new PointObject(_X2, _Y2);
		
	}
	
	/**
	 * 
	 * @param _X1
	 * @param _Y1
	 * @param _X2
	 * @param _Y2
	 */
	public LineObject(double _X1, double _Y1, double _X2, double _Y2) {
		this.lineID = 0;
		this.lineNumber = String.valueOf(this.lineID);
		this.firstPointObject = new PointObject(_X1, _Y1);
		this.secondPointObject = new PointObject(_X2, _Y2);
		
	}
	
	/**
	 * 
	 * @param _FP
	 * @param _SP
	 */
	public LineObject (PointObject _FP, PointObject _SP) {
		this.firstPointObject = _FP;
		this.secondPointObject = _SP;
	}
	
	// setter and getter methods
	
	public int getLineID() {
		return lineID;
	}
	
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}
	
	public String getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public PointObject getfirstPointObject() {
		return firstPointObject;
	}
	
	public void setfirstPointObject(PointObject firstPointObject) {
		this.firstPointObject = firstPointObject;
	}
	
	public PointObject getsecondPointObject() {
		return secondPointObject;
	}
	
	public void setsecondPointObject(PointObject secondPointObject) {
		this.secondPointObject = secondPointObject;
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LineObject gui = new LineObject();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(200, 200);
		gui.setVisible(true);

	}

}
