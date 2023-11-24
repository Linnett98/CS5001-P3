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
    private static final Color HIGHLIGHT_COLOR = new Color(255,0,0,128); // semi red

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
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShapeType != null && selectedShape != null) {
                    model.addShape(selectedShape);
                }
                selectedShape = null;
                startPoint = null;
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
        switch (currentShapeType) {
            case LINE:
                return new LineShape(start.x, start.y, end.x, end.y);
            case RECTANGLE:
                return new RectangleShape(start.x, start.y, end.x, end.y);
            // Add cases for other shapes
            default:
                return null;
        }
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
        return model.getShapes().stream()
            .filter(shape -> shape.isClicked(x, y))
            .findFirst()
            .orElse(null);
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
    
    public Color getSelectedShapeFillColor() {
        if (selectedShape != null) {
            return selectedShape.getFillColor();
        } else {
            return Color.BLACK;
        }
    }

    public void updateSelectedShapeFill(Color fillColor, boolean fill) {
        if (selectedShape != null) {
            selectedShape.setFillColorAndFill(fillColor, fill);
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

    
}