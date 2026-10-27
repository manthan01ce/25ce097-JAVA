import java.lang.reflect.Method;

/**
 * Practical 7 — Part A, Program 2
 * Mini test runner: uses reflection to find and invoke all @Run methods.
 */
public class MiniTestRunner {

    public static int run(Object testObject) throws Exception {
        int count = 0;
        for (Method method : testObject.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class)) {
                method.setAccessible(true);
                method.invoke(testObject);
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        TestSuite suite = new TestSuite();
        System.out.println("=== Mini Test Runner ===");
        int ran = run(suite);
        System.out.println("\nTotal @Run methods invoked: " + ran);
    }
}
