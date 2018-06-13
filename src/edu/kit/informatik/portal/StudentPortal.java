package edu.kit.informatik.portal;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Scanner;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.element.Exercise;
import edu.kit.informatik.element.Instructor;
import edu.kit.informatik.element.Lecture;
import edu.kit.informatik.element.Manager;
import edu.kit.informatik.element.Module;
import edu.kit.informatik.element.Professor;
import edu.kit.informatik.element.Student;
import edu.kit.informatik.parsing.CommandParser;
import edu.kit.informatik.parsing.DoubleParser;
import edu.kit.informatik.parsing.IntegerParser;
import edu.kit.informatik.parsing.ParseException;
import edu.kit.informatik.parsing.StringParser;
import edu.kit.informatik.parsing.TokenParser;

public final class StudentPortal {
    private static final Manager MANAGER = new Manager();

    private static CommandParser commandParser = new CommandParser()
            .commands("add-professor", "list-professor", "summary-professor", "add-instructor", "list-instructor",
                    "summary-instructor", "add-student", "list-student", "summary-student", "add-module", "list-module",
                    "summary-module", "add-lecture", "list-lecture", "summary-lecture", "add-exercise", "list-exercise",
                    "summary-exercise", "examination-competence", "examination-marking", "reset", "quit")
            .commandSeparator(" ").argumentsSeparator(";");
    private static TokenParser<String> name = new StringParser();
    private static TokenParser<String> chairs = new StringParser();
    private static TokenParser<Integer> id = new IntegerParser();
    private static TokenParser<Integer> credits = new IntegerParser();
    private static TokenParser<Integer> matriculationNumber = new IntegerParser();
    private static TokenParser<Double> mark = new DoubleParser().minimun(0.00).maximun(5.00);
    private static TokenParser<?>[] parsers = {commandParser, name, chairs, id, credits, matriculationNumber, mark };
    private static String command = "";

    private StudentPortal() {

    }

    public static void main(String[] args) {
        do {
            Scanner commandScanner = new Scanner(Terminal.readLine());
            initializeParsers(commandScanner);
            String output = null;
            try {
                command = commandParser.next();
                assertExpectArgument(command);
                switch (command) {
                case "add-professor":
                    output = addProfessor(name.next(), name.next(), chairs.end());
                    break;
                case "list-professor":
                    output = listProfessor();
                    break;
                case "summary-professor":
                    output = summaryProfessor(name.next(), name.next(), chairs.end());
                    break;
                case "add-instructor":
                    output = addInstructor(name.next(), name.next(), chairs.end());
                    break;
                case "list-instructor":
                    output = listInstructor();
                    break;
                case "summary-instructor":
                    output = summaryInstructor(name.next(), name.next(), chairs.end());
                    break;
                case "add-student":
                    output = addStudent(name.next(), name.next(), matriculationNumber.end());
                    break;
                case "list-student":
                    output = listStudent();
                    break;
                case "summary-student":
                    output = summaryStudent(name.next(), name.next(), matriculationNumber.end());
                    break;
                case "add-module":
                    output = addModule(name.next());
                    break;
                case "list-module":
                    output = listModule();
                    break;
                case "summary-module":
                    output = summaryModule(id.end());
                    break;
                case "add-lecture":
                    output = addLecture(name.next(), id.next(), name.next(), name.next(), chairs.next(), credits.end());
                    break;
                case "list-lecture":
                    output = listLecture();
                    break;
                case "summary-lecture":
                    output = summaryLecture(id.end());
                    break;
                case "add-exercise":
                    output = addExercise(name.next(), id.next(), name.next(), name.next(), chairs.end());
                    break;
                case "list-exercise":
                    output = listExercise();
                    break;
                case "summary-exercise":
                    output = summaryExercise(id.end());
                    break;
                case "examination-competence":
                    output = registerExaminationCompetence(id.next(), matriculationNumber.end());
                    break;
                case "examination-marking":
                    output = registerExaminationMarking(id.next(), matriculationNumber.next(), mark.end());
                    break;
                default:
                    commandParser.expectArguments(false);
                }
                if (output != null) {
                    Terminal.printLine(output);
                }
            } catch (ParseException e) {
                Terminal.printLine(format("Error, %s", e.getMessage()));
            }
        } while (!command.equals("quit"));
    }

    private static void initializeParsers(Scanner commandScanner) {
        for (TokenParser<?> parser : parsers) {
            parser.setSourceScanner(commandScanner);
        }
    }

