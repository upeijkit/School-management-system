package edu.kit.informatik.element;

import static java.lang.String.format;

import java.util.ArrayList;

public class Instructor extends Staff {
    private ArrayList<Exercise> exerciseList = new ArrayList<>();

    public Instructor(String firstName, String lastName, String chair) {
        super(firstName, lastName, chair);
    }

    public void addExercise(Exercise exercise) {
        exerciseList.add(exercise);
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(format("%s %s %s %d", getChair(), getFirstName(), getLastName(), exerciseList.size()));
        return resultBuilder.toString();
    }
}
