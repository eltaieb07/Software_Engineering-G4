package de.seproject.de;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author georg
 * Create date 20.11.15
 * 
 * used to create two different PopupMenus for the creation and loading of a database
 *
 */

public class PopupMenu extends JFrame implements ActionListener {
	
	/**
	 * @param frame_popup	JFrame, frame for the popupMenu, either create database or load database
	 * @param textField		JTextfield, name of the database that is created/loaded
	 * @param button_popup	JButton, button to either create or load a database
	 * @param contentPane	Container
	 * @param databaseName	String, name of database from textField
	 * @param dbCreation 	boolean, used to check if creation of db was successful, only then table geometries is created -> createTable
	 */
	
	JFrame frame_popup;
	private JTextField textField;
	private JButton button_popup;
	Container contentPane = getContentPane();
	String databaseName;
	boolean dbCreation;
	
	/**
	 * Constructor of class PopupMenu
	 * @param button	String, name of the button that was clicked, used to determine if database should be created or loaded
	 * @param db		DatabasConnection object, object of our class DatabaseConnection
	 */
	PopupMenu (String button, final DatabaseConnection db) {
		super();
		//contentPane.setBackground(Color.LIGHT_GRAY);
		//contentPane.setLayout(new GridLayout(0,2));
		String buttonLabel = "";
		frame_popup = new JFrame();
		frame_popup.setBackground(Color.LIGHT_GRAY);
		frame_popup.setLayout(new GridLayout(0,2));
		frame_popup.setSize(300,100);
		//frame_popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (button == "Create Database") {
			buttonLabel = "Create Database";
		} else if (button == "Load Database")  {
			buttonLabel = "Load Database";			
		}
		else {
			buttonLabel = "Test";
		}
		frame_popup.setTitle(buttonLabel);
		
		//Textfield and Button:
		textField = new JTextField();
		button_popup = new JButton();
		
		frame_popup.add(textField);
		frame_popup.add(button_popup);
		
		//ActionListener:
		button_popup.setLabel(buttonLabel);
		button_popup.addActionListener(new ActionListener() {
			/**
			 * create or load database, value of text field is saved and used to either create database and table geometries (createDatabase and createTable methods) 
			 * or load database (connectToDatabase method)
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				String str = event.getActionCommand();
				System.out.println(str);
				//Check string/name of button
				//if db should be created:
				if (str=="Create Database")
				{
					//get value of text field for the creation of the database
					databaseName = textField.getText();
					System.out.println(databaseName);
					dbCreation = db.createDatabase(databaseName);		//creation of database
					if (dbCreation == true) {							//if successful, table geometries is created
						db.db_name = databaseName;
						db.connectToDatabase("localhost:3306", databaseName, "root", "riazdh");	//connect to new database, instead of sakila
						db.createTable();														//create table geometries
						frame_popup.setVisible(false);	
					}
				}
				//load existing database
				else if (str == "Load Database") {
					databaseName = textField.getText();
					System.out.println(databaseName);
					boolean loadDb = db.connectToDatabase("localhost:3306", databaseName, "root", "riazdh");
					if (loadDb == true) {
						frame_popup.setVisible(false);
						db.db_name = databaseName;
						JOptionPane.showMessageDialog(null, "Loading Database " + databaseName + " successful");
						//first reset canvas, so that no points of another table are displayed:
						Application.canvas.drawCommand = "p,25,100";
						Application.canvas.checkPoints = false;
						Application.canvas.repaint();
						int countPoints = db.getID("GEOMETRIES");
						//check if there is at least one point to be drawn!
						if (countPoints > 0){
							Application.canvas.drawCommand = db.getData("SELECT * FROM GEOMETRIES");
							Application.canvas.checkPoints = true;
							Application.canvas.repaint();
						}						
					} else {
						JOptionPane.showMessageDialog(null, "Loading Database " + databaseName + " was not successful!");
					}
				}
			}
		});
		
		frame_popup.show();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
