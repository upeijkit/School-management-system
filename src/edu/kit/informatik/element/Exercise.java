package edu.kit.informatik.element;

import static java.lang.String.format;

import java.util.ArrayList;

public class Exercise extends Course {
    private Lecture lecture;
    private Instructor instructor;
    private ArrayList<Student> examinationCompetenceList = new ArrayList<>();

    public Exercise(String exerciseName, int id) {
        super(exerciseName, id);
    }

    public void registerExaminationCompetence(Student student) {
        examinationCompetenceList.add(student);
    }

    public ArrayList<Student> getExaminationCompetenceList() {
        return examinationCompetenceList;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(format("%d %s %d %d %s %s %s %d", getCourseId(), getCourseName(),
                getLecture().getCourseId(), getLecture().getModule().getCourseId(), getInstructor().getFirstName(),
                getInstructor().getLastName(), getInstructor().getChair(), examinationCompetenceList.size()));
        return resultBuilder.toString();
    }

}
