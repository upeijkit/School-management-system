package edu.kit.informatik.element;

import static java.lang.String.format;

import java.util.ArrayList;

public class Professor extends Staff {
    private ArrayList<Lecture> lectureList = new ArrayList<>();

    public Professor(String firstName, String lastName, String chair) {
        super(firstName, lastName, chair);
    }

    public void addLecture(Lecture lecture) {
        lectureList.add(lecture);
    }

    public ArrayList<Lecture> getLectureList() {
        return lectureList;
    }

    public Double getAverageOfProfessor() {
        Double average = 0.00;
        int sumOfCredits = 0;
        for (Lecture l : lectureList) {
            average += l.getAverage() * l.getLectureCredits();
            sumOfCredits += l.getLectureCredits();
        }
        average = average / sumOfCredits;
        return average;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        if (getAverageOfProfessor() == 0.00) {
            resultBuilder.append(format("%s %s %s %s", getChair(), getFirstName(), getLastName(), "none"));
        } else {
            resultBuilder.append(
                    format("%s %s %s %.2f", getChair(), getFirstName(), getLastName(), getAverageOfProfessor()));
        }
        return resultBuilder.toString();
    }

}
