public class shapeTest {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(3),
            new Rectangle(4, 5),
            new Triangle(6, 7)
        };

        double totalArea = 0;
        double largestArea = Double.MIN_VALUE;

        for (Shape shape : shapes) {
            double area = shape.area();
            System.out.println("Area: " + area);
            totalArea += area;
            if (area > largestArea) {
                largestArea = area;
            }
        }

        System.out.println("Total Area: " + totalArea);
        System.out.println("Largest Area: " + largestArea);
    }
}