    private static void assertExpectArgument(String command) {
        try {
            switch (command) {
            case "add-professor":
                commandParser.expectArguments(true);
                break;
            case "list-professor":
                commandParser.expectArguments(false);
                break;
            case "summary-professor":
                commandParser.expectArguments(true);
                break;
            case "add-instructor":
                commandParser.expectArguments(true);
                break;
            case "list-instructor":
                commandParser.expectArguments(false);
                break;
            case "summary-instructor":
                commandParser.expectArguments(true);
                break;
            case "add-student":
                commandParser.expectArguments(true);
                break;
            case "list-student":
                commandParser.expectArguments(false);
                break;
            case "summary-student":
                commandParser.expectArguments(true);
                break;
            case "add-module":
                commandParser.expectArguments(true);
                break;
            case "list-module":
                commandParser.expectArguments(false);
                break;
            case "summary-module":
                commandParser.expectArguments(true);
                break;
            case "add-lecture":
                commandParser.expectArguments(true);
                break;
            case "list-lecture":
                commandParser.expectArguments(false);
                break;
            case "summary-lecture":
                commandParser.expectArguments(true);
                break;
            case "add-exercise":
                commandParser.expectArguments(true);
                break;
            case "list-exercise":
                commandParser.expectArguments(false);
                break;
            case "summary-exercise":
                commandParser.expectArguments(true);
                break;
            case "examination-competence":
                commandParser.expectArguments(true);
                break;
            case "examination-marking":
                commandParser.expectArguments(true);
                break;
            default:
                commandParser.expectArguments(false);
            }
        } catch (ParseException e) {
            Terminal.printLine(format("Error, %s", e.getMessage()));
        }
    }

    private static String addProfessor(String firstName, String lastName, String chair) {
        MANAGER.addProfessor(firstName, lastName, chair);
        return "OK";
    }

    private static String addInstructor(String firstName, String lastName, String chair) {
        MANAGER.addInstructor(firstName, lastName, chair);
        return "OK";
    }

    private static String addStudent(String firstName, String lastName, int matriculationNumber) {
        MANAGER.addStudent(firstName, lastName, matriculationNumber);
        return "OK";
    }

    private static String addLecture(String lectureName, int moduleId, String firstNameOfProfessor,
            String lastNameOfProfessor, String chair, int credits) {
        MANAGER.addLecture(lectureName, moduleId, firstNameOfProfessor, lastNameOfProfessor, chair, credits);
        return "OK";
    }

    private static String addExercise(String exerciseName, int lectureId, String firstNameOfInstructor,
            String lastNameOfInstructor, String chair) {
        MANAGER.addExercise(exerciseName, lectureId, firstNameOfInstructor, lastNameOfInstructor, chair);
        return "ok";
    }

    private static String addModule(String moduleName) {
        MANAGER.addModule(moduleName);
        return "OK";
    }

