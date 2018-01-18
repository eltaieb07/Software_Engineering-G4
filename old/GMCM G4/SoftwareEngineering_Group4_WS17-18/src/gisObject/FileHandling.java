/**
 * 
 */
package gisObject;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
 
/**
 * 
 * @author Shahab, Eunice
 *
 */
public class FileHandling {
 
    /**
     * This function has a file chooser to choose a CSV file to read shapes from it, then it fills shapes array list with those shapes
     */
    public void ReadFile(){
        //fileName = "E:/GEOMATICS MASTERS-HSKA/WS 2017-18/Software Engineering/Exercises/Drawing/src/ShapeCoordinates.csv";
        String line = null;
        String [] coords;
        String [] color;
    //setup file CHOOSER
    //creates a jFileChooser
        JFileChooser  fileChooser = new JFileChooser();
        //Sets the text used in the ApproveButton in the FileChooserUI. 
        fileChooser.setApproveButtonText("Select CSV File");
        //disables the AcceptAll FileFilter
        fileChooser.setAcceptAllFileFilterUsed(false);
        // creates a new file extension filter
        FileNameExtensionFilter f1 = new FileNameExtensionFilter("csv files", "csv");
        //Sets the current file filter to f1.
        fileChooser.setFileFilter(f1);       
        int returnVal = fileChooser.showOpenDialog(null);                      
        if (returnVal == JFileChooser.APPROVE_OPTION) {            
            java.io.File file = fileChooser.getSelectedFile();                     
            try
            {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                System.out.println("*****File Found*****\n");               
                while((line = bufferedReader.readLine())!= null){               
                    coords = line.split(",");  
                    if (coords[0].equals("point")){
                        color = coords[1].split("-");
                        DrawingPanel.shapes.add(new ShapeItems ("point" , new Point(Integer.parseInt(coords[2]),Integer.parseInt(coords[3])) , new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2]))));
                    }
                    if (coords[0].equals("line")){
                        color = coords[1].split("-");
                        DrawingPanel.shapes.add(new ShapeItems("line" , new Line2D.Double(new Point2D.Double(Double.parseDouble(coords[2]),Double.parseDouble(coords[3])) , new Point2D.Double(Double.parseDouble(coords[4]),Double.parseDouble(coords[5]))) , new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2]))));
                    }
                    if (coords[0].equals("rectangle")){
                        color = coords[1].split("-");
                        DrawingPanel.shapes.add(new ShapeItems ("rectangle" , new Rectangle2D.Double(Double.parseDouble(coords[2]),Double.parseDouble(coords[3]) , Double.parseDouble(coords[4]) , Double.parseDouble(coords[5])) , new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2])) ));
                    }
                    if (coords[0].equals("polygon")){
                         
                        Path2D.Double path = new Path2D.Double();
                        color = coords[1].split("-");
                        path.moveTo(Double.parseDouble(coords[2]), Double.parseDouble(coords[3]));
                        for (int i=4 ; i<coords.length; i+=2){
                            if (i==coords.length-3){
                                path.closePath();                                 
                                break;
                            }
                            path.lineTo(Double.parseDouble(coords[i]), Double.parseDouble(coords[i+1]));                            
                        }
                        DrawingPanel.shapes.add(new ShapeItems("polygon" , path , new Color(Integer.parseInt(color[0]),Integer.parseInt(color[1]),Integer.parseInt(color[2]))));                         
                    }                   
                }             
                bufferedReader.close();
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Cannot Find/Open File");
            }
            catch(IOException i){
                System.out.println("Cannot Read Line");
            }            
          } else {
              System.out.println("No file selected");       
          }        
    }
    /**
     * This function iterates through the shapes array list to retrieve all the shapes/objects and stores them in a file.
     */
    public void WriteFile(){       
        //creates a jFileChooser
           JFileChooser  fileChooser = new JFileChooser();
          //Sets the text used in the ApproveButton in the FileChooserUI. 
          fileChooser.setApproveButtonText("Save File");
          //disables the AcceptAll FileFilter
          fileChooser.setAcceptAllFileFilterUsed(false);
          // creates a new file extension filter
          FileNameExtensionFilter f1 = new FileNameExtensionFilter("csv files", "csv");
          //Sets the current file filter to f1.
          fileChooser.setFileFilter(f1);       
          if (fileChooser.showSaveDialog(null)  == JFileChooser.APPROVE_OPTION) {
              java.io.File file = fileChooser.getSelectedFile();
              String fileName = file.getName();              
                try
                {                     
                    FileWriter fileWriter = new FileWriter(file);
                    System.out.println("Writing to File..." + fileName );
                    BufferedWriter bufferedWriter= new BufferedWriter(fileWriter); 
                    int type = 0;
                    double val1 =0;
                    double val2 =0;         
                    double [] val = new double[6];
                    for (ShapeItems s: DrawingPanel.shapes){
                        if (s.getName().equals("point")){
                            Point p = s.getPoint();
                            bufferedWriter.write("point" + ',');
                            int rp = s.getColor().getRed();
                            int gp = s.getColor().getGreen();
                            int bp = s.getColor().getBlue();                            
                            String color = Integer.toString(rp)+"-"+Integer.toString(gp)+"-"+Integer.toString(bp);                             
                            bufferedWriter.write(color + ',');
                            bufferedWriter.write(Integer.toString(p.x));
                            bufferedWriter.write(',');
                            bufferedWriter.write(Integer.toString(p.y));                            
                            bufferedWriter.newLine();
                        }
                        else
                        if (s.getName().equals("line")){
                            Line2D.Double l = (java.awt.geom.Line2D.Double) s.getShape();
                            bufferedWriter.write("line" + ',');
                            int rp = s.getColor().getRed();
                            int gp = s.getColor().getGreen();
                            int bp = s.getColor().getBlue();                           
                            String color = Integer.toString(rp)+"-"+Integer.toString(gp)+"-"+Integer.toString(bp);                           
                            bufferedWriter.write(color + ',');
                            bufferedWriter.write(Double.toString((l.getP1().getX())));
                            bufferedWriter.write(',');
                            bufferedWriter.write(Double.toString((l.getP1().getY())));
                            bufferedWriter.write(',');
                            bufferedWriter.write(Double.toString((l.getP2().getX())));
                            bufferedWriter.write(',');
                            bufferedWriter.write(Double.toString((l.getP2().getY())));                                                      
                            bufferedWriter.newLine();
                        }
                        else
                        if (s.getName().equals("rectangle")){
                            Rectangle2D.Double r = (Rectangle2D.Double) s.getShape();
                            bufferedWriter.write("rectangle" + ',');
                            int rp = s.getColor().getRed();
                            int gp = s.getColor().getGreen();
                            int bp = s.getColor().getBlue();      
                            String color = Integer.toString(rp)+"-"+Integer.toString(gp)+"-"+Integer.toString(bp);      
                            bufferedWriter.write(color + ',');
                            bufferedWriter.write(Double.toString(r.x));
                            bufferedWriter.write(',');
                            bufferedWriter.write(Double.toString(r.y));
                            bufferedWriter.write(',');
                            bufferedWriter.write(Double.toString(r.getWidth()));
                            bufferedWriter.write(',');
                            bufferedWriter.write(Double.toString(r.getHeight()));                        
                            bufferedWriter.newLine();
                        }
                        else
                        if (s.getName().equals("polygon")){
                            PathIterator pi = s.getShape().getPathIterator(null, 0);
                            bufferedWriter.write("polygon" + ',');             
                            int rp = s.getColor().getRed();
                            int gp = s.getColor().getGreen();
                            int bp = s.getColor().getBlue();                             
                            String color = Integer.toString(rp)+"-"+Integer.toString(gp)+"-"+Integer.toString(bp);                             
                            bufferedWriter.write(color + ',');
                            while(!pi.isDone()){
                                type = pi.currentSegment(val);
                                if (type == PathIterator.SEG_MOVETO) {
                                    val1 = val[0] ; val2 = val[1];
                                    bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');         
                                }
                                else if (type == PathIterator.SEG_LINETO) {
                                    bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');
                                }
                                else if (type == PathIterator.SEG_CLOSE) {
                                    bufferedWriter.write(Double.toString(val1) + ',' + Double.toString(val2));
                                }
                                pi.next();
                            }
                            bufferedWriter.newLine();
                        }
                    }
                    bufferedWriter.close();
                }
                catch(FileNotFoundException e) {
                    System.out.println("Cannot Find/Open File");
                    e.printStackTrace();
                }
                catch(IOException i){
                    System.out.println("Cannot Write Line");
                }
        }
    }
}