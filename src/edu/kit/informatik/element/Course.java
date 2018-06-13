package edu.kit.informatik.element;

public class Course {
    private int courseId;
    private String courseName;

    public Course(String courseName, int courseId) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseId() {
        return courseId;
    }

}
