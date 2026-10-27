import java.io.*;

class Student implements Serializable {
    private String name;
    private int age;
    transient private int marks;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public int getMarks() {
        return marks;
    }
}

 class main {
    public static void main(String[] args) throws Exception{
        Student[] students = new Student[3];
        students[0] = new Student("Alice", 20);
        students[1] = new Student("Bob", 22);
        students[2] = new Student("Charlie", 21);

        FileOutputStream fos = new FileOutputStream("students.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(students);
        oos.close();

        FileInputStream fis = new FileInputStream("students.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Student[] deserializedStudents = (Student[]) ois.readObject();
        ois.close();

        System.out.println("Deserialized Students:");
        for (Student student : deserializedStudents) {
            System.out.println("Name: " + student.getName() + ", Age: " + student.getAge() + ", Marks: " + student.getMarks());             
    }
}
}