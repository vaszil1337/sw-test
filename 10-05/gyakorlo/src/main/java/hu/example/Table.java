package hu.example;

public class Table {
    private final int width;
    private final int length;
    private final int height;
    private int currentHeight;
    private final boolean adjustable;
    private String color;
    private int numberOfLegs;

    public Table(int width, int length, int height) {
        this(width, length, height, false, height);
    }

    public Table(int width, int length, int height, int currentHeight) {
        this(width, length, height, true, currentHeight);
    }

    public Table(int width, int length, int height, boolean isAdjustable) {
        this(width, length, height, isAdjustable, height);
    }

    public Table(int width, int length, int height, boolean isAdjustable, int currentHeight) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.adjustable = isAdjustable;
        validateHeight(currentHeight);
        this.currentHeight = currentHeight;
    }

    public void setHeight(int newHeight) {
        if (!adjustable) {
            throw new IllegalStateException("The table height is not adjustable");
        }
        validateHeight(newHeight);
        currentHeight = newHeight;
    }

    public int area() {
        return width * length;
    }

    public int getCapacity() {
        return getPerimeter() / 60;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public boolean isAdjustable() {
        return adjustable;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void repaint(String newColor) {
        color = newColor;
    }

    public boolean isStable() {
        return numberOfLegs >= 3;
    }

    public boolean isFoldable() {
        return adjustable && numberOfLegs >= 4;
    }

    public int getPerimeter() {
        return 2 * (width + length);
    }

    public String getColor() {
        return color;
    }

    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    public void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }

    private static void validateHeight(int value) {
        if (value < 0 || value > 200) {
            throw new IllegalArgumentException("Height must be between 0 and 200");
        }
    }
}
