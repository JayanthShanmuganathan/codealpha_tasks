import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Student Grade Tracker
 * Manages student grades with average, highest, and lowest score calculations.
 */
public class StudentGradeTracker {

    // ── Data model ────────────────────────────────────────────────────────────

    static class Student {
        String id;
        String name;
        String subject;
        double score;

        Student(String id, String name, String subject, double score) {
            this.id      = id;
            this.name    = name;
            this.subject = subject;
            this.score   = score;
        }

        String letterGrade() {
            if (score >= 90) return "A+";
            if (score >= 80) return "A";
            if (score >= 70) return "B";
            if (score >= 60) return "C";
            if (score >= 50) return "D";
            return "F";
        }

        @Override
        public String toString() {
            return String.format("%-6s %-20s %-15s %5.1f  %s",
                    id, name, subject, score, letterGrade());
        }
    }

    // ── State ─────────────────────────────────────────────────────────────────

    private final ArrayList<Student> students = new ArrayList<>();
    private int idCounter = 1;

    // ── Statistics ────────────────────────────────────────────────────────────

    private double average() {
        if (students.isEmpty()) return 0;
        double sum = 0;
        for (Student s : students) sum += s.score;
        return sum / students.size();
    }

    private Student highest() {
        return students.isEmpty() ? null :
                Collections.max(students, Comparator.comparingDouble(s -> s.score));
    }

    private Student lowest() {
        return students.isEmpty() ? null :
                Collections.min(students, Comparator.comparingDouble(s -> s.score));
    }

    private long passingCount() {
        return students.stream().filter(s -> s.score >= 50).count();
    }

    // ── Display helpers ───────────────────────────────────────────────────────

    private static void printDivider() {
        System.out.println("-------------------------------------------------------------- ");
    }

    private static void printHeader() {
        printDivider();
        System.out.printf("%-6s %-20s %-15s %5s  %s%n",
                "ID", "Name", "Subject", "Score", "Grade");
        printDivider();
    }

    // ── Core operations ───────────────────────────────────────────────────────

    private void addStudent(String name, String subject, double score) {
        String id = String.format("S%03d", idCounter++);
        students.add(new Student(id, name, subject, score));
        System.out.println("  Added: " + name + " (" + id + ")");
    }

    private boolean removeStudent(String id) {
        return students.removeIf(s -> s.id.equalsIgnoreCase(id));
    }

    private void listAll() {
        if (students.isEmpty()) {
            System.out.println("  No students on record.");
            return;
        }
        printHeader();
        for (Student s : students) System.out.println(s);
        printDivider();
    }

    private void listBySubject() {
        if (students.isEmpty()) {
            System.out.println("  No students on record.");
            return;
        }
        ArrayList<String> subjects = new ArrayList<>();
        for (Student s : students)
            if (!subjects.contains(s.subject)) subjects.add(s.subject);
        Collections.sort(subjects);

        for (String sub : subjects) {
            System.out.println("\n  Subject: " + sub);
            printHeader();
            double sum = 0; int count = 0;
            for (Student s : students) {
                if (s.subject.equalsIgnoreCase(sub)) {
                    System.out.println(s);
                    sum += s.score;
                    count++;
                }
            }
            printDivider();
            System.out.printf("  Subject average: %.1f  (%d student%s)%n",
                    sum / count, count, count != 1 ? "s" : "");
        }
    }

    private void showSummary() {
        System.out.println("\n  +-----------------------------------+");
        System.out.println("  |         Summary Report            |");
        System.out.println("  +-----------------------------------+");
        int n = students.size();
        System.out.printf("  Total students : %d%n", n);
        if (n == 0) return;
        System.out.printf("  Class average  : %.1f%n", average());
        Student hi = highest(), lo = lowest();
        System.out.printf("  Highest score  : %.1f  (%s - %s)%n",
                hi.score, hi.name, hi.subject);
        System.out.printf("  Lowest score   : %.1f  (%s - %s)%n",
                lo.score, lo.name, lo.subject);
        System.out.printf("  Passing (>=50) : %d / %d%n", passingCount(), n);
        System.out.println();
        System.out.println("  Grade distribution:");
        String[] grades = {"A+","A","B","C","D","F"};
        for (String g : grades) {
            long cnt = students.stream()
                    .filter(s -> s.letterGrade().equals(g)).count();
            if (cnt > 0) System.out.printf("    %-3s: %d student%s%n",
                    g, cnt, cnt != 1 ? "s" : "");
        }
    }

    private void loadSampleData() {
        String[][] data = {
            {"Arjun Sharma",  "Mathematics", "88"},
            {"Priya Nair",    "Science",     "92"},
            {"Ravi Kumar",    "Mathematics", "74"},
            {"Deepa Menon",   "English",     "67"},
            {"Kiran Raj",     "Science",     "55"},
            {"Ananya Iyer",   "English",     "79"},
            {"Suresh Babu",   "Mathematics", "45"},
            {"Meena Das",     "Science",     "83"},
            {"Vikram Singh",  "English",     "91"},
            {"Lakshmi Rao",   "Mathematics", "61"},
        };
        for (String[] row : data)
            addStudent(row[0], row[1], Double.parseDouble(row[2]));
        System.out.println("  Sample data loaded.");
    }

    // ── Menu ──────────────────────────────────────────────────────────────────

    private static void printMenu() {
        System.out.println();
        System.out.println("  +==============================+");
        System.out.println("  |   Student Grade Tracker      |");
        System.out.println("  +==============================+");
        System.out.println("  |  1. Add student              |");
        System.out.println("  |  2. Remove student           |");
        System.out.println("  |  3. List all students        |");
        System.out.println("  |  4. List by subject          |");
        System.out.println("  |  5. Show summary report      |");
        System.out.println("  |  6. Load sample data         |");
        System.out.println("  |  0. Exit                     |");
        System.out.println("  +==============================+");
        System.out.print("  Choose an option: ");
    }

    private static double readScore(Scanner sc) {
        while (true) {
            System.out.print("  Score (0-100): ");
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v >= 0 && v <= 100) return v;
                System.out.println("  Score must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    // ── Entry point ───────────────────────────────────────────────────────────

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n  Welcome to Student Grade Tracker!");

        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("  Student name   : ");
                    String name = sc.nextLine().trim();
                    System.out.print("  Subject        : ");
                    String subject = sc.nextLine().trim();
                    double score = readScore(sc);
                    if (!name.isEmpty() && !subject.isEmpty())
                        addStudent(name, subject, score);
                    else
                        System.out.println("  Name and subject cannot be empty.");
                    break;

                case "2":
                    System.out.print("  Enter student ID to remove (e.g. S001): ");
                    String rid = sc.nextLine().trim();
                    boolean removed = removeStudent(rid);
                    System.out.println(removed
                            ? "  Removed student " + rid + "."
                            : "  Student ID not found.");
                    break;

                case "3":
                    listAll();
                    break;

                case "4":
                    listBySubject();
                    break;

                case "5":
                    showSummary();
                    break;

                case "6":
                    loadSampleData();
                    break;

                case "0":
                    System.out.println("\n  Goodbye!\n");
                    sc.close();
                    return;

                default:
                    System.out.println("  Invalid option. Choose 0-6.");
            }
        }
    }

    public static void main(String[] args) {
        new StudentGradeTracker().run();
    }
}
