package shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * Abstract base class for shapes in a vector graphics drawing application.
 * This class provides common properties and methods for various shapes.
 */
public abstract class Shape implements Serializable {
    protected java.awt.Shape awtShape;
    protected int x1, y1, x2, y2;
    protected Color borderColor, fillColor;
    protected boolean fill;
    protected float borderWidth;
    protected double rotationAngle;
    protected int width, height;

    /**
     * Constructor for Shape.
     */
    public Shape() {
        borderColor = Color.BLACK;
        fillColor = Color.WHITE;
        borderWidth = 1.0f;
        rotationAngle = 0.0;
        fill = false;
    }

    /**
     * Abstract method to construct the specific shape.
     */
    public abstract void constructShape();

    /**
     * Paints the shape on a Graphics2D context.
     * @param g the Graphics2D context
     */
    public void paint(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        g.rotate(Math.toRadians(rotationAngle), x1 + (x2 - x1) / 2.0, y1 + (y2 - y1) / 2.0);

        if (fill) {
            g.setColor(fillColor);
            g.fill(awtShape);
        }

        g.setColor(borderColor);
        g.setStroke(new BasicStroke(borderWidth));
        g.draw(awtShape);

        g.setTransform(oldTransform);
    }

    /**
     * Checks if a point is inside the shape.
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the point is inside the shape, false otherwise
     */
    public boolean isClicked(int x, int y) {
        return awtShape.contains(x, y);
    }

    /**
     * Moves the shape to a new location.
     * @param dx the change in x-coordinate
     * @param dy the change in y-coordinate
     */
    public void drag(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        constructShape();
    }

    /**
     * Resize a shape.
     * @param x the x-coordinate of the corner to resize
     * @param y the y-coordinate of the corner to resize
     * @param corner the corner to resize
     */
    public void resize(int x, int y, int corner) {
        if (corner == 0) {
            x1 = x;
            y1 = y;
        } else if (corner == 1) {
            x2 = x;
            y1 = y;
        } else if (corner == 2) {
            x1 = x;
            y2 = y;
        } else if (corner == 3) {
            x2 = x;
            y2 = y;
        }
        width = Math.abs(x2 - x1);
        height = Math.abs(y2 - y1);
        constructShape();
    }

    /**
     * Updates the dimensions of the shape.
     */
    public void updateBounds() {
        Rectangle bounds = awtShape.getBounds();
        x1 = bounds.x;
        y1 = bounds.y;
        width = bounds.width;
        height = bounds.height;
        x2 = x1 + width;
        y2 = y1 + height;
    }

    // Getters and setters for properties
    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public boolean getFillState() {
        return fill;
    }

    public void setFillState(boolean fill) {
        this.fill = fill;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public java.awt.Shape getBoundary() {
        return awtShape;
    }
}
