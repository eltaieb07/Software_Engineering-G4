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

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import javax.swing.tree.TreeSelectionModel;

import javax.swing.event.ChangeEvent;

import javax.swing.event.ChangeListener;

import javax.swing.event.TreeSelectionEvent;

import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;





public class ShapeDrawing extends JFrame{

	



	MyPanel panel;

	DatabaseConnectivity dc;

	FileHandling fh;

	private JPanel p;

	private JPanel p2;

	private JMenuBar menubar;

	public JButton button;

	public static JLabel label;

	public JButton open;

	public JButton save;

	public JButton start_edit;

	public JButton point;

	public JButton line;

	public static JButton rectangle;

	public JButton polygon;

	public JButton select;

	public JButton clear;

	public JButton moveUp, moveDown, moveLeft, moveRight;

	public JButton delete = new JButton();

	public JButton range_query = new JButton();

	public static int current_shape; public static String moveID = "z";

	public JTree tree;

	public JButton db_conn;

	

	private JPanel db_panel;

	private JTextField txtLocalhost;

	private JTextField textPort;

	private JTextField txtdb;

	private JTextField textUsername;

	private JTextField password;

	

	//private static JFrame f;

	public ShapeDrawing(){

		

		//this.setSize(800, 800);

		getContentPane().setLayout(null);

		//getContentPane().setBackground(Color.GRAY);

		panel  = new MyPanel();

		panel.setBounds(200, 50, 1160, 642);

		panel.setBackground(Color.WHITE);

		p =  new JPanel();

		p.setBounds(0, 50, 200, 642);

		p2 = new JPanel();

		p2.setBounds(0, 0, 1373, 50);



		this.setTitle("My Frame");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        

        

        p.setBorder((BorderFactory.createLineBorder(Color.LIGHT_GRAY)));

        p2.setBorder((BorderFactory.createEtchedBorder(EtchedBorder.RAISED)));

        panel.setBorder((BorderFactory.createLineBorder(Color.LIGHT_GRAY)));

        

    

        getContentPane().add(panel);

         //f.add(panel);

        // this.add(panel, BorderLayout.EAST);

        getContentPane().add(p);

        p.setLayout(null);

   

        getContentPane().add(p2);

        //p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

        

        

        

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
        
        UIManager.put("Tree.leafIcon", new ImageIcon("D:\\Hochschule\\Geomatics\\Programming\\color.png"));
	    UIManager.put("Tree.openIcon", new ImageIcon("D:\\Hochschule\\Geomatics\\Programming\\close_kl.png"));
	    UIManager.put("Tree.closedIcon", new ImageIcon("D:\\Hochschule\\Geomatics\\Programming\\open_kl.png"));


        

        

        tree = new JTree(objectsRoot);

        tree.setBounds(20, 11, 150, 250);

        p.add(tree);

        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        tree.addTreeSelectionListener(new SelectionListener());



         

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

         

		p2.setLayout(null);

		

		open = new JButton();

		open.setBounds(7, 7, 35, 35);

		ImageIcon icon_open = new ImageIcon("..\\Drawing\\icons\\open_file.png");

		open.setIcon(icon_open);

		open.setToolTipText("Load File");

		open.addActionListener(ac);

		p2.add(open);

		

		save = new JButton();

		save.setBounds(50, 7, 35, 35);

		ImageIcon icon_save = new ImageIcon("..\\Drawing\\icons\\save_to_file.png");

		save.setIcon(icon_save);

		save.setToolTipText("Save to File");

		save.addActionListener(ac);

		p2.add(save);

		

		start_edit = new JButton();

		start_edit.setBounds(100, 7, 35, 35);

		ImageIcon icon_edit = new ImageIcon("..\\Drawing\\icons\\Edit.jpg");

		start_edit.setIcon(icon_edit);

		start_edit.setToolTipText("Start/Stop Editing");

		start_edit.addActionListener(new Listener());

		p2.add(start_edit);

		

		point = new JButton();

		point.setBounds(250, 7, 35, 35);

		point.setBackground(Color.WHITE);

		ImageIcon icon_point = new ImageIcon("..\\Drawing\\icons\\Point.png");

		point.setIcon(icon_point);

		point.setToolTipText("Add Point");

		point.addActionListener(ac);

		p2.add(point);

		point.setEnabled(false);

		

		line = new JButton();

		line.setBounds(200, 7, 35, 35);

		line.setBackground(Color.WHITE);

		ImageIcon icon_line = new ImageIcon("..\\Drawing\\icons\\Line.png");

		line.setIcon(icon_line);

		line.setToolTipText("Add Line");

		line.addActionListener(ac);

		p2.add(line);

		line.setEnabled(false);

		

		rectangle = new JButton();

		rectangle.setBounds(150, 7, 35, 35);

		rectangle.setBackground(Color.WHITE);

		ImageIcon icon_rect = new ImageIcon("..\\Drawing\\icons\\Rectangle.png");

		rectangle.setIcon(icon_rect);

		rectangle.setToolTipText("Add Rectangle");

		rectangle.addActionListener(ac);

		p2.add(rectangle);

		rectangle.setEnabled(false);

		

		polygon = new JButton();

		polygon.setBounds(300, 7, 35, 35);

		polygon.setBackground(Color.WHITE);

		ImageIcon icon_poly = new ImageIcon("..\\Drawing\\icons\\Polygon.png");

		polygon.setIcon(icon_poly);

		polygon.setToolTipText("Add Polygon");

		polygon.addActionListener(ac);

		p2.add(polygon);

		polygon.setEnabled(false);

	

		clear = new JButton();

		clear.setBounds(350, 7, 35, 35);

		clear.setBackground(Color.WHITE);

		ImageIcon icon_clear = new ImageIcon("..\\Drawing\\icons\\Clear.png");

		clear.setIcon(icon_clear);

		clear.setToolTipText("Clear Display");

		clear.addActionListener(ac);

		p2.add(clear);	

	

		select = new JButton();

		select.setBounds(400, 7, 35, 35);

		select.setBackground(Color.WHITE);

		ImageIcon icon_select = new ImageIcon("..\\Drawing\\icons\\select.jpg");

		select.setIcon(icon_select);

		select.setToolTipText("Select Feature");

		select.addActionListener(ac);

		p2.add(select);

		select.setEnabled(false);

		

		

		moveUp = new JButton();

		moveUp.setBounds(450, 7, 35, 35);

		moveUp.setBackground(Color.WHITE);

		ImageIcon icon_moveUp = new ImageIcon("..\\Drawing\\icons\\up.jpg");

		moveUp.setIcon(icon_moveUp);

		moveUp.setToolTipText("Move Up");

		moveUp.addActionListener(ac);

		p2.add(moveUp);

		

		moveDown = new JButton();

		moveDown.setBounds(500, 7, 35, 35);

		moveDown.setBackground(Color.WHITE);

		ImageIcon icon_moveDown = new ImageIcon("..\\Drawing\\icons\\down.jpg");

		moveDown.setIcon(icon_moveDown);

		moveDown.setToolTipText("Move Down");

		moveDown.addActionListener(ac);

		p2.add(moveDown);

		

		moveRight = new JButton();

		moveRight.setBounds(550, 7, 35, 35);

		moveRight.setBackground(Color.WHITE);

		ImageIcon icon_moveRight = new ImageIcon("..\\Drawing\\icons\\right.jpg");

		moveRight.setIcon(icon_moveRight);

		moveRight.setToolTipText("Move Right");

		moveRight.addActionListener(ac);

		p2.add(moveRight);

		

		moveLeft = new JButton();

		moveLeft.setBounds(600, 7, 35, 35);

		moveLeft.setBackground(Color.WHITE);

		ImageIcon icon_moveLeft = new ImageIcon("..\\Drawing\\icons\\left.jpg");

		moveLeft.setIcon(icon_moveLeft);

		moveLeft.setToolTipText("Move Left");

		moveLeft.addActionListener(ac);

		p2.add(moveLeft);



	    delete.setBounds(650, 7, 35, 35);

	    delete.setBackground(Color.WHITE);

		ImageIcon icon_delete = new ImageIcon("..\\Drawing\\icons\\delete.png");

		delete.setIcon(icon_delete);

		delete.setToolTipText("Delete Selected Object");

		delete.addActionListener(ac);

		p2.add(delete);



	    

	    range_query.setBounds(700, 7, 35, 35);

	    range_query.setBackground(Color.WHITE);

		ImageIcon icon_range_query = new ImageIcon("..\\Drawing\\icons\\range_query.png");

		range_query.setIcon(icon_range_query);

		range_query.setToolTipText("Select multiple objects");

		range_query.addActionListener(ac);

		p2.add(range_query);



         

	     label = new JLabel();

	     label.setBounds(750, 7, 320, 35);

	     p2.add(label);

	     label.setText("Editing Stopped...");

	     

	     db_conn = new JButton();

	     db_conn.setBounds(1300, 7, 35, 35);

	     p2.add(db_conn);

	     db_conn.setBackground(Color.WHITE);

	     db_conn.setIcon(new ImageIcon("..\\Drawing\\icons\\db_connection.png"));

	     db_conn.setToolTipText("Database Connectivity");

	     db_conn.addActionListener(ac);

	     p2.add(db_conn);

	  

	     this.setVisible(true);

        

    }



	

