package shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends Shape {

    public RectangleShape(int x0, int y0, int x1, int y1) {
        super();
        this.x1 = x0;
        this.y1 = y0;
        this.width = Math.abs(x1 - x0);
        this.height = Math.abs(y1 - y0);
        constructShape();
    }

    @Override
    public void constructShape() {
        awtShape = new Rectangle2D.Float(x1, y1, width, height);
    }

    @Override
    public void paint(Graphics2D g) {
        if (fill) {
            g.setColor(fillColor);
            g.fill(awtShape);
        }
        g.setColor(borderColor);
        g.setStroke(new BasicStroke(borderWidth));
        g.draw(awtShape);
    }

    public void setEndPoint(int x2, int y2) {
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
        constructShape();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    // Add any additional methods needed for the RectangleShape here
}
