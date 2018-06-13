package edu.kit.informatik.element;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lecture extends Course {
    private int lectureCredits;
    private Exercise exercise = null;
    private Module module;
    private Map<Integer, Double> markList = new HashMap<>();
    private ArrayList<Student> studentList = new ArrayList<>();

    public Lecture(String lectureName, int credits, int id) {
        super(lectureName, id);
        this.lectureCredits = credits;

    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Double getMark(int matriculationNumber) {
        return markList.get(matriculationNumber);
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public Map<Integer, Double> getMarkList() {
        return markList;
    }

    public void addMarkToMap(int matriculationNumber, Double mark) {
        markList.put(matriculationNumber, mark);
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public int getLectureCredits() {
        return lectureCredits;
    }

    public Double getAverage() {
        Double average = 0.00;
        if (!markList.isEmpty()) {
            for (int i : markList.keySet()) {
                average += markList.get(i);
            }
            average = average / markList.size();
        }
        return average;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        if (markList.isEmpty()) {
            if (exercise != null) {
                resultBuilder.append(
                        format("%d %s %s %d %s", getCourseId(), getCourseName(), "Yes", lectureCredits, "none"));
            } else {
                resultBuilder
                        .append(format("%d %s %s %d %s", getCourseId(), getCourseName(), "No", lectureCredits, "none"));
            }
        } else {
            if (exercise != null) {
                resultBuilder.append(format("%d %s %s %d %.2f", getCourseId(), getCourseName(), "Yes", lectureCredits,
                        getAverage()));
            } else {
                resultBuilder.append(
                        format("%d %s %s %d %.2f", getCourseId(), getCourseName(), "No", lectureCredits, getAverage()));
            }
        }
        return resultBuilder.toString();
    }
}
