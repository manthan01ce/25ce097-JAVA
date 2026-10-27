public class PartTime extends Employee {

    private double hoursWorked;
    private double hourlyRate;

    public PartTime(String name, String id, double hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double monthlySalary() {
        return hoursWorked * hourlyRate;
    }
}
