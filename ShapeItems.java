import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

class ShapeItems {
		private String name;
        private Shape shape;
        private Point point;
        private Color color;
        private boolean moveup = false;

        public ShapeItems(String name, Shape shape, Color color) {
            super();
            this.name = name;
            this.shape = shape;
            this.color = color;
        }
        public ShapeItems(String name, Point point, Color color) {
            super();
            this.name = name;
            this.point = point;
            this.color = color;
        }
        
        public String getName(){
        	return this.name;
        }
        public Point getPoint() {
            return point;
        }
        public Shape getShape() {
            return shape;
        }
        
        public void setPoint(Point p) {
            this.point = p;
        }
        public void setShape(Shape shape) {
            this.shape = shape;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

    }
