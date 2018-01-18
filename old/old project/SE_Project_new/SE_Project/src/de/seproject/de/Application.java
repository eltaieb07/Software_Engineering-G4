package de.seproject.de;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.List;
import java.awt.Button;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.Box;
import javax.swing.JCheckBoxMenuItem;
import java.awt.ScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.JEditorPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Choice;
import javax.swing.JTextField;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import javax.swing.ButtonGroup;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import java.awt.Panel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

/**
 * @author ahmed, georg
 * Create date 15.10.15 
 *
 */
public class Application extends JFrame {
	/**
	 * @param frame		JFrame, frame of the application
	 * @param db		DatabaseConnection, object of the class DatabaseConnection, needed to connect to Database, create a table and write/change and get data from table
	 * @param canvas	Functionality, object of class Functionality, canvas for the drawing of the different geometry objects	
	 */

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	static DatabaseConnection db;
	static Functionality canvas;
	
	//Controll variables for radio buttons:
	static boolean rbPoint, rbLine, rbRectangle, rbDraw, rbSelect, rbDeselect, rbMove, rbDelete;
	
	//test coordinate display:
	static JLabel lblXCoordinate, lblYCoordinate;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		db = new DatabaseConnection();
		db.connectToDatabase("localhost:3306", "sakila", "root", "riazdh");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public Application() {
		
		initialize();	
	}

	/**
	 * Initialize the contents of the frame (e.g. MenuBar, Buttons in MenuBar, Panels with RadioButtons, Canvas by Functionality, ...)
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setForeground(Color.BLUE);
		frame.setBackground(Color.BLUE);
		frame.setBounds(100, 100, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("SE-Project (Winter Term 2015/16) by Ahmed, Georg, Mohamed and Rajesh");
		canvas = new Functionality();
		frame.getContentPane().add(canvas);
		
		/*Beginning of menu bar:*/
		
