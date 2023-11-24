package model;

import shapes.Shape;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Manages shapes for a vector graphics drawing application and notifies observers of changes.
 */
public class ShapeModel {

    private final List<Shape> shapes; // List to keep the shapes
    private final Stack<List<Shape>> undoStack; // Stack for undo operation
    private final Stack<List<Shape>> redoStack; // Stack for redo operation
    private final PropertyChangeSupport pcs; // Support for property change listeners

    public ShapeModel() {
        shapes = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        pcs = new PropertyChangeSupport(this);
    }

    // Adds a shape and fires a property change event
    public void addShape(Shape shape) {
        saveToUndoHistory();
        shapes.add(shape);
        pcs.firePropertyChange("shapeAdded", null, shape);
    }

    // Removes a shape and fires a property change event
    public void removeShape(Shape shape) {
        saveToUndoHistory();
        shapes.remove(shape);
        pcs.firePropertyChange("shapeRemoved", null, shape);
    }

    // Returns an unmodifiable list of shapes
    public List<Shape> getShapes() {
        return new ArrayList<>(shapes);
    }

    // Adds a property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    // Removes a property change listener
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    // Saves the current state for undo operation
    private void saveToUndoHistory() {
        undoStack.push(new ArrayList<>(shapes));
        redoStack.clear(); // Clear redo stack when a new action occurs
    }

    // Undo operation
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new ArrayList<>(shapes)); // Save current state for redo
            shapes.clear();
            shapes.addAll(undoStack.pop());
            pcs.firePropertyChange("undo", null, shapes);
        }
    }

    // Redo operation
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new ArrayList<>(shapes)); // Save current state for undo
            shapes.clear();
            shapes.addAll(redoStack.pop());
            pcs.firePropertyChange("redo", null, shapes);
        }
    }

    // Additional methods for shape selection, modification, and rotation can be added here.
    // Methods for file operations (saveToFile, readFromFile) remain the same.
}
