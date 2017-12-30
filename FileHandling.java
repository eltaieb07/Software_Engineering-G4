import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;


public class FileHandling {

	static String fileName = null;
	static String line = null;
	
	
	public static void ReadFile(){
		fileName = "E:/GEOMATICS MASTERS-HSKA/WS 2017-18/Software Engineering/Exercises/Drawing/src/ShapeCoordinates.csv";
		String [] coords;
		try
		{
			FileReader fileReader = new FileReader("MAP_allData.csv");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			System.out.println("*****File Found*****\n");
			
			while((line = bufferedReader.readLine())!= null){
				
				coords = line.split(",");
				//for (int i=0 ; i<coords.length; i++){
	
				if (coords[0].equals("point")){
					//MyPanel.points.add(new Point(Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
					MyPanel.shapes.add(new ShapeItems ("point" , new Point(Integer.parseInt(coords[1]),Integer.parseInt(coords[2])) , Color.BLACK));
				}
				if (coords[0].equals("line")){
					MyPanel.shapes.add(new ShapeItems("line" , new Line2D.Double(new Point2D.Double(Double.parseDouble(coords[1]),Double.parseDouble(coords[2])) , new Point2D.Double(Double.parseDouble(coords[3]),Double.parseDouble(coords[4]))) , Color.BLACK));
				}
				if (coords[0].equals("rectangle")){
					MyPanel.shapes.add(new ShapeItems ("rectangle" , new Rectangle2D.Double(Double.parseDouble(coords[1]),Double.parseDouble(coords[2]) , Double.parseDouble(coords[3]) , Double.parseDouble(coords[4]) ),Color.BLACK) );
				}
				if (coords[0].equals("polygon")){
					
					Path2D.Double path = new Path2D.Double();

					path.moveTo(Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
					for (int i=3 ; i<coords.length; i+=2){

						if (i==coords.length-3){
							path.closePath();
							
							break;
						}
						path.lineTo(Double.parseDouble(coords[i]), Double.parseDouble(coords[i+1]));
						
					}
					MyPanel.shapes.add(new ShapeItems("polygon" , path , Color.BLACK));
					
				}
				
			}
		
			bufferedReader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Cannot Find/Open File");
			//e.printStackTrace();
		}
		catch(IOException i){
			System.out.println("Cannot Read Line");
		}
		
	}
	public static void WriteFile(){
		try
		{
			
			FileWriter fileWriter = new FileWriter("MAP_allData.csv");
			System.out.println("Writing to File...");
			BufferedWriter bufferedWriter= new BufferedWriter(fileWriter);
			
			/*for (Point p : MyPanel.points){
				
				bufferedWriter.write("point" + ',');
				bufferedWriter.write(Integer.toString(p.x));
				bufferedWriter.write(',');
				//System.out.println((int) (l.getP1().getX() + ','));
				bufferedWriter.write(Integer.toString(p.y));
				
				bufferedWriter.newLine();
				
			}
			
			for (Line2D.Double l : MyPanel.lines){
				
				bufferedWriter.write("line" + ',');
				bufferedWriter.write(Double.toString((l.getP1().getX())));
				bufferedWriter.write(',');
				//System.out.println((int) (l.getP1().getX() + ','));
				bufferedWriter.write(Double.toString((l.getP1().getY())));
				bufferedWriter.write(',');
				bufferedWriter.write(Double.toString((l.getP2().getX())));
				bufferedWriter.write(',');
				//System.out.println((int) (l.getP1().getX() + ','));
				bufferedWriter.write(Double.toString((l.getP2().getY())));
				
				
				bufferedWriter.newLine();
				
			}
			for (Rectangle2D.Double r : MyPanel.rectangles){
				
				bufferedWriter.write("rectangle" + ',');
				bufferedWriter.write(Double.toString(r.x));
				bufferedWriter.write(',');
				//System.out.println((int) (l.getP1().getX() + ','));
				bufferedWriter.write(Double.toString(r.y));
				bufferedWriter.write(',');
				bufferedWriter.write(Double.toString(r.getWidth()));
				bufferedWriter.write(',');
				//System.out.println((int) (l.getP1().getX() + ','));
				bufferedWriter.write(Double.toString(r.getHeight()));
				
				
				bufferedWriter.newLine();
				
			}
			int type = 0;
			double val1 =0;
			double val2 =0;

			double [] val = new double[6];
			
			for (Path2D pa : MyPanel.polygons){
				PathIterator pi = pa.getPathIterator(null, 0);
				bufferedWriter.write("polygon" + ',');
				while(!pi.isDone()){
					type = pi.currentSegment(val);
					if (type == PathIterator.SEG_MOVETO) {
						val1 = val[0] ; val2 = val[1];
						System.out.println("move to:"+val[0] +" , " +val[1]);
						bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');
						//System.out.println(val[1]);
					}
					else if (type == PathIterator.SEG_LINETO) {
						System.out.println("line to:"+val[0] +" , " +val[1]);
						bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');
						//System.out.println(val[1]);
					}
					else if (type == PathIterator.SEG_CLOSE) {
						System.out.println("close:"+val[0] +" , " +val[1]);
						bufferedWriter.write(Double.toString(val1) + ',' + Double.toString(val2));
						//System.out.println(val[1]);
					}

					pi.next();
					

				}
				bufferedWriter.newLine();
				
			}*/
			int type = 0;
			double val1 =0;
			double val2 =0;

			double [] val = new double[6];
			for (ShapeItems s: MyPanel.shapes){
				if (s.getName().equals("point")){
					Point p = s.getPoint();
					bufferedWriter.write("point" + ',');
					bufferedWriter.write(Integer.toString(p.x));
					bufferedWriter.write(',');
					//System.out.println((int) (l.getP1().getX() + ','));
					bufferedWriter.write(Integer.toString(p.y));
					
					bufferedWriter.newLine();
				}
				else
				if (s.getName().equals("line")){
					Line2D.Double l = (java.awt.geom.Line2D.Double) s.getShape();
					bufferedWriter.write("line" + ',');
					bufferedWriter.write(Double.toString((l.getP1().getX())));
					bufferedWriter.write(',');
					//System.out.println((int) (l.getP1().getX() + ','));
					bufferedWriter.write(Double.toString((l.getP1().getY())));
					bufferedWriter.write(',');
					bufferedWriter.write(Double.toString((l.getP2().getX())));
					bufferedWriter.write(',');
					//System.out.println((int) (l.getP1().getX() + ','));
					bufferedWriter.write(Double.toString((l.getP2().getY())));
					
					
					bufferedWriter.newLine();
				}
				else
				if (s.getName().equals("rectangle")){
					Rectangle2D.Double r = (Rectangle2D.Double) s.getShape();
					bufferedWriter.write("rectangle" + ',');
					bufferedWriter.write(Double.toString(r.x));
					bufferedWriter.write(',');
					//System.out.println((int) (l.getP1().getX() + ','));
					bufferedWriter.write(Double.toString(r.y));
					bufferedWriter.write(',');
					bufferedWriter.write(Double.toString(r.getWidth()));
					bufferedWriter.write(',');
					//System.out.println((int) (l.getP1().getX() + ','));
					bufferedWriter.write(Double.toString(r.getHeight()));
					
					
					bufferedWriter.newLine();
				}
				else
				if (s.getName().equals("polygon")){
					PathIterator pi = s.getShape().getPathIterator(null, 0);
					bufferedWriter.write("polygon" + ',');
					while(!pi.isDone()){
						type = pi.currentSegment(val);
						if (type == PathIterator.SEG_MOVETO) {
							val1 = val[0] ; val2 = val[1];
							//System.out.println("move to:"+val[0] +" , " +val[1]);
							bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');

						}
						else if (type == PathIterator.SEG_LINETO) {
							//System.out.println("line to:"+val[0] +" , " +val[1]);
							bufferedWriter.write(Double.toString(val[0]) + ',' + Double.toString(val[1]) + ',');

						}
						else if (type == PathIterator.SEG_CLOSE) {
							//System.out.println("close:"+val[0] +" , " +val[1]);
							bufferedWriter.write(Double.toString(val1) + ',' + Double.toString(val2));
						}

						pi.next();
						

					}
					bufferedWriter.newLine();
				}
			}
			

			bufferedWriter.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Cannot Find/Open File");
			e.printStackTrace();
		}
		catch(IOException i){
			System.out.println("Cannot Write Line");
		}
	}
	
}
