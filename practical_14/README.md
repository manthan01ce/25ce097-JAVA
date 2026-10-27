# MiniBank — Practical 14 (Capstone)

Final assembled MiniBank demonstrating all concepts from Practicals 1–14:
classes, inheritance, interfaces, annotations, exceptions, threads, collections, generics, I/O.

---

## Prerequisites

- JDK 17+
- JUnit 5 jars in `lib/` (already included):
  - `junit-jupiter-api.jar`
  - `junit-platform-console-standalone.jar`
  - `opentest4j.jar`

---

## Compile

```bash
cd practical_14

# Compile application sources
javac -d out *.java

# Compile tests (needs JUnit API jar)
javac -cp out:lib/junit-jupiter-api.jar:lib/opentest4j.jar \
      -d out \
      BankServiceTest.java ItemServiceTest.java
```

---

## Run Tests

```bash
java -jar lib/junit-platform-console-standalone.jar \
     --class-path out \
     --scan-class-path
```

---

## Build Runnable JAR

```bash
mkdir -p out
javac -d out *.java

# Create manifest
echo "Main-Class: MiniBank" > manifest.txt

# Build JAR
jar cfm minibank.jar manifest.txt -C out .
```

## Run JAR

```bash
java -jar minibank.jar
```

Data is saved to `data_p14/accounts.dat` on exit and reloaded on next startup.
Transaction log: `data_p14/logs/transactions.log`
End-of-day report: `data_p14/report.txt`

---

## Menu Options

| # | Action              |
|---|---------------------|
| 1 | Open Account        |
| 2 | Deposit             |
| 3 | Withdraw            |
| 4 | Transfer            |
| 5 | Statement           |
| 6 | List All Accounts   |
| 7 | Run Batch Demo      |
| 8 | End-of-Day Report   |
| 9 | Save & Exit         |

---

## Key Design

- **Model**: `Account` (abstract) → `SavingsAccount`, `CurrentAccount`, `FixedDepositAccount`
- **Storage**: `Repository<T>` (generic, `ConcurrentHashMap`, `@Id` reflection)
- **Service**: `BankService` (validates, throws checked `BankException` hierarchy)
- **Persistence**: `StatePersister` (ObjectOutputStream/InputStream + NIO)
- **Logging**: `TransactionLog` (NIO append-only)
- **Reporting**: `ReportGenerator` (NIO directory stream + BufferedReader)
- **Concurrency**: `ExecutorService` thread pool, `synchronized` deposit/withdraw
- **Testing**: JUnit 5 `@Test` — balance, exceptions, concurrent correctness
