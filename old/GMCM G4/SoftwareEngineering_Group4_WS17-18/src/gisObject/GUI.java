/**
 * 
 */
package gisObject;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.colorchooser.ColorSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 * @author Shahab Ahmed, Fabienne
 * GUI class extends JFrame class
 */
public class GUI extends JFrame{

	/**
	 * serial VersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * DrawingPanel class, every thing happening here in this panel
	 */
	DrawingPanel panel; 
	DatabaseConnectivity dc; 
	FileHandling fh; 
	/**
	 * this panel used as a container to hold the Jtree of the geometry properties
	 */
	private JPanel p;
	/**
	 * this panel is used to hold the functionalities buttons
	 */
	private JPanel p2;
	/**
	 * the tool menu
	 */
	private JMenuBar menubar;
	/**
	 * JLable to create labels
	 */
	public static JLabel label;
	/**
	 * to load from database
	 */
	public JButton open;
	/**
	 * to save into database
	 */
	public JButton save;
	/**
	 * to start and stop editing
	 */
	public JButton start_edit;
	/**
	 * to draw points
	 */
	public JButton point;
	/**
	 * to draw lines
	 */
	public JButton line;
	/**
	 * to draw rectangles
	 */
	public static JButton rectangle;
	/**
	 * to draw polygon
	 */
	public JButton polygon;
	/**
	 * to select geometry
	 */
	public JButton select;
	/**
	 * to clear display
	 */
	public JButton clear;
	/**
	 * to move objects
	 */
	public JButton moveUp, moveDown, moveLeft, moveRight;
	public JButton delete = new JButton();
	public JButton range_query = new JButton();
	public static int selected_button; public static String moveID = "z";
	/**
	 * to change geometry properties
	 */
	public JTree tree;
	/**
	 * to connect to database
	 */
	public JButton db_conn;
	private JPanel db_panel;
	/**
	 * to make a text field
	 */
	private JTextField txtLocalhost, textPort, txtdb, textUsername, password;

	/**
	 * the main GUI Constructor 
	 */
	public GUI(){
		getContentPane().setLayout(null);
		panel  = new DrawingPanel(); // main panel which hold DrawingPanel functionalities
		panel.setBounds(200, 50, 1160, 642);
		panel.setBackground(Color.WHITE);
		p =  new JPanel(); // this is the properties panel to hold the JTree of changing geometry properties
		p.setBounds(0, 50, 200, 642);
		p2 = new JPanel();
		p2.setBounds(0, 0, 1373, 50);

		this.setTitle("SE_G4_WS17-18");
		ImageIcon icon = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\logo.png");
		setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        p.setBorder((BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
        p2.setBorder((BorderFactory.createEtchedBorder(EtchedBorder.RAISED)));
        panel.setBorder((BorderFactory.createLineBorder(Color.LIGHT_GRAY)));

        getContentPane().add(panel);
        getContentPane().add(p);
        p.setLayout(null);
        p.setBackground(Color.WHITE);
        getContentPane().add(p2);

        DefaultMutableTreeNode objectsRoot = new DefaultMutableTreeNode("Shapes");

        //create the child nodes
        DefaultMutableTreeNode pointNode = new DefaultMutableTreeNode("Points");
        DefaultMutableTreeNode lineNode = new DefaultMutableTreeNode("Lines");
        DefaultMutableTreeNode rectangleNode = new DefaultMutableTreeNode("Rectangles");
        DefaultMutableTreeNode polygonNode = new DefaultMutableTreeNode("Polygons");

        //add the child nodes to the root node
        objectsRoot.add(pointNode);
        objectsRoot.add(lineNode);
        objectsRoot.add(rectangleNode);
        objectsRoot.add(polygonNode);

        DefaultMutableTreeNode ColorChooserPNodeF = new DefaultMutableTreeNode("Color");
        DefaultMutableTreeNode ColorChooserLNodeF = new DefaultMutableTreeNode("Color");
        DefaultMutableTreeNode ColorChooserRNodeF = new DefaultMutableTreeNode("Color");
        DefaultMutableTreeNode ColorChooserPolyNodeF = new DefaultMutableTreeNode("Color");

        pointNode.add(ColorChooserPNodeF);
        lineNode.add(ColorChooserLNodeF);
        rectangleNode.add(ColorChooserRNodeF);
        polygonNode.add(ColorChooserPolyNodeF);
        UIManager.put("Tree.leafIcon", new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\color.png"));
	    UIManager.put("Tree.openIcon", new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\close.png"));
	    UIManager.put("Tree.closedIcon", new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\open.png"));
	    /**
	     * constructing JTree
	     */
        tree = new JTree(objectsRoot);
        tree.setBounds(20, 11, 150, 600);
        p.add(tree);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(new SelectionListener());
        /**
         * constructing menubar
         * file menu with "load, save to, and exit" menu items
         * edit menu with "dtata" menu item
         * view menu 
         * help menu with "about" menu item
         */
        menubar = new JMenuBar();
		this.setJMenuBar(menubar);

		JMenu file = new JMenu("File");
		menubar.add(file);

		JMenuItem load  = new JMenuItem("Load from File");
		JMenuItem save_to  = new JMenuItem("Save to File");
		JMenuItem exit = new JMenuItem("Exit");

		fh = new FileHandling();
		load.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
	            fh.ReadFile();
	            panel.update_drawing();
	            label.setText("Shapes loaded from File!");
		    }
		});

		save_to.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
	            fh.WriteFile();
	            label.setText("Shapes saved into File!");
		    }
		});

