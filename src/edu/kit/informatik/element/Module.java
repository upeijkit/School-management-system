package edu.kit.informatik.element;

import static java.lang.String.format;

import java.util.ArrayList;

public class Module extends Course {
    private ArrayList<Lecture> lectureList = new ArrayList<>();

    public Module(String moduleName, int id) {
        super(moduleName, id);
    }

    public void addLecture(Lecture lecture) {
        lectureList.add(lecture);
    }

    public int getModuleCredits() {
        int moduleCredits = 0;
        for (Lecture l : lectureList) {
            moduleCredits = moduleCredits + l.getLectureCredits();
        }
        return moduleCredits;
    }

    public ArrayList<Lecture> getLectureList() {
        return lectureList;
    }

    public Double getAverageOfModule() {
        Double average = 0.00;
        int credits = 0;
        for (Lecture l : lectureList) {
            if (!l.getMarkList().isEmpty()) {
                credits += l.getLectureCredits();
                average += (l.getLectureCredits() * l.getAverage());
            }
        }
        average = (average / credits);
        return average;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        if (lectureList.isEmpty() || getAverageOfModule() == 0.00) {
            resultBuilder.append(format("%d %s %d %s", getCourseId(), getCourseName(), getModuleCredits(), "none"));
        } else {
            resultBuilder.append(
                    format("%d %s %d %.2f", getCourseId(), getCourseName(), getModuleCredits(), getAverageOfModule()));
        }
        return resultBuilder.toString();
    }
}
