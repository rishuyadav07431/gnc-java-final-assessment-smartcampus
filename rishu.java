import java.util.*;

// Custom Exception
class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

// Student Class
class Student {
    int studentId;
    String name;
    String email;

    Student(int studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return "ID: " + studentId + ", Name: " + name + ", Email: " + email;
    }
}

// Course Class
class Course {
    int courseId;
    String courseName;
    double fee;

    Course(int courseId, String courseName, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.fee = fee;
    }

    public String toString() {
        return "ID: " + courseId + ", Course: " + courseName + ", Fee: " + fee;
    }
}

// Thread for Enrollment Processing
class EnrollmentThread extends Thread {
    int studentId;
    int courseId;

    EnrollmentThread(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public void run() {
        System.out.println("Processing enrollment...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
        System.out.println("Enrollment completed for Student " + studentId + " in Course " + courseId);
    }
}

// Main Class
public class rishu {

    static HashMap<Integer, Student> students = new HashMap<>();
    static HashMap<Integer, Course> courses = new HashMap<>();
    static HashMap<Integer, ArrayList<Integer>> enrollments = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Smart Campus System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. View Students");
            System.out.println("5. View Enrollments");
            System.out.println("6. Process Enrollment (Thread)");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        addStudent(sc);
                        break;

                    case 2:
                        addCourse(sc);
                        break;

                    case 3:
                        enrollStudent(sc);
                        break;

                    case 4:
                        viewStudents();
                        break;

                    case 5:
                        viewEnrollments();
                        break;

                    case 6:
                        processEnrollment(sc);
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        return;

                    default:
                        throw new InvalidDataException("Invalid Menu Choice!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers only.");
                sc.next(); // clear buffer
            } catch (InvalidDataException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Add Student
    static void addStudent(Scanner sc) throws InvalidDataException {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (students.containsKey(id)) {
            throw new InvalidDataException("Student ID already exists!");
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        students.put(id, new Student(id, name, email));
        System.out.println("Student added successfully!");
    }

    // Add Course
    static void addCourse(Scanner sc) throws InvalidDataException {
        System.out.print("Enter Course ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (courses.containsKey(id)) {
            throw new InvalidDataException("Course ID already exists!");
        }

        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Fee: ");
        double fee = sc.nextDouble();

        courses.put(id, new Course(id, name, fee));
        System.out.println("Course added successfully!");
    }

    // Enroll Student
    static void enrollStudent(Scanner sc) throws InvalidDataException {
        System.out.print("Enter Student ID: ");
        int sid = sc.nextInt();

        System.out.print("Enter Course ID: ");
        int cid = sc.nextInt();

        if (!students.containsKey(sid) || !courses.containsKey(cid)) {
            throw new InvalidDataException("Student or Course not found!");
        }

        enrollments.putIfAbsent(sid, new ArrayList<>());
        enrollments.get(sid).add(cid);

        System.out.println("Enrollment successful!");
    }

    // View Students
    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students.values()) {
            System.out.println(s);
        }
    }

    // View Enrollments
    static void viewEnrollments() {
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        for (int sid : enrollments.keySet()) {
            System.out.println("Student ID: " + sid + " enrolled in courses: " + enrollments.get(sid));
        }
    }

    // Process Enrollment using Thread
    static void processEnrollment(Scanner sc) throws InvalidDataException {
        System.out.print("Enter Student ID: ");
        int sid = sc.nextInt();

        System.out.print("Enter Course ID: ");
        int cid = sc.nextInt();

        if (!students.containsKey(sid) || !courses.containsKey(cid)) {
            throw new InvalidDataException("Invalid student or course!");
        }

        EnrollmentThread t = new EnrollmentThread(sid, cid);
        t.start();
    }
}