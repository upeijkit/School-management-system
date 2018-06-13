package edu.kit.informatik.element;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Manager {
    private static ArrayList<Professor> professorStock = new ArrayList<>();
    private static ArrayList<Instructor> instructorStock = new ArrayList<>();
    private static ArrayList<Student> studentStock = new ArrayList<>();
    private static ArrayList<Course> courseStock = new ArrayList<>();
    private static ArrayList<Lecture> lectureStock = new ArrayList<>();
    private static ArrayList<Module> moduleStock = new ArrayList<>();
    private static ArrayList<Exercise> exerciseStock = new ArrayList<>();
    private static Map<Integer, Lecture> lectureMap = new HashMap<>();
    private static Map<Integer, Student> studentMap = new HashMap<>();
    private static Map<Integer, Module> moduleMap = new HashMap<>();
    private static Map<Integer, Exercise> exerciseMap = new HashMap<>();

    public Manager() {

    }

    public ArrayList<Professor> getProfessorStock() {
        return professorStock;
    }

    public ArrayList<Instructor> getInstructorStock() {
        return instructorStock;
    }

    public ArrayList<Lecture> getLectureStock() {
        return lectureStock;
    }

    public ArrayList<Module> getModuleStock() {
        return moduleStock;
    }

    public ArrayList<Exercise> getExerciseStock() {
        return exerciseStock;
    }

    public Map<Integer, Student> getStudentMap() {
        return studentMap;
    }

    public Map<Integer, Module> getModuleMap() {
        return moduleMap;
    }

    public Map<Integer, Lecture> getLectureMap() {
        return lectureMap;
    }

    public Map<Integer, Exercise> getExerciseMap() {
        return exerciseMap;
    }

    public void addProfessor(String firstName, String lastName, String chair) {
        professorStock.add(new Professor(firstName, lastName, chair));
    }

    public void addInstructor(String firstName, String lastName, String chair) {
        instructorStock.add(new Instructor(firstName, lastName, chair));
    }

    public void addStudent(String firstName, String lastName, int matriculationNumber) {
        Student student = new Student(firstName, lastName, matriculationNumber);
        studentStock.add(student);
        studentMap.put(matriculationNumber, student);

    }

    public void addModule(String moduleName) {
        int moduleId = courseStock.size() + 1;
        Module module = new Module(moduleName, moduleId);
        courseStock.add(module);
        moduleStock.add(module);
        moduleMap.put(moduleId, module);
    }

    public void addLecture(String lectureName, int moduleId, String firstNameOfProfessor, String lastNameOfProfessor,
            String chair, int credits) {
        int lectureId = courseStock.size() + 1;
        Lecture lecture = new Lecture(lectureName, credits, lectureId);
        Module module = moduleMap.get(moduleId);
        for (Professor professor : professorStock) {
            if (professor.getFirstName().equals(firstNameOfProfessor)
                    && professor.getLastName().equals(lastNameOfProfessor) && professor.getChair().equals(chair)) {
                professor.addLecture(lecture);
            }
        }
        lectureMap.put(lectureId, lecture);
        module.addLecture(lecture);
        lecture.setModule(module);
        courseStock.add(lecture);
        lectureStock.add(lecture);

    }

    public void addExercise(String exerciseName, int lectureId, String firstNameOfInstructor,
            String lastNameOfInstructor, String chair) {
        int exerciseId = courseStock.size() + 1;
        Exercise exercise = new Exercise(exerciseName, exerciseId);
        Instructor instructor = new Instructor(firstNameOfInstructor, lastNameOfInstructor, chair);
        Lecture lecture = lectureMap.get(lectureId);
        exercise.setInstructor(instructor);
        for (Instructor i : instructorStock) {
            if (i.getFirstName().equals(firstNameOfInstructor) && i.getLastName().equals(lastNameOfInstructor)
                    && i.getChair().equals(chair)) {
                i.addExercise(exercise);
            }
        }
        lecture.setExercise(exercise);
        exercise.setLecture(lecture);
        exerciseMap.put(exerciseId, exercise);
        courseStock.add(exercise);
        exerciseStock.add(exercise);
    }

    public SortedSet<Professor> sortProfessor() {
        SortedSet<Professor> sort = new TreeSet<Professor>(new Comparator<Professor>() {
            @Override
            public int compare(Professor a, Professor b) {
                if (a.getFirstName().equals(b.getFirstName())) {
                    if (a.getLastName().equals(b.getLastName())) {
                        return (a.getChair().compareTo(b.getChair()));
                    }
                    return (a.getLastName().compareTo(b.getLastName()));
                }
                return (a.getFirstName().compareTo(b.getFirstName()));
            }
        });
        sort.addAll(professorStock);
        return sort;
    }

    public SortedSet<Instructor> sortInstructor() {
        SortedSet<Instructor> sort = new TreeSet<Instructor>(new Comparator<Instructor>() {
            @Override
            public int compare(Instructor a, Instructor b) {
                if (a.getFirstName().equals(b.getFirstName())) {
                    if (a.getLastName().equals(b.getLastName())) {
                        return (a.getChair().compareTo(b.getChair()));
                    }
                    return (a.getLastName().compareTo(b.getLastName()));
                }
                return (a.getFirstName().compareTo(b.getFirstName()));
            }
        });
        sort.addAll(instructorStock);
        return sort;
    }

    public SortedSet<Student> sortStudent() {
        SortedSet<Student> sort = new TreeSet<Student>(new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b) {
                return (a.getMatriculationNumber() - b.getMatriculationNumber());
            }
        });
        sort.addAll(studentStock);
        return sort;
    }

    public void registerExaminationCompetence(int exerciseId, int matriculationNumber) {
        exerciseMap.get(exerciseId).registerExaminationCompetence(studentMap.get(matriculationNumber));
    }

    public void registerExaminationMarking(int lectureId, int matriculationNumber, Double mark) {
        Lecture lecture = lectureMap.get(lectureId);
        Student student = studentMap.get(matriculationNumber);
        int moduleId = lecture.getModule().getCourseId();
        Module module = moduleMap.get(moduleId);
        lecture.addMarkToMap(matriculationNumber, mark);
        lecture.addStudent(student);
        student.addLecture(lecture);
        if (!student.getModuleList().contains(module)) {
            student.addModule(module);
        }
    }

}
