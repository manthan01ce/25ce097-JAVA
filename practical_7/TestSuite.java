/**
 * Sample class whose @Run methods are discovered by the mini test runner.
 */
public class TestSuite {

    @Run
    public void testAddition() {
        int result = 2 + 3;
        System.out.println("testAddition: 2+3=" + result + (result == 5 ? " PASS" : " FAIL"));
    }

    @Run
    public void testStringReverse() {
        String s = new StringBuilder("hello").reverse().toString();
        System.out.println("testStringReverse: " + s + (s.equals("olleh") ? " PASS" : " FAIL"));
    }

    public void notATest() {
        System.out.println("notATest: should NOT run");
    }

    @Run
    public void testNull() {
        String s = null;
        System.out.println("testNull: s is null? " + (s == null ? "PASS" : "FAIL"));
    }
}
