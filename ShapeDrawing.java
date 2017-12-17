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
	public static int current_shape;
	
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
        tree.setBounds(10, 36, 100, 150);
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
		    }
		});
		
		save_to.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
	            FileHandling.WriteFile();
	            
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
	
		select = new JButton();
		select.setBounds(350, 7, 35, 35);
		select.setBackground(Color.WHITE);
		ImageIcon icon_select = new ImageIcon("..\\Drawing\\icons\\select_curso.png");
		select.setIcon(icon_select);
		select.setToolTipText("Select Feature");
		select.addActionListener(ac);
		p2.add(select);
		
		clear = new JButton();
		clear.setBounds(400, 7, 35, 35);
		clear.setBackground(Color.WHITE);
		ImageIcon icon_clear = new ImageIcon("..\\Drawing\\icons\\Clear.png");
		clear.setIcon(icon_clear);
		clear.setToolTipText("Clear Display");
		clear.addActionListener(ac);
		p2.add(clear);
         
	     label = new JLabel();
	     label.setBounds(450, 7, 400, 35);
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
		          label.setText("Shapes loaded from File!");
	          }
	          if (actionEvent.getSource() == save){
	        	  FileHandling.WriteFile();
	        	  label.setText("Shapes Saved into File!");
	          }
	          if (actionEvent.getSource() == select){
	        	  label.setText("Click on the shape to select");
	        	  current_shape = 10;
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
			
		}
		
	}
	

	public static void main(String a[]){
		
		new ShapeDrawing();
    }
}

