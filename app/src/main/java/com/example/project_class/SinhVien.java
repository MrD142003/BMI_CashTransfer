package com.example.project_class;

public class SinhVien {
    private int id;
    private String name;
    private int yearOB;

    public SinhVien(int id, String name, int yearOB) {
        this.id = id;
        this.name = name;
        this.yearOB = yearOB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOB() {
        return yearOB;
    }

    public void setYearOB(int yearOB) {
        this.yearOB = yearOB;
    }
}
