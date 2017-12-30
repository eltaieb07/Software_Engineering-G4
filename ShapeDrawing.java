import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ShapeDrawing extends JFrame{
	
	
	
	MyPanel panel;
	DatabaseConnectivity dc;
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
	public static int current_shape; public static String moveID = "z";
	
	//private static JFrame f;
	public ShapeDrawing(){
		
		//this.setSize(800, 800);
		getContentPane().setLayout(null);
		//getContentPane().setBackground(Color.GRAY);
		panel  = new MyPanel();
		panel.setBounds(200, 50, 1160, 700);
		p =  new JPanel();
		p.setBounds(0, 50, 200, 700);
		p2 = new JPanel();
		p2.setBounds(0, 0, 1400, 50);
		//JFrame f = new JFrame("My Frame");
		this.setTitle("My Frame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setContentPane(new ShapeDrawing());
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //this.setUndecorated(true);
        
       // Dimension dim1 = new Dimension(500,10);
        //panel.setPreferredSize(dim1);
        //panel.setBounds(200, 100, 600, 200);
        //Dimension dim2 = new Dimension(200,100);
        //p.setPreferredSize(dim2);
        //p2.setPreferredSize(new Dimension(100,50));
        p.setBorder((BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
        p2.setBorder((BorderFactory.createEtchedBorder(EtchedBorder.RAISED)));
        
        panel.setBorder((BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
        
    
        getContentPane().add(panel);
         //f.add(panel);
        // this.add(panel, BorderLayout.EAST);
        getContentPane().add(p);
        p.setLayout(null);
        
        tree = new JTree();
        tree.setBounds(20, 11, 100, 201);
        p.add(tree);
        getContentPane().add(p2);
        //p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
         
        menubar = new JMenuBar();
		this.setJMenuBar(menubar);
        
		JMenu file = new JMenu("File");
		menubar.add(file);
		
		JMenuItem load  = new JMenuItem("Load from File");
		JMenuItem save_to  = new JMenuItem("Save to File");
		JMenuItem exit = new JMenuItem("Exit");
		
		load.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
	            FileHandling.ReadFile();
	            panel.update_drawing();
	            label.setText("Shapes loaded from File!");
		    }
		});
		
		save_to.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
	            FileHandling.WriteFile();
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
	    p2.add(delete);
         
	     label = new JLabel();
	     label.setBounds(700, 7, 400, 35);
	     p2.add(label);
	     label.setText("Editing Stopped...");
	  
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
	        	  FileHandling.ReadFile();
		          panel.update_drawing();
		          label.setText("Shapes loaded from Database!");
	          }
	          if (actionEvent.getSource() == save){
	        	  FileHandling.WriteFile();
	        	  //DatabaseConnectivity.saveToDb();
	        	  label.setText("Shapes Saved into Database!");
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
	          
	      }
	};
	private JTree tree;
	
	
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
	

	public static void main(String a[]){
		
		new ShapeDrawing();
    }

}

