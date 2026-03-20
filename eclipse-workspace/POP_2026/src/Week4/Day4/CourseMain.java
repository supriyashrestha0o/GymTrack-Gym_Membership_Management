package Week4.Day4;

class Course {
    int courseID;
    String courseName;
    int maxStudents;
    int enrolledStudents;

    Course(int courseID, String courseName, int maxStudents) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.maxStudents = maxStudents;
        this.enrolledStudents = 0;
    }

    void enrollStudent() {
        if (enrolledStudents < maxStudents) {
            enrolledStudents++;
            System.out.println("Student enrolled successfully.");
        } else {
            System.out.println("No seats available.");
        }
    }

    void removeStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("No students to remove.");
        }
    }

    void displayCourseDetails() {
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Enrolled Students: " + enrolledStudents + "/" + maxStudents);
    }
}

public class CourseMain {
    public static void main(String[] args) {
        Course c1 = new Course(501, "Java Programming", 2);

        c1.enrollStudent();
        c1.enrollStudent();
        c1.enrollStudent();

        c1.displayCourseDetails();
        c1.removeStudent();
        c1.displayCourseDetails();
    }
}
