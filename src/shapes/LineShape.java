package shapes;

import java.awt.*;
import java.awt.geom.Line2D;
//import shapes.*;
public class LineShape extends Shape {

    public LineShape(int x1, int y1, int x2, int y2) {
       super();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        constructShape();
    }

    @Override
    public void constructShape() {
        awtShape = new Line2D.Float(x1, y1, x2, y2);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(getBorderColor()); // Use the color of the border to draw the line
        g.draw(awtShape); // Draw the awtShape which is a Line2D.Float
    }
    // You may override other methods if necessary, 
    // for example, to handle resizing in a way specific to lines
    @Override
    public void resize(int x, int y, int corner) {
        // Assuming corner 1 is the end point of the line
        if (corner == 1) {
            x2 = x;
            y2 = y;
        } else {
            x1 = x;
            y1 = y;
        }
        constructShape();
    }
    public void setEndPoint(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
        constructShape();
    }
    
}
