/**
 * Practical 13 — Part A, Program 1 (a)
 * Generic Box<T> with get/set — type-safe without casts.
 */
public class Box<T> {

    private T value;

    public Box(T value)  { this.value = value; }
    public T   get()     { return value; }
    public void set(T v) { this.value = v; }

    @Override
    public String toString() { return "Box[" + value + "]"; }
}
