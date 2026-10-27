public class PayrollTest {

    public static void main(String[] args) {
        Employee[] staff = {
            new FullTime("Riya",   "E001", 50000),
            new PartTime("Aman",   "E002", 120, 200),
            new Intern("Priya",    "E003", 10000),
            new FullTime("Kunal",  "E004", 60000),
            new PartTime("Sneha",  "E005", 80, 250)
        };

        double totalSalary = 0;

        System.out.println("--- Payroll Report ---");
        for (Employee e : staff) {
            double salary = e.monthlySalary();
            System.out.printf("%s — Salary: %.2f", e, salary);

            if (e instanceof Intern) {
                System.out.print("  [Intern — stipend only]");
            }

            System.out.println();
            totalSalary += salary;
        }

        System.out.println("---------------------");
        System.out.printf("Total Payroll: %.2f%n", totalSalary);
    }
}
