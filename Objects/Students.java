package com.mycompany.app.Objects;

public class Students {
    public int id;
    public String letters;
    public int numb_of_group;
    public String faculty;
    public Hostel hostel;
    public int privilege;
    public String state;
    public String date;

    public Students() {}

    public Students(int id, String letters, String faculty) {
        this.id = id;
        this.letters = letters;
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Students{" +
                "id=" + id +
                ", letters='" + letters + '\'' +
                ", numb_of_group=" + numb_of_group +
                ", faculty='" + faculty + '\'' +
                ", hostel=" + hostel +
                ", privilege=" + privilege +
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
