 package guiDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.ShapeModel;
import shapes.Shape;
import shapes.LineShape;
import shapes.RectangleShape;
import model.ShapeType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class DrawingPanel extends JPanel implements PropertyChangeListener {
    private ShapeModel model;
    private Shape selectedShape;
    private Point startPoint;
    private ShapeType currentShapeType; // Enum for different shape types
    private static final Color HIGHLIGHT_COLOR = new Color(255,0,0,128); // semi-transparent red
    private Color lineColor;
    private float lineWidth;

    public DrawingPanel(ShapeModel model) {
        this.model = model;
        this.setBackground(Color.WHITE);
        model.addPropertyChangeListener(this);
        setupMouseHandlers();
    }

    private void setupMouseHandlers() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentShapeType != null) {
                    startPoint = e.getPoint();
                    selectedShape = createShape(startPoint, startPoint);
                } else {
                    selectedShape = findShapeAtPoint(e.getX(), e.getY());
                    repaint(); // Repaint to update the highlight of the selected shape
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShapeType != null && selectedShape != null) {
                    model.addShape(selectedShape);
                }
                selectedShape = null;
                startPoint = null;
                repaint(); // Repaint to remove the highlight of the selected shape
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null && startPoint != null) {
                    updateCreatingShape(e.getPoint());
                    repaint();
                }
            }
        });
    }

    private Shape createShape(Point start, Point end) {
        // Ensure currentShapeType is set to something valid
        if (currentShapeType != null) {
            switch (currentShapeType) {
                case LINE:
                    LineShape line = new LineShape(start.x, start.y, end.x, end.y);
                    line.setBorderColor(lineColor); // Use the stored user-selected color
                    line.setBorderWidth(lineWidth); // Use the stored user-selected width
                    return line;
                case RECTANGLE:
                    // Assume that RectangleShape constructor and setters exist and are correctly implemented
                    RectangleShape rectangle = new RectangleShape(start.x, start.y, end.x, end.y);
                    // Set rectangle color and width if needed
                    return rectangle;
                // Add cases for other shapes
                // ...
            }
        }
        return null; // Return null if no shape type is selected
    }

    private void updateCreatingShape(Point currentPoint) {
        if (selectedShape instanceof LineShape) {
            ((LineShape) selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        } else if (selectedShape instanceof RectangleShape) {
            ((RectangleShape) selectedShape).setEndPoint(currentPoint.x, currentPoint.y);
        }
        // Update logic for other shapes
    }

    private Shape findShapeAtPoint(int x, int y) {
        // Iterate through all shapes in reverse order
        for (int i = model.getShapes().size() - 1; i >= 0; i--) {
            Shape shape = model.getShapes().get(i);
            if (shape.isClicked(x, y)) {
                return shape;
            }
        }
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Shape shape : model.getShapes()) {
            shape.paint(g2d);
            if (shape == selectedShape) {
                highlightSelectedShape(g2d, shape);
            }
        }
    }

    private void highlightSelectedShape(Graphics2D g, Shape shape) {
        g.setColor(HIGHLIGHT_COLOR);
        g.setStroke(new BasicStroke(3));
        g.draw(shape.getBoundary().getBounds2D());
    }

    // Other methods (getSelectedShapeFillColor, updateSelectedShapeFill, etc.) remain unchanged

    
    public Color getSelectedShapeFillColor() {
        if (selectedShape != null) {
            return selectedShape.getFillColor();
        } else {
            return Color.BLACK;
        }
    }

    public void updateSelectedShapeFill(Color fillColor, boolean fill) {
        if (selectedShape != null) {
            selectedShape.setFillColor(fillColor);
            selectedShape.setFillState(fill);
            repaint();
        }
    }

    public ShapeType getCurrentShapeType() {
        return this.currentShapeType;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }
    public void updateSelectedShapeBorderWidth(float width) {
        if (selectedShape != null) {
            selectedShape.setBorderWidth(width);
            repaint();
        }
    }
    
    public void updateSelectedShapeBorderColor(Color color) {
        if (selectedShape != null) {
            selectedShape.setBorderColor(color);
            repaint();
        }
    }
    public void setCurrentShapeType(ShapeType shapeType) {
        this.currentShapeType = shapeType;
        this.selectedShape = null; // Optionally reset the selected shape
    }
    public void onLineButtonPressed() {
        // Set the current shape type
        this.currentShapeType = ShapeType.LINE;
    
        // Prompt for line color
        Color lineColor = JColorChooser.showDialog(this, "Choose Line Color", Color.BLACK);
        if (lineColor == null) {
            // User canceled the color chooser, exit the line creation mode
            this.currentShapeType = null;
            return;
        }
    
        // Prompt for line width
        String lineWidthString = JOptionPane.showInputDialog(this, "Enter Line Width:", "1.0");
        float lineWidth;
        try {
            lineWidth = Float.parseFloat(lineWidthString);
        } catch (NumberFormatException e) {
            // User entered an invalid number, exit the line creation mode
            JOptionPane.showMessageDialog(this, "Invalid line width entered. Please enter a number.");
            this.currentShapeType = null;
            return;
        }
    
        // Store the properties for new lines
        this.lineColor = lineColor;
        this.lineWidth = lineWidth;
    }
    

    
}