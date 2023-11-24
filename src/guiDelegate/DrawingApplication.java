package guiDelegate;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import model.ShapeModel;
//import shapes.Shape;
import model.ShapeType;
// This class will be the main application window.
public class DrawingApplication extends JFrame {
    private JCheckBox fillCheckBox; // Member variable for fill checkbox
    
    public DrawingApplication() {

        // Set the title of the window
        super("Vector Drawing Application");

        // Create a model instance to manage shapes
        ShapeModel model = new ShapeModel();

        // Create a custom drawing panel
        DrawingPanel drawingPanel = new DrawingPanel(model);

        // initalise fill checkbox
        fillCheckBox = new JCheckBox("Fill");

        // Set up the main window layout
        this.setLayout(new BorderLayout());
        this.add(drawingPanel, BorderLayout.CENTER);

        // Add a toolbar
        JToolBar toolBar = createToolBar(model, drawingPanel);
        this.add(toolBar, BorderLayout.NORTH);

        // Add a menu bar
    
        setJMenuBar(createMenuBar(model, drawingPanel));

        // Configure the main window
        configureWindow();
    }

    private void configureWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600); // Set the initial size
        this.setLocationRelativeTo(null); // Center the window
        this.setVisible(true);
    }

    private JMenuBar createMenuBar(ShapeModel model, DrawingPanel drawingPanel) {
        JMenuBar menuBar = new JMenuBar();
    
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export");
        exportItem.addActionListener(e -> exportDrawing(drawingPanel));
        fileMenu.add(exportItem);
    
        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.addActionListener(e -> model.undo());
        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.addActionListener(e -> model.redo());
        editMenu.add(undoItem);
        editMenu.add(redoItem);
    
        // Shape Menu //** NEED TO ADD SHAPE TYPE */
        JMenu shapeMenu = new JMenu("Shapes");
        JMenuItem lineItem = new JMenuItem("Line");
        lineItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.LINE));
        JMenuItem rectangleItem = new JMenuItem("Rectangle");
        rectangleItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.RECTANGLE));
        JMenuItem ellipseItem = new JMenuItem("Ellipse");
        ellipseItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.ELLIPSE));
        JMenuItem triangleItem = new JMenuItem("Triangle");
        triangleItem.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.TRIANGLE));
        shapeMenu.add(lineItem);
        shapeMenu.add(rectangleItem);
        shapeMenu.add(ellipseItem);
        shapeMenu.add(triangleItem);
    
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(shapeMenu);
    
        return menuBar;
    }
    
    private JToolBar createToolBar(ShapeModel model, DrawingPanel drawingPanel) {
        JToolBar toolBar = new JToolBar();
    
        // Shape buttons
        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.LINE));
        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.RECTANGLE));
        JButton ellipseButton = new JButton("Ellipse");
        ellipseButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.ELLIPSE));
        JButton triangleButton = new JButton("Triangle");
        triangleButton.addActionListener(e -> drawingPanel.setCurrentShapeType(ShapeType.TRIANGLE));
    
        // Color button for border color
        JButton borderColorButton = new JButton("Border Color");
        borderColorButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(drawingPanel, "Choose a border color", Color.BLACK);
            if (chosenColor != null) {
                drawingPanel.updateSelectedShapeBorderColor(chosenColor);
            }
        });
    
        // Slider for border width
        JSlider borderWidthSlider = new JSlider(1, 10); // Example range
        borderWidthSlider.setMajorTickSpacing(1);
        borderWidthSlider.setPaintTicks(true);
        borderWidthSlider.setPaintLabels(true);
        borderWidthSlider.addChangeListener(e -> {
            float width = borderWidthSlider.getValue();
            drawingPanel.updateSelectedShapeBorderWidth(width);
        });
    
        // Fill color and state
        JButton fillColorButton = new JButton("Fill Color");
        fillColorButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(drawingPanel, "Choose a fill color", Color.BLACK);
            if (chosenColor != null) {
                drawingPanel.updateSelectedShapeFill(chosenColor, fillCheckBox.isSelected());
            }
        });
        JCheckBox fillCheckBox = new JCheckBox("Fill"); 
        fillCheckBox.addActionListener(e -> {
            Color currentColor = drawingPanel.getSelectedShapeFillColor();
            drawingPanel.updateSelectedShapeFill(currentColor, fillCheckBox.isSelected());
        });
    
        // Undo/redo buttons
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> model.undo());
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> model.redo());
    
        // Rotation button
        JButton rotateButton = new JButton("Rotate");
        rotateButton.addActionListener(e -> rotateShape(drawingPanel));
    
        // Adding buttons to the toolbar
        toolBar.add(lineButton);
        toolBar.add(rectangleButton);
        toolBar.add(ellipseButton);
        toolBar.add(triangleButton);
        toolBar.addSeparator();
        toolBar.add(borderColorButton);
        toolBar.add(borderWidthSlider);
        toolBar.addSeparator();
        toolBar.add(fillColorButton);
        toolBar.add(fillCheckBox);
        toolBar.addSeparator();
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();
        toolBar.add(rotateButton);
    
        return toolBar;
    }
    
    // Helper methods for actions
    private void exportDrawing(DrawingPanel drawingPanel) {
        // Implement exporting logic here
    }
    
    private void chooseColor(DrawingPanel drawingPanel) {
        Color chosenColor = JColorChooser.showDialog(drawingPanel, "Choose a color", Color.BLACK);
        if (chosenColor != null) {
            drawingPanel.updateSelectedShapeFill(chosenColor, fillCheckBox.isSelected());
        }
    }
    private void rotateShape(DrawingPanel drawingPanel) {
        // Implement rotation logic here
    }
    
    // Main entry point of the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingApplication());
    }
}
