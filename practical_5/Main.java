import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();

        // Add a mix of shapes
        shapes.add(new Circle(5.0));
        shapes.add(new Rectangle(4.0, 6.0));
        shapes.add(new Triangle(5.0, 5.0));
        shapes.add(new Circle(10.0));

        double totalArea = 0.0;
        double largestArea = 0.0;

        System.out.println("--- Shape Area Calculation ---");

        for (Shape shape : shapes) {
            double area = shape.area();
            System.out.printf("Shape Area: %.2f\n", area);
            totalArea += area;
            if (area > largestArea) {
                largestArea = area;
            }
        }

        System.out.println("\n------------------------------");
        System.out.printf("Total Area of all shapes: %.2f\n", totalArea);
        System.out.printf("Largest Area found: %.2f\n", largestArea);
    }
}