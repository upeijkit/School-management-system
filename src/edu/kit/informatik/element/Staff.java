package edu.kit.informatik.element;

public class Staff extends User {
    private String chair;

    public Staff(String firstName, String lastName, String chair) {
        super(firstName, lastName);
        this.chair = chair;
    }

    public String getChair() {
        return chair;
    }

}
