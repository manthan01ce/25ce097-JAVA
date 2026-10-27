import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Practical 14 — Part A: JUnit 5 tests for ItemService.
 * Checks: adding increases count, invalid action throws, remove works.
 */
public class ItemServiceTest {

    private ItemService service;

    @BeforeEach
    void setUp() { service = new ItemService(); }

    @Test
    void addIncreasesCount() {
        assertEquals(0, service.count());
        service.add(new Item("Apple", 1.5));
        assertEquals(1, service.count());
        service.add(new Item("Banana", 0.8));
        assertEquals(2, service.count());
    }

    @Test
    void addNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.add(null));
    }

    @Test
    void itemWithBlankNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Item("", 1.0));
    }

    @Test
    void itemWithNegativePriceThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Item("Bad", -5.0));
    }

    @Test
    void removeDecreasesCount() {
        service.add(new Item("Apple", 1.5));
        service.add(new Item("Banana", 0.8));
        assertTrue(service.remove("Apple"));
        assertEquals(1, service.count());
    }

    @Test
    void removeNonExistentReturnsFalse() {
        assertFalse(service.remove("Ghost"));
    }
}
