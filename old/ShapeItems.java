import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;


/**
 * 
 * @author Shahab Ahmed
 * 
 *
 */
class ShapeItems {
		/**
		 * it is used to store the name of the shape/object
		 */
		private String name;
		/**
		 * stores geometry of Line, Rectangle, Polygon but not Point because Java Class Point does not implement the interface 'Shape'
		 */
        private Shape shape;
        /**
         * stores geometry of Point
         */
        private Point point;
        /**
         * stores color of the shape/object
         */
        private Color color;
        
        /**
         * First Constructor of Class ShapeItems to create instances containing Lines, Rectangles and Polygons
         * @param name stores name of the objects (Lines, Rectangles and Polygons)
         * @param shape stores geometry/shape of the objects (Lines, Rectangles and Polygons)
         * @param color stores color of objects (Lines, Rectangles and Polygons)
         */
        public ShapeItems(String name, Shape shape, Color color) {
            super();
            this.name = name;
            this.shape = shape;
            this.color = color;
        }
        /**
         * Second Constructor of Class ShapeItems to create instances containing Points only
         * @param name stores name of the Point objects
         * @param point stores geometry/shape of the Point objects
         * @param color stores color of Point objects
         */
        public ShapeItems(String name, Point point, Color color) {
            super();
            this.name = name;
            this.point = point;
            this.color = color;
        }
        
        /**
         * @return returns the name of object of type ShapeItems
         */
        public String getName(){
        	return this.name;
        }
        /**
         * @return returns the name of Point object of type ShapeItems (if any)
         */
        public Point getPoint() {
            return point;
        }
        /**
         * @return returns the geometry/shape of Line, Rectangle or Polygon object of type ShapeItems
         */
        public Shape getShape() {
            return shape;
        }
        /**
         * sets the point parameter of object of type ShapeItems
         * @param p parameter of type Point to set
         */
        public void setPoint(Point p) {
            this.point = p;
        }
        /**
         * sets the shape parameter of object of type ShapeItems
         * @param shape parameter of type Shape to set
         */
        public void setShape(Shape shape) {
            this.shape = shape;
        }
        /**
         * @return returns color of the object of type ShapeItems
         */
        public Color getColor() {
            return color;
        }
        /**
         * sets color of the object of type ShapeItems
         * @param color parameter of type Color to set
         */
        public void setColor(Color color) {
            this.color = color;
        }

    }
