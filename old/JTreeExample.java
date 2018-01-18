import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JTreeExample extends JFrame {
	private JPanel p;
	private JTree tree;
	public JTreeExample(){
		getContentPane().setLayout(null);
		p =  new JPanel();
		p.setBounds(0, 50, 200, 1000);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(p);
        p.setLayout(null);
		 DefaultMutableTreeNode objectsRoot = new DefaultMutableTreeNode("Objects");
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
	        
	        DefaultMutableTreeNode ColorChooserPNodeF = new DefaultMutableTreeNode("Fill color");
	        DefaultMutableTreeNode ColorChooserLNodeF = new DefaultMutableTreeNode("Fill color");
	        DefaultMutableTreeNode ColorChooserRNodeF = new DefaultMutableTreeNode("Fill color");
	        DefaultMutableTreeNode ColorChooserPolyNodeF = new DefaultMutableTreeNode("Fill color");
	        DefaultMutableTreeNode ColorChooserPNodeS = new DefaultMutableTreeNode("Stroke color");
	        DefaultMutableTreeNode ColorChooserLNodeS = new DefaultMutableTreeNode("Stroke color");
	        DefaultMutableTreeNode ColorChooserRNodeS = new DefaultMutableTreeNode("Stroke color");
	        DefaultMutableTreeNode ColorChooserPolyNodeS = new DefaultMutableTreeNode("Stroke color");
	        pointNode.add(ColorChooserPNodeF);
	        lineNode.add(ColorChooserLNodeF);
	        rectangleNode.add(ColorChooserRNodeF);
	        polygonNode.add(ColorChooserPolyNodeF);
	        pointNode.add(ColorChooserPNodeS);
	        lineNode.add(ColorChooserLNodeS);
	        rectangleNode.add(ColorChooserRNodeS);
	        polygonNode.add(ColorChooserPolyNodeS);
	        
	      //create the tree by passing in the root node
	        tree = new JTree(objectsRoot);
	        tree.setBounds(10, 36, 150, 1000 );
	        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		    tree.addTreeSelectionListener(new SelectionListener());
	        p.add(tree);
	        
	        this.setVisible(true);
	         
	       
	}

	
	
    class SelectionListener implements TreeSelectionListener {

  	  public void valueChanged(TreeSelectionEvent se) {
  	    JTree tree = (JTree) se.getSource();
  	    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
  	    if (selectedNode.isLeaf()) {
  	        JColorChooser.showDialog(null, "Choose Color", null);
  	    }
  	  }
  	}
	public static void main (String[]a) {
		new JTreeExample();
	}
}