		//Menu bar that will contain the different bar object:
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.RED);
		menuBar.setBackground(Color.LIGHT_GRAY);
		frame.setJMenuBar(menuBar);

		//Menu bar object "file", contains buttons "New", "Save", "Open Project", "Exit":
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//If new is clicked, connection to sakila database of mySql is changed -> implement that canvas is deactivated!
		//both control variables drawCommand and checkPoints are changed to make sure that the points are "deleted"
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				db.connectToDatabase("localhost:3306", "sakila", "root", "riazdh");
				canvas.drawCommand = "p,25,100";
				canvas.checkPoints = false;
				canvas.repaint();
			}
		});
		
		//Not yet implemented, opens MessageDialog at the moment, in future possible to open e.g. textfile with name of db:
		JMenuItem mntmOpenProject = new JMenuItem("Open Project");
		mnFile.add(mntmOpenProject);
		mntmOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Open Project function has not been implemented, yet!");	
			}
		});
		
		//separation line between the buttons Open Project and Save:
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.RED);
		mnFile.add(separator);
		
		//Not yet implemented, save function, in future possible to save e.g. name of a database in a textfile:
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Save function has not been implemented, yet!");	
			}
		});
		
		//Exit-Buttons - closes the JFrame object:
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmExit);
		
		//Menu bar object "Database", contains buttons "Create Database" and "Load Database":
		JMenu mnDatabase = new JMenu("Database");
		menuBar.add(mnDatabase);
		
		JMenuItem mntmCreateDatabase = new JMenuItem("Create Database");
		mnDatabase.add(mntmCreateDatabase);
		mntmCreateDatabase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new PopupMenu("Create Database", db);
			}
			
		});
		
		JMenuItem mntmOpenDatabase = new JMenuItem("Load Database");
		mnDatabase.add(mntmOpenDatabase);
		mntmOpenDatabase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new PopupMenu("Load Database", db);	//creates a new object of the class PopupMenu
			}
			
		});
		
		JMenu mnNewMenu = new JMenu("File Handling");
		menuBar.add(mnNewMenu);
		
		// CSV-Import and Export:
		// Import:
		JMenuItem mntmAsCsvFile = new JMenuItem("Export ");
		mntmAsCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//calls method openFileChooser() of class CSV_SE_test, returns the string of the absolute path of the file:
				String filename = CSV_SE_test.openFileChooser();
				if (filename != "no File") {
					//if filename exists -> write the file:
					boolean readFile = CSV_SE_test.writeFile(filename, db);
					if (readFile == true){
						JOptionPane.showMessageDialog(null, "Success writing CSV file");
					}
					else
						JOptionPane.showMessageDialog(null, "No success writing the CSV file");
				}
				else
					JOptionPane.showMessageDialog(null, "No file selected");
				
			}
		});
		
		// Export:
		JMenuItem mntmImportToDb = new JMenuItem("Import");
		mnNewMenu.add(mntmImportToDb);
		mntmImportToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//calls method openFileChooser() of class CSV_SE_test, returns the string of the absolute path of the file:
				String filename = CSV_SE_test.openFileChooser();
				if (filename != "no File") {
					//if file exists, the entries in this file will be imported
					boolean readFile = CSV_SE_test.readFile(filename, db);
					if (readFile == true){
						JOptionPane.showMessageDialog(null, "Success reading CSV file");
					}
					else
						JOptionPane.showMessageDialog(null, "No success reading the CSV file");
				}
				else
					JOptionPane.showMessageDialog(null, "No file selected");
				
			}
		});
		mnNewMenu.add(mntmAsCsvFile);
		
		//Help button - gives important information about the program and how to use it as a MessageDialog:
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setBackground(Color.LIGHT_GRAY);
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Help:\n"
						+ "1.) Always create or lead database first! Drawing of geometries and import is only possible with database connection!\n"
						+ "2.) If you want to interact with the canvas (drawing / editing) you have to first select the respective radio button!\n"
						+ "Geometries can be selected and deleted by a single click or by dragging the mouse\n"
						+ "However, when selecting/deleting a line or rectangle with a single click, you have to keep in mind:\n"
						+ "rectangles: only possible at the top left and bottom right of the features!\n"
						+ "lines: only possible at the left and right end of the features!\n"
						+ "3.) Additional information:\n"
						+ "New-button disconnects from the database and empties the canvas,\n"
						+ "Save- as well as Open Project have not been implemented, yet, but Exit closes the program,\n"
						+ "Create / Load database opens a small popup menu where you have to enter the name of the database to create or load,\n"
						+ "with Import CSV / Export CSV you can import and export (always add .csv to the filename) your files"
						+ "Table attributes: ID, TYPE, X1, Y1, X2, Y2\n"
						+ "with:\n"
						+ "ID = INTEGER and PRIMARY KEY,\n"
						+ "TYPE = VARCHAR [(p)oint,(l)ine,(r)ectangle]\n"
						+ "X1,Y1,X2,Y2 = INTEGER,\n"
						+ "\n"
						+ "for more information please check the provided Readme file or contact us at\n"
						+ "georg.stubenrauch@gmx.de");
				
			}
		});
		menuBar.add(mntmHelp);
		frame.getContentPane().setLayout(null);
		/*End of menu bar*/
		
		/*Beginning of radiobuttons for drawing and editing*/
		
		//Panel for the radiobuttons to draw geometries:
		JPanel Drawing = new JPanel();
		Drawing.setBackground(Color.LIGHT_GRAY);
		Drawing.setBounds(0, 0, 112, 109);
		Drawing.setBorder(null);
		frame.getContentPane().add(Drawing);
		
		JRadioButton rb_Point = new JRadioButton("Point");
		rb_Point.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rb_Point);
		rb_Point.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
			@Override
			public void ancestorResized(HierarchyEvent arg0) {
			}
		});
		
		/*Explanation for point radiobutton, code structure the same for all the other radiobuttons:
		 * change the values of the different (boolean) control variables,
		 * set the corresponding variable, here rbPoint, to true and all the other to false,
		 * these are then used in the class Functionality to control the drawing!
		 */
		rb_Point.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbPoint = true;
				rbLine = false;
				rbRectangle = false;
				rbDraw = false;
				rbSelect = false;
				rbDeselect = false;
				rbMove = false;
				rbDelete = false;
			}
		});
		
		Box verticalBox = Box.createVerticalBox();
		
		//Lines:
		JRadioButton rb_Line = new JRadioButton("Line");
		rb_Line.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rb_Line);
		rb_Line.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
			@Override
			public void ancestorResized(HierarchyEvent arg0) {
			}
		});
		rb_Line.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
			}
		});
		rb_Line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbPoint = false;
				rbLine = true;
				rbRectangle = false;
				rbDraw = false;
				rbSelect = false;
				rbDeselect = false;
				rbMove = false;
				rbDelete = false;
			}
		});
		
		Box verticalBox_1 = Box.createVerticalBox();
		
		
		//Rectangles:
		JRadioButton rb_Rectangle = new JRadioButton("Rectangular");
		rb_Rectangle.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rb_Rectangle);
		rb_Rectangle.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
			@Override
			public void ancestorResized(HierarchyEvent arg0) {
			}
		});
		rb_Rectangle.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
			}
		});
		rb_Rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbPoint = false;
				rbLine = false;
				rbRectangle = true;
				rbDraw = false;
				rbSelect = false;
				rbDeselect = false;
				rbMove = false;
				rbDelete = false;
			}
		});
		
		JLabel lblDrawing = new JLabel("Drawing");
		lblDrawing.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout gl_Drawing = new GroupLayout(Drawing);
		gl_Drawing.setHorizontalGroup(
			gl_Drawing.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_Drawing.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Drawing.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(rb_Point, Alignment.LEADING)
						.addComponent(rb_Line, Alignment.LEADING)
						.addComponent(rb_Rectangle, Alignment.LEADING)
						.addComponent(lblDrawing, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_Drawing.setVerticalGroup(
			gl_Drawing.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Drawing.createSequentialGroup()
					.addGap(5)
					.addComponent(lblDrawing, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rb_Point)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rb_Line)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rb_Rectangle)
					.addGap(11))
		);
		Drawing.setLayout(gl_Drawing);
		
		//Beginning of the radiobuttons for the editing:
		//Panel for the radiobuttons to draw geometries:
		JPanel Editor = new JPanel();
		Editor.setBackground(Color.LIGHT_GRAY);
		Editor.setBounds(0, 102, 112, 169);
		frame.getContentPane().add(Editor);
		Editor.setBorder(null);
		
		//Delete:
		JRadioButton rb_Delete = new JRadioButton("Delete");
		rb_Delete.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rb_Delete);
		rb_Delete.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
			}
		});
		rb_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbPoint = false;
				rbLine = false;
				rbRectangle = false;
				rbDraw = false;
				rbSelect = false;
				rbDeselect = false;
				rbMove = false;
				rbDelete = true;				
			}
		});
		
		//Select:
		JRadioButton rb_Select = new JRadioButton("Select");
		rb_Select.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rb_Select);
		rb_Select.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
			}
		});
		rb_Select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbPoint = false;
				rbLine = false;
				rbRectangle = false;
				rbDraw = false;
				rbSelect = true;
				rbDeselect = false;
				rbMove = false;
				rbDelete = false;				
			}
		});
		
		//Unselect:
		JRadioButton rb_Deselect = new JRadioButton("Unselect");
		rb_Deselect.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rb_Deselect);
		rb_Deselect.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
			}
		});
		rb_Deselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.canvas.drawSelect = "";
				Application.canvas.checkSelect = false;
				Application.canvas.repaint();
				rbPoint = false;
				rbLine = false;
				rbRectangle = false;
				rbDraw = false;
				rbSelect = false;
				rbDeselect = true;
				rbMove = false;
				rbDelete = false;
			}
		});
		
		//Move:
		JRadioButton rb_Move = new JRadioButton("Move");
		rb_Move.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rb_Move);
		rb_Move.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
			}
		});
		rb_Move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbPoint = false;
				rbLine = false;
				rbRectangle = false;
				rbDraw = false;
				rbSelect = false;
				rbDeselect = false;
				rbMove = true;
				rbDelete = false;				
			}
		});
		
		JLabel lblEditor = new JLabel("Editor");
		lblEditor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GroupLayout gl_Editor = new GroupLayout(Editor);
		gl_Editor.setHorizontalGroup(
			gl_Editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Editor.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Editor.createParallelGroup(Alignment.LEADING)
						.addComponent(rb_Move)
						.addComponent(rb_Deselect)
						.addComponent(rb_Select)
						.addGroup(gl_Editor.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblEditor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(rb_Delete))
		)));
		gl_Editor.setVerticalGroup(
			gl_Editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Editor.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEditor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rb_Delete)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rb_Select)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rb_Deselect)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rb_Move)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		Editor.setLayout(gl_Editor);
		
		/*End of radiobuttons for the drawing and editing*/
		
		//Type of "legend" - labels that display the x- and y-values of the canvas:
		JLabel lblX = new JLabel("X:");
		lblX.setBounds(12, 318, 56, 16);
		frame.getContentPane().add(lblX);
		
		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(12, 349, 24, 16);
		frame.getContentPane().add(lblY);
		
		lblXCoordinate= new JLabel("");
		lblXCoordinate.setBounds(40, 318, 56, 16);
		frame.getContentPane().add(lblXCoordinate);
		
		lblYCoordinate = new JLabel("");
		lblYCoordinate.setBounds(40, 349, 56, 16);
		frame.getContentPane().add(lblYCoordinate);
				
		Box verticalBox_3 = Box.createVerticalBox();
		
		Box verticalBox_4 = Box.createVerticalBox();
		
		Box verticalBox_5 = Box.createVerticalBox();
		
		Box verticalBox_2 = Box.createVerticalBox();
		
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}