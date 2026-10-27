import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Practical 14 — Part B: JUnit 5 tests for BankService.
 *
 * Tests:
 *  1. deposit increases the balance
 *  2. withdrawal over the limit throws InsufficientFundsException
 *  3. deposit of 0 or negative throws InvalidAmountException
 *  4. transfer moves money correctly
 *  5. unknown account throws AccountNotFoundException
 *  6. many concurrent deposits keep the balance correct (thread-safety)
 */
public class BankServiceTest {

    private Repository<Account> repo;
    private BankService         service;
    private Account             acc;

    @BeforeEach
    void setUp() throws BankException {
        Account.resetCounter();
        repo    = new Repository<>();
        service = new BankService(repo, Path.of(System.getProperty("java.io.tmpdir"), "test_tx.log"));
        acc     = service.openAccount("Riya", "SAVINGS", 5000);
    }

    // ------------------------------------------------------------------ //

    @Test
    void depositIncreasesBalance() throws BankException {
        long newBalance = service.deposit(acc.getAccountNumber(), 1000);
        assertEquals(6000, newBalance);
    }

    @Test
    void withdrawDecreasesBalance() throws BankException {
        long newBalance = service.withdraw(acc.getAccountNumber(), 2000);
        assertEquals(3000, newBalance);
    }

    @Test
    void withdrawOverLimitThrowsInsufficientFunds() {
        InsufficientFundsException ex = assertThrows(
            InsufficientFundsException.class,
            () -> service.withdraw(acc.getAccountNumber(), 99999)
        );
        assertTrue(ex.getShortfall() > 0);
    }

    @Test
    void depositZeroThrowsInvalidAmount() {
        assertThrows(InvalidAmountException.class,
            () -> service.deposit(acc.getAccountNumber(), 0));
    }

    @Test
    void depositNegativeThrowsInvalidAmount() {
        assertThrows(InvalidAmountException.class,
            () -> service.deposit(acc.getAccountNumber(), -500));
    }

    @Test
    void unknownAccountThrowsNotFound() {
        assertThrows(AccountNotFoundException.class,
            () -> service.deposit("AC9999", 100));
    }

    @Test
    void transferMovesMoneyCorrectly() throws BankException {
        Account target = service.openAccount("Aman", "CURRENT", 1000);
        service.transfer(acc.getAccountNumber(), target.getAccountNumber(), 2000);
        assertEquals(3000, acc.getBalance());
        assertEquals(3000, target.getBalance());
    }

    @Test
    void transferInsufficientFundsThrows() throws BankException {
        Account target = service.openAccount("Aman", "CURRENT", 1000);
        assertThrows(InsufficientFundsException.class,
            () -> service.transfer(acc.getAccountNumber(), target.getAccountNumber(), 99999));
    }

    @Test
    void concurrentDepositsKeepBalanceCorrect() throws Exception {
        // 10 threads × 1000 deposits of 1 each → expected += 10000
        final int THREADS = 10, TIMES = 1000;
        long expected = acc.getBalance() + (long) THREADS * TIMES;

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        CountDownLatch  latch = new CountDownLatch(THREADS);

        for (int i = 0; i < THREADS; i++) {
            pool.execute(() -> {
                try {
                    for (int j = 0; j < TIMES; j++)
                        service.deposit(acc.getAccountNumber(), 1);
                } catch (BankException e) {
                    fail("Unexpected exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(30, TimeUnit.SECONDS);
        pool.shutdown();
        assertEquals(expected, acc.getBalance(),
                "Concurrent deposits must produce exact balance");
    }
}