	ActionListener ac =  new ActionListener(){

		public void actionPerformed(ActionEvent actionEvent) {



	          if(actionEvent.getSource() == point){

	        	  current_shape = 1;

	        	  System.out.println("point");

	        	  label.setText("Click to Draw Point");

	          }

	          if(actionEvent.getSource() == line){

	        	  current_shape = 2;

	        	  System.out.println("line");

	        	  label.setText("Drag to Draw a Straigt Line");

	          }

	          if(actionEvent.getSource() == rectangle){

	        	  current_shape = 3;

	        	  System.out.println("rectangle");

	        	  label.setText("Drag to Draw Rectangle");

	          }

	          if(actionEvent.getSource() == polygon){

	        	  current_shape = 4;

	        	  System.out.println("polygon");

	        	  label.setText("Click to draw vertices of polygon and double click to close the polygon");

	          }

	          if(actionEvent.getSource() == clear){

	        	  panel.clear_display();

	        	  current_shape = 0;

	        	  System.out.println("Display Cleared...");

	        	  label.setText("Display Cleared...");

	          }

	          if (actionEvent.getSource() == open){

	        	  //FileHandling.ReadFile();

	        	  if(dc!=null){

	        		  if (dc.testConnection())

	        		  {

	        			  dc.loadDb();

				          panel.update_drawing();

				          label.setText("Shapes loaded from Database!");

	        		  }

	        		  else{

	        			  JOptionPane.showMessageDialog(null, "Problem in Connection to the Database");

	        		  }

		        	  

	        	  }

	        	  else{

	        		  JOptionPane.showMessageDialog(null, "Please first connect with the Database");

	        	  }

	          }

	          if (actionEvent.getSource() == save){

	        	  //FileHandling.WriteFile();

	        	  if(dc!=null){

	        		  if (dc.testConnection())

	        		  {

	        			  dc.saveToDb();

			        	  label.setText("Shapes Saved into Database!");

	        		  }

	        		  else{

	        			  JOptionPane.showMessageDialog(null, "Problem in Connection to the Database");

	        		  }

		        	  

	        	  }

	        	  else{

	        		  JOptionPane.showMessageDialog(null, "Please first connect with the Database");

	        	  }

	          }

	          if (actionEvent.getSource() == select){

	        	  label.setText("Click on the shape to select");

	        	  current_shape = 10;

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

	        	  current_shape = 5;

	          }

	          

	          if (actionEvent.getSource() == db_conn){

	        	  JFrame frame2 = new JFrame("Add Database Connection");

	        	  frame2.setSize(300,400);

	        	  //frame2.setLayout(null);

	        	 

	        	  

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

					    		//System.out.println(host);

					    		JOptionPane.showMessageDialog(null, "Connection Established!");

					    	}

					    	else{

					    		//System.out.println(host);

					    		JOptionPane.showMessageDialog(null, "Connection Unsuccessful!");

					    		dc=null;

					    	}

					    }

					});



	        	  

		      		 

	        	  frame2.setLocationRelativeTo(null);   // Opens the frame in center

	        	  frame2.setVisible(true);

	          }

	          

	      }

	};

	



	

	class Listener implements ActionListener

	{

		public void actionPerformed(ActionEvent e)

		{

			if (polygon.isEnabled()){

				polygon.setEnabled(false);

				

			}

			else{

				polygon.setEnabled(true);

			}

			if (rectangle.isEnabled()){

				rectangle.setEnabled(false);

				

			}

			else{

				rectangle.setEnabled(true);

			}

			if (line.isEnabled()){

				line.setEnabled(false);

				

			}

			else{

				line.setEnabled(true);

			}

			if (point.isEnabled()){

				point.setEnabled(false);

				label.setText("Editing Stopped...");

				current_shape =0;

			}

			else{

				point.setEnabled(true);

				label.setText("Editing Started...");

			}

			if (select.isEnabled()){

				select.setEnabled(false);

				

			}

			else{

				select.setEnabled(true);

			}

			

		}

		

	}

	class SelectionListener implements TreeSelectionListener {



	  	  public void valueChanged(TreeSelectionEvent se) {

	  	    JTree tree = (JTree) se.getSource();

	  	    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

	  	    if (selectedNode.isLeaf()) {

	  	    	  JFrame frame = new JFrame("JColorChooser Popup");

	  	    	  JColorChooser colorChooser = new JColorChooser();



		  	      ColorSelectionModel model = colorChooser.getSelectionModel();

		  	      ChangeListener changeListener = new ChangeListener() {

		  	        public void stateChanged(ChangeEvent changeEvent) {

		  	          Color outlineColor = colorChooser.getColor();

		  	          label.setForeground(outlineColor);

		  	          if (selectedNode.getParent().toString() == "Points"){

		  	        	  for (ShapeItems item : panel.shapes){

		  	        		  if (item.getName().equals("point")){

		  	        			  item.setColor(outlineColor);

		  	        		  }

		  	        	  }

		  	        	  

		  	          }

		  	          if (selectedNode.getParent().toString() == "Lines"){

		  	        	  for (ShapeItems item : panel.shapes){

		  	        		  if (item.getName().equals("line")){

		  	        			  item.setColor(outlineColor);

		  	        		  }

		  	        	  }

		  	        	  

		  	          }

		  	          if (selectedNode.getParent().toString() == "Rectangles"){

		  	        	  for (ShapeItems item : panel.shapes){

		  	        		  if (item.getName().equals("rectangle")){

		  	        			  item.setColor(outlineColor);

		  	        		  }

		  	        	  }

		  	        	  

		  	          }

		  	          if (selectedNode.getParent().toString() == "Polygons"){

		  	        	  for (ShapeItems item : panel.shapes){

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

		  	        //JColorChooser.showDialog(null, "Choose Color", null);

		  	       // Color newForegroundColor = colorChooser.getColor();

		  	    }

		  }

	}
	/*DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
	Icon closedIcon = new ImageIcon("D:\\Hochschule\\Geomatics\\Programming\\Point.png");
	Icon openIcon = new ImageIcon("D:\\Hochschule\\Geomatics\\Programming\\Point.png");
	Icon leafIcon = new ImageIcon("D:\\Hochschule\\Geomatics\\Programming\\Point.png");
	renderer.setClosedIcon(closedIcon);
	renderer.setOpenIcon(openIcon);
	renderer.setLeafIcon(leafIcon); */
	
	//URL resource = logaff.class.getResource(IMAGE);


/*	private class TreeRenderer extends DefaultTreeCellRenderer {
	@Override
	public Component getTreeCellRendererComponent(JTree tree,
	    Object value, boolean selected, boolean expanded,
	    boolean leaf, int row, boolean hasFocus) {
	        super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
	        DefaultMutableTreeNode pointNode = (DefaultMutableTreeNode) value;
	        if (tree.getModel().getRoot().equals(pointNode)) {
	            setIcon(icon_point);
	     
	        } 
	        return this;
	}}*/
/*	private class TreeRenderer extends DefaultTreeCellRenderer {
		 @Override
		    public Component getTreeCellRendererComponent(JTree tree, Object value,
		            boolean selected, boolean expanded, boolean leaf, int row,
		            boolean hasFocus) {
		        super.getTreeCellRendererComponent(tree, value, selected, expanded,
		                leaf, row, hasFocus);
		        Object pointNode = ((DefaultMutableTreeNode) value).getUserObject();
		        if (pointNode instanceof ReportTemplate) {
		            setIcon(((ReportTemplate) pointNode).getIcon());
		        }
		        return this;
		    } 
	 
}*/


	//ImageIcon icon_open = new ImageIcon("..\\Drawing\\icons\\open_file.png");



	  


	public static void main(String a[]){



		new ShapeDrawing();

		

    }

}