		exit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
	            System.exit(0);
			    }
			});

		file.add(load);
		file.add(save_to);
		file.add(exit);

		JMenu edit = new JMenu("Edit");
		menubar.add(edit);
		
		JMenuItem data = new JMenu("Data");
		edit.add(data);

		JMenu view = new JMenu("View");
		menubar.add(view);

		JMenu help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Help:\n"
						+ "1.) Drawing shapes and using the different functionalities works with no need for database connection\n"
						+ "2.) If you are interacting with the drawing shapes buttons, you have to activate them using Start/Stop button \n"
						+ "Geometries can be selected and deleted by a single click and then pressing the select and delete button, respectively\n"
						+ "3.) You can deal with the dtabase only when you are connected with your local database\n"
						+ "You can save to database and load from it as well all shape specifications name, color, and geometry\n"
						+ "4.) You can save your work as csv from file menu and laod a csv file also\n"
						+ "\n"
						+ "\n"
						+ "for more information please check the provided Readme file or contact us at\n"
						+ "ahmed.shahab123@gmail.com\n"
						+ "a.a.eltaieb@gmail.com\n"
						+ "FabienneHeise@gmx.de\n"
						+ "Edyzena@yahoo.co.uk");
			}
		});
		help.add(about);

		p2.setLayout(null);
		/**
		 * load from database button
		 */
		open = new JButton();
		open.setBounds(7, 7, 35, 35);
		ImageIcon icon_open = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\loaddb.png");
		open.setIcon(icon_open);
		open.setToolTipText("Load File");
		open.addActionListener(ac);
		p2.add(open);
		/**
		 * save to database button
		 */
		save = new JButton();
		save.setBounds(50, 7, 35, 35);
		ImageIcon icon_save = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\savefile.png");
		save.setIcon(icon_save);
		save.setToolTipText("Save to File");
		save.addActionListener(ac);
		p2.add(save);
		/**
		 * Start/Stop button
		 */
		start_edit = new JButton();
		start_edit.setBounds(100, 7, 35, 35);
		ImageIcon icon_edit = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\startstop.png");
		start_edit.setIcon(icon_edit);
		start_edit.setToolTipText("Start/Stop Editing");
		start_edit.addActionListener(new Listener());
		p2.add(start_edit);
		/**
		 * draw point button
		 */
		point = new JButton();
		point.setBounds(250, 7, 35, 35);
		point.setBackground(Color.WHITE);
		ImageIcon icon_point = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\Point.png");
		point.setIcon(icon_point);
		point.setToolTipText("Add Point");
		point.addActionListener(ac);
		p2.add(point);
		point.setEnabled(false);
		/**
		 * draw line button
		 */
		line = new JButton();
		line.setBounds(200, 7, 35, 35);
		line.setBackground(Color.WHITE);
		ImageIcon icon_line = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\Line.png");
		line.setIcon(icon_line);
		line.setToolTipText("Add Line");
		line.addActionListener(ac);
		p2.add(line);
		line.setEnabled(false);
		/**
		 * draw rectangle button
		 */
		rectangle = new JButton();
		rectangle.setBounds(150, 7, 35, 35);
		rectangle.setBackground(Color.WHITE);
		ImageIcon icon_rect = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\Rectangle.png");
		rectangle.setIcon(icon_rect);
		rectangle.setToolTipText("Add Rectangle");
		rectangle.addActionListener(ac);
		p2.add(rectangle);
		rectangle.setEnabled(false);
		/**
		 * draw polygon button
		 */
		polygon = new JButton();
		polygon.setBounds(300, 7, 35, 35);
		polygon.setBackground(Color.WHITE);
		ImageIcon icon_poly = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\Polygon.png");
		polygon.setIcon(icon_poly);
		polygon.setToolTipText("Add Polygon");
		polygon.addActionListener(ac);
		p2.add(polygon);
		polygon.setEnabled(false);
		/**
		 * clear display button
		 */
		clear = new JButton();
		clear.setBounds(350, 7, 35, 35);
		clear.setBackground(Color.WHITE);
		ImageIcon icon_clear = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\Clear.png");
		clear.setIcon(icon_clear);
		clear.setToolTipText("Clear Display");
		clear.addActionListener(ac);
		p2.add(clear);	
		/**
		 * select geometry button
		 */
		select = new JButton();
		select.setBounds(400, 7, 35, 35);
		select.setBackground(Color.WHITE);
		ImageIcon icon_select = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\select.png");
		select.setIcon(icon_select);
		select.setToolTipText("Select Feature");
		select.addActionListener(ac);
		p2.add(select);
		select.setEnabled(false);
		/**
		 * move up button
		 */
		moveUp = new JButton();
		moveUp.setBounds(450, 7, 35, 35);
		moveUp.setBackground(Color.WHITE);
		ImageIcon icon_moveUp = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\up.png");
		moveUp.setIcon(icon_moveUp);
		moveUp.setToolTipText("Move Up");
		moveUp.addActionListener(ac);
		p2.add(moveUp);
		/**
		 * move down button
		 */
		moveDown = new JButton();
		moveDown.setBounds(500, 7, 35, 35);
		moveDown.setBackground(Color.WHITE);
		ImageIcon icon_moveDown = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\down.png");
		moveDown.setIcon(icon_moveDown);
		moveDown.setToolTipText("Move Down");
		moveDown.addActionListener(ac);
		p2.add(moveDown);
		/**
		 * move to right button
		 */
		moveRight = new JButton();
		moveRight.setBounds(550, 7, 35, 35);
		moveRight.setBackground(Color.WHITE);
		ImageIcon icon_moveRight = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\right.png");
		moveRight.setIcon(icon_moveRight);
		moveRight.setToolTipText("Move Right");
		moveRight.addActionListener(ac);
		p2.add(moveRight);
		/**
		 * move to left button
		 */
		moveLeft = new JButton();
		moveLeft.setBounds(600, 7, 35, 35);
		moveLeft.setBackground(Color.WHITE);
		ImageIcon icon_moveLeft = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\left.png");
		moveLeft.setIcon(icon_moveLeft);
		moveLeft.setToolTipText("Move Left");
		moveLeft.addActionListener(ac);
		p2.add(moveLeft);
		/**
		 * delete selected geometry button
		 */
	    delete.setBounds(650, 7, 35, 35);
	    delete.setBackground(Color.WHITE);
		ImageIcon icon_delete = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\deleteselected.png");
		delete.setIcon(icon_delete);
		delete.setToolTipText("Delete Selected Object");
		delete.addActionListener(ac);
		p2.add(delete);
		/**
		 * select multiple objects button
		 */
	    range_query.setBounds(700, 7, 35, 35);
	    range_query.setBackground(Color.WHITE);
		ImageIcon icon_range_query = new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\selectmultipleobjects.png");
		range_query.setIcon(icon_range_query);
		range_query.setToolTipText("Select multiple objects");
		range_query.addActionListener(ac);
		p2.add(range_query);
		/**
		 * create lable
		 */
		label = new JLabel();
		label.setBounds(750, 7, 320, 35);
	    p2.add(label);
	    label.setText("Editing Stopped...");
	    /**
	     * database connectivity button
	     */
	    db_conn = new JButton();
	    db_conn.setBounds(1300, 7, 35, 35);
	    p2.add(db_conn);
	    db_conn.setBackground(Color.WHITE);
	    db_conn.setIcon(new ImageIcon("..\\SoftwareEngineering_Group4_WS17-18\\icons\\dbconnectivity.png"));
	    db_conn.setToolTipText("Database Connectivity");
	    db_conn.addActionListener(ac);
	    p2.add(db_conn);

	    this.setVisible(true);
    } //end of the GUI constructor
	/**
	 * the action listener for all buttons stands on their own
	 */
	ActionListener ac =  new ActionListener(){
		public void actionPerformed(ActionEvent actionEvent) {
	          if(actionEvent.getSource() == point){
	        	  selected_button = 1;
	        	  System.out.println("point");
	        	  label.setText("Click to Draw Point");
	          }
	          if(actionEvent.getSource() == line){
	        	  selected_button = 2;
	        	  System.out.println("line");
	        	  label.setText("Drag to Draw a Straigt Line");
	          }
	          if(actionEvent.getSource() == rectangle){
	        	  selected_button = 3;
	        	  System.out.println("rectangle");
	        	  label.setText("Drag to Draw Rectangle");
	          }
	          if(actionEvent.getSource() == polygon){
	        	  selected_button = 4;
	        	  System.out.println("polygon");
	        	  label.setText("Click to draw vertices of polygon and double click to close the polygon");
	          }
	          if(actionEvent.getSource() == clear){
	        	  panel.clear_display();
	        	  selected_button = 0;
	        	  System.out.println("Display Cleared...");
	        	  label.setText("Display Cleared...");
	          }
	          if (actionEvent.getSource() == open){
	        	  if(dc!=null){
	        		  if (dc.testConnection())
	        		  {
	        			  dc.loadDb();
				          panel.update_drawing();
				          label.setText("Shapes loaded from Database!");
	        		  } else{
	        			  JOptionPane.showMessageDialog(null, "Problem in Connection to the Database");
	        		  }
	        	  } else{
	        		  JOptionPane.showMessageDialog(null, "Please first connect with the Database");
	        	  }
	          }
	          if (actionEvent.getSource() == save){
	        	  if(dc!=null){
	        		  if (dc.testConnection())
	        		  {
	        			  dc.saveToDb();
			        	  label.setText("Shapes Saved into Database!");
	        		  } else{
	        			  JOptionPane.showMessageDialog(null, "Problem in Connection to the Database");
	        		  }
	        	  } else{
	        		  JOptionPane.showMessageDialog(null, "Please first connect with the Database");
	        	  }
	          }   
	          if (actionEvent.getSource() == select){
	        	  label.setText("Click on the shape to select");
	        	  selected_button = 10;
	          }
	          if (actionEvent.getSource() == moveUp){
	        	  moveID = "up";
	        	  panel.move();  
	          }
	          if (actionEvent.getSource() == moveDown){
	        	  moveID = "down";
	        	  panel.move();
	          }
	          if (actionEvent.getSource() == moveLeft){
	        	  moveID = "left";
	        	  panel.move();
	          }
	          if (actionEvent.getSource() == moveRight){
	        	  moveID = "right";
	        	  panel.move();
	          }
	          if (actionEvent.getSource() == delete){
	        	  panel.delete();
	          }
	          if (actionEvent.getSource() == range_query){
	        	  selected_button = 5;
	          }
	          if (actionEvent.getSource() == db_conn){
	        	  JFrame frame2 = new JFrame("Add Database Connection");
	        	  frame2.setSize(300,400);
	        	  db_panel = new JPanel();
	        	  frame2.add(db_panel);
	      	      db_panel.setLayout(null);
	        	  JLabel lblHost = new JLabel("Host");
		      	  lblHost.setBounds(61, 76, 64, 14);
		          db_panel.add(lblHost);
		      	  JLabel lblPort = new JLabel("Port");
		      	  lblPort.setBounds(61, 117, 64, 14);
		          db_panel.add(lblPort);
		      	  JLabel lblDatabase = new JLabel("Database");
		      	  lblDatabase.setBounds(61, 159, 64, 14);
		      	  db_panel.add(lblDatabase);
		      	  JLabel lblUsername = new JLabel("Username");
		          lblUsername.setBounds(61, 197, 64, 14);
		      	  db_panel.add(lblUsername);		      		
		      	  JLabel lblPassword = new JLabel("Password");
		      	  lblPassword.setBounds(61, 243, 64, 14);
		      	  db_panel.add(lblPassword);    		
		      	  txtLocalhost = new JTextField();
		      	  txtLocalhost.setText("127.0.0.1");
		      	  txtLocalhost.setBounds(135, 73, 86, 20);
		          db_panel.add(txtLocalhost);
		      	  txtLocalhost.setColumns(50);
		      	  textPort = new JTextField();
		          textPort.setText("5432");
		      	  textPort.setBounds(135, 114, 86, 20);
		      	  db_panel.add(textPort);
		      	  textPort.setColumns(50);
		      	  txtdb = new JTextField();
		      	  txtdb.setText("java_db");
		      	  txtdb.setBounds(135, 156, 86, 20);
		      	  db_panel.add(txtdb);
		      	  txtdb.setColumns(50);
		      	  textUsername = new JTextField();
		      	  textUsername.setBounds(135, 194, 86, 20);
		      	  db_panel.add(textUsername);
		      	  textUsername.setColumns(50);
		      	  password = new JTextField();
		      	  password.setBounds(135, 240, 86, 20);
		      	  db_panel.add(password);
		      	  password.setColumns(50);
		      	  JButton btnClose = new JButton("Close");
		      	  btnClose.setBounds(161, 304, 74, 23);
		      	  db_panel.add(btnClose);
		      	  btnClose.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent ev) {
						  frame2.setVisible(false);
					  }
				  });
		          JButton btnConnect = new JButton("Connect");
		      	  btnConnect.setBounds(61, 304, 94, 23);
		      	  db_panel.add(btnConnect);
		      	  btnConnect.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent ev) {
					      String host = "jdbc:postgresql://"+txtLocalhost.getText()+":"+textPort.getText()+"/"+txtdb.getText(); 
					      dc = new DatabaseConnectivity(host,textUsername.getText(),password.getText());
					      System.out.println(host);
					      if(dc.testConnection()){
					    	  JOptionPane.showMessageDialog(null, "Connection Established!");
					      } else{
					    	  JOptionPane.showMessageDialog(null, "Connection Unsuccessful!");
					    	  dc=null;
					    	}
					  }
				  });
	        	  frame2.setLocationRelativeTo(null);   // Opens the frame in center
	        	  frame2.setVisible(true);
	          }
	      }
	}; //end of buttons action listener
	/**
	 * Start/Stop button action listener
	 * to enable or disable point, line, rectangle, and polygon buttons
	 */
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (polygon.isEnabled()){
				polygon.setEnabled(false);
			} else{
				polygon.setEnabled(true);
			}
			if (rectangle.isEnabled()){
				rectangle.setEnabled(false);
			} else{
				rectangle.setEnabled(true);
			}
			if (line.isEnabled()){
				line.setEnabled(false);
			} else{
				line.setEnabled(true);
			}
			if (point.isEnabled()){
				point.setEnabled(false);
				label.setText("Editing Stopped...");
				selected_button =0;
			} else{
				point.setEnabled(true);
				label.setText("Editing Started...");
			}
			if (select.isEnabled()){
				select.setEnabled(false);		
			} else{
				select.setEnabled(true);
			}			
		}		
	}// end of Start/Stop listener
	/**
	 * JTree listener
	 */
	class SelectionListener implements TreeSelectionListener {
	  	  public void valueChanged(TreeSelectionEvent se) {
	  	    JTree tree = (JTree) se.getSource();
	  	    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	  	    if (selectedNode.isLeaf()) {                            //set the color chooser
	  	    	  JFrame frame = new JFrame("JColorChooser Popup");
	  	    	  JColorChooser colorChooser = new JColorChooser();
		  	      ColorSelectionModel model = colorChooser.getSelectionModel();
		  	      ChangeListener changeListener = new ChangeListener() {
		  	        public void stateChanged(ChangeEvent changeEvent) {
		  	          Color outlineColor = colorChooser.getColor();
		  	          if (selectedNode.getParent().toString() == "Points"){
		  	        	  for (ShapeItems item : DrawingPanel.shapes){
		  	        		  if (item.getName().equals("point")){
		  	        			  item.setColor(outlineColor);
		  	        		  }
		  	        	  }		  	        
		  	          }
		  	          if (selectedNode.getParent().toString() == "Lines"){
		  	        	  for (ShapeItems item : DrawingPanel.shapes){
		  	        		  if (item.getName().equals("line")){
		  	        			  item.setColor(outlineColor);
		  	        		  }
		  	        	  }		  	        	  
		  	          }
		  	          if (selectedNode.getParent().toString() == "Rectangles"){
		  	        	  for (ShapeItems item : DrawingPanel.shapes){
		  	        		  if (item.getName().equals("rectangle")){
		  	        			  item.setColor(outlineColor);
		  	        		  }
		  	        	  }		  	        	  
		  	          }
		  	          if (selectedNode.getParent().toString() == "Polygons"){
		  	        	  for (ShapeItems item : DrawingPanel.shapes){
		  	        		  if (item.getName().equals("polygon")){
		  	        			  item.setColor(outlineColor);
		  	        		  }
		  	        	  }		  	        	  
		  	          }		  	          
		  	        }		  	       
		  	      };
		  	      model.addChangeListener(changeListener);
		  	      frame.add(colorChooser,BorderLayout.CENTER);		  	      
		  	      JButton btnClose = new JButton("Ok");
		  	      btnClose.addActionListener(new ActionListener() {
		  	    	  public void actionPerformed(ActionEvent ev) {
		  	    		  panel.update_drawing();
		  	    		  frame.setVisible(false);
		  	    	  }
		  	      });  	      
		  	      frame.add(btnClose, BorderLayout.SOUTH);
		  	      frame.pack();
		  	      frame.setLocationRelativeTo(null);   // Opens the frame in center
		  	      frame.setVisible(true);
		  	    }
		  }
	}//end of JTree listener
	/**
	 * main method
	 */
	public static void main(String a[]){
		new GUI();		
    }
}