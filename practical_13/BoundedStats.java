/**
 * Practical 13 — Part A, Program 2
 * Generic method with bounded type parameter: <N extends Number> average.
 * Works for Integer[], Double[], Long[]; refuses non-Number types at compile time.
 */
public class BoundedStats {

    /** Returns the first element of any array. */
    public static <T> T first(T[] arr) {
        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException("Array is empty");
        return arr[0];
    }

    /** Average of any Number subtype array. */
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("Array is empty");
        double sum = 0;
        for (N n : values) sum += n.doubleValue();
        return sum / values.length;
    }

    public static void main(String[] args) {
        // Box and Pair demo
        Box<String>  strBox  = new Box<>("Hello");
        Box<Integer> intBox  = new Box<>(42);
        strBox.set("World");
        System.out.println("strBox : " + strBox);
        System.out.println("intBox : " + intBox);

        Pair<String, Integer> pair = new Pair<>("Alice", 95);
        System.out.println("pair   : " + pair);

        // generic first()
        String[] names = {"Riya", "Aman", "Priya"};
        System.out.println("first  : " + first(names));

        // bounded average
        Integer[] ints   = {10, 20, 30, 40};
        Double[]  doubles = {1.5, 2.5, 3.0};
        Long[]    longs   = {100L, 200L, 300L};

        System.out.printf("%navg(ints)   = %.2f%n", average(ints));
        System.out.printf("avg(doubles)= %.2f%n", average(doubles));
        System.out.printf("avg(longs)  = %.2f%n", average(longs));
        // average(new String[]{"a","b"});  // won't compile — not a Number
    }
}