    private static String listProfessor() {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Professor p : MANAGER.sortProfessor()) {
            toPrint.add(p.toString());
        }
        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));
        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String listInstructor() {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Instructor i : MANAGER.sortInstructor()) {
            toPrint.add(i.toString());
        }

        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));

        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String listStudent() {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Student s : MANAGER.sortStudent()) {
            toPrint.add(s.toString());
        }
        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));
        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String listLecture() {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Lecture lec : MANAGER.getLectureStock()) {
            toPrint.add(lec.toString());
        }
        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));
        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String listModule() {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Module m : MANAGER.getModuleStock()) {
            toPrint.add(m.toString());
        }
        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));
        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String listExercise() {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Exercise e : MANAGER.getExerciseStock()) {
            toPrint.add(e.toString());
        }
        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));
        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String summaryProfessor(String firstName, String lastName, String chair) {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Professor prof : MANAGER.getProfessorStock()) {
            if (prof.getFirstName().equals(firstName) && prof.getLastName().equals(lastName)
                    && prof.getChair().equals(chair)) {
                for (Lecture l : prof.getLectureList()) {
                    if (l.getAverage() == 0.00) {
                        toPrint.add(format("%d %s %s", l.getCourseId(), l.getCourseName(), "none"));
                    } else {
                        toPrint.add(format("%d %s %.2f", l.getCourseId(), l.getCourseName(), l.getAverage()));
                    }
                }
                for (int i = 0; i < toPrint.size() - 1; i++) {
                    resultBuilder.append(format("%s%n", toPrint.get(i)));
                }
                resultBuilder.append(toPrint.get(toPrint.size() - 1));
            }
        }
        return resultBuilder.toString();
    }

    private static String summaryInstructor(String firstName, String lastName, String chair) {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Instructor instructor : MANAGER.getInstructorStock()) {
            if (instructor.getFirstName().equals(firstName) && instructor.getLastName().equals(lastName)
                    && instructor.getChair().equals(chair)) {
                for (Exercise e : instructor.getExerciseList()) {
                    toPrint.add(format("%d %s %d %s %s", e.getCourseId(), e.getCourseName(),
                            e.getExaminationCompetenceList().size(), e.getLecture().getCourseId(),
                            e.getLecture().getCourseName()));
                }
                for (int i = 0; i < toPrint.size() - 1; i++) {
                    resultBuilder.append(format("%s%n", toPrint.get(i)));
                }
                resultBuilder.append(toPrint.get(toPrint.size() - 1));
            }
        }
        return resultBuilder.toString();

    }

    private static String summaryStudent(String firstName, String lastName, int matriculationNumber) {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Lecture l : MANAGER.getStudentMap().get(matriculationNumber).getLectureList()) {
            if (l.getMarkList().isEmpty()) {
                toPrint.add(format("%d %s %s", l.getCourseId(), l.getCourseName(), "none"));
            } else {
                toPrint.add(format("%d %s %.2f", l.getCourseId(), l.getCourseName(), l.getMark(matriculationNumber)));
            }
        }
        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));
        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String summaryModule(int moduleId) {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        for (Lecture l : MANAGER.getModuleMap().get(moduleId).getLectureList()) {
            if (l.getAverage() == 0.00) {
                toPrint.add(format("%d %s %d %s", l.getCourseId(), l.getCourseName(), l.getLectureCredits(), "none"));
            } else {
                toPrint.add(format("%d %s %d %.2f", l.getCourseId(), l.getCourseName(), l.getLectureCredits(),
                        l.getAverage()));
            }
        }
        for (int i = 0; i < toPrint.size() - 1; i++) {
            resultBuilder.append(format("%s%n", toPrint.get(i)));
        }
        resultBuilder.append(toPrint.get(toPrint.size() - 1));
        return resultBuilder.toString();
    }

    private static String summaryLecture(int lectureId) {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        Lecture lecture = MANAGER.getLectureMap().get(lectureId);
        for (Student s : lecture.getStudentList()) {
            if (lecture.getMarkList().isEmpty()) {
                toPrint.add(
                        format("%d %s %s %s", s.getMatriculationNumber(), s.getFirstName(), s.getLastName(), "none"));
            } else {
                toPrint.add(format("%d %s %s %.2f", s.getMatriculationNumber(), s.getFirstName(), s.getLastName(),
                        lecture.getMark(s.getMatriculationNumber())));
            }
        }
        if (!toPrint.isEmpty()) {
            for (int i = 0; i < toPrint.size() - 1; i++) {
                resultBuilder.append(format("%s%n", toPrint.get(i)));
            }
            resultBuilder.append(toPrint.get(toPrint.size() - 1));
        }
        if (lecture.getExercise() != null) {
            resultBuilder.append(format("%n%d %s %s %s %s", lecture.getExercise().getCourseId(),
                    lecture.getExercise().getCourseName(), lecture.getExercise().getInstructor().getFirstName(),
                    lecture.getExercise().getInstructor().getLastName(),
                    lecture.getExercise().getInstructor().getChair()));
        }
        return resultBuilder.toString();
    }

    private static String summaryExercise(int exerciseId) {
        StringBuilder resultBuilder = new StringBuilder();
        ArrayList<String> toPrint = new ArrayList<>();
        if (!MANAGER.getExerciseMap().get(exerciseId).getExaminationCompetenceList().isEmpty()) {
            for (Student s : MANAGER.getExerciseMap().get(exerciseId).getExaminationCompetenceList()) {
                toPrint.add(format("%d %s %s", s.getMatriculationNumber(), s.getFirstName(), s.getLastName()));
            }
            for (int i = 0; i < toPrint.size() - 1; i++) {
                resultBuilder.append(format("%s%n", toPrint.get(i)));
            }
            resultBuilder.append(toPrint.get(toPrint.size() - 1));
        }
        return resultBuilder.toString();

    }

    private static String registerExaminationCompetence(int exerciseId, int matriculationNumber) {
        MANAGER.registerExaminationCompetence(exerciseId, matriculationNumber);
        return "ok";
    }

    private static String registerExaminationMarking(int lectureId, int matriculationNumber, Double mark) {
        MANAGER.registerExaminationMarking(lectureId, matriculationNumber, mark);
        return "OK";
    }
}
