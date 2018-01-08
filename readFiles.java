package readFiles;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class readFiles {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public readFiles(){
      prepareGUI();
   }
   public static void main(String[] args){
      readFiles  readFiles = new readFiles();      
      readFiles.showFileChooser();
   }
   private void prepareGUI(){
      mainFrame = new JFrame("Java Swing Examples");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    
      statusLabel.setSize(350,100);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }
   private void showFileChooser(){
     //creates a jFileChooser
	   JFileChooser  fileChooser = new JFileChooser();
	  //Sets the text used in the ApproveButton in the FileChooserUI. 
      fileChooser.setApproveButtonText("Select File");
      //disables the AcceptAll FileFilter
      fileChooser.setAcceptAllFileFilterUsed(false);
      //create a new button
      JButton showfileChooserButton = new JButton("Open File");
      // creates a new file extension filter
      FileNameExtensionFilter f1 = new FileNameExtensionFilter("csv files", "csv");
      //Sets the current file filter to f1.
      fileChooser.setFileFilter(f1);
      showfileChooserButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(mainFrame);
            
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
               java.io.File file = fileChooser.getSelectedFile();
               
            } else {
               System.out.println("No file selected");       
            }      
         }
      });
      controlPanel.add(showfileChooserButton);
      mainFrame.setVisible(true);  
   }
}