package edu.kit.informatik.element;

import static java.lang.String.format;

import java.util.ArrayList;

public class Student extends User {
    private int matriculationNumber;
    private ArrayList<Lecture> lectureList = new ArrayList<>();
    private ArrayList<Module> moduleList = new ArrayList<>();

    public Student(String firstName, String lastName, int matriculationNumber) {
        super(firstName, lastName);
        this.matriculationNumber = matriculationNumber;
    }

    public int getMatriculationNumber() {
        return matriculationNumber;
    }

    public ArrayList<Lecture> getLectureList() {
        return lectureList;
    }

    public void addLecture(Lecture lecture) {
        lectureList.add(lecture);
    }

    public void addModule(Module module) {
        moduleList.add(module);
    }

    public ArrayList<Module> getModuleList() {
        return moduleList;
    }

    public Double getAverageOfModule(int moduleId) {
        Double average = 0.00;
        // int sumOfCredits = 0;
        for (Lecture l : lectureList) {
            if (l.getModule().getCourseId() == moduleId) {
                average += (l.getLectureCredits()) * (l.getMark(matriculationNumber));
            }
        }
        return average;
    }

    public Double getAverage() {
        Double average = 0.00;
        int sumOfCredits = 0;
        for (Lecture l : lectureList) {
            sumOfCredits += l.getLectureCredits();
            for (Module m : moduleList) {
                if (l.getModule().equals(m)) {
                    average += (l.getLectureCredits()) * (l.getMark(matriculationNumber));
                }
            }
        }
        average += average / sumOfCredits;
        return average;
    }

    public Double getAverageOfStudent() {
        Double average = 0.00;
        int sumOfCredits = 0;
        for (Lecture l : lectureList) {
            sumOfCredits += l.getLectureCredits();
            average += (l.getLectureCredits()) * (l.getMark(matriculationNumber));
        }
        average = average / sumOfCredits;
        return average;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(
                format("%d %s %s %.2f", matriculationNumber, getFirstName(), getLastName(), getAverageOfStudent()));
        return resultBuilder.toString();
    }
}
