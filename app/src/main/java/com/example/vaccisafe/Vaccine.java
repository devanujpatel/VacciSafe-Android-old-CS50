package com.example.vaccisafe;

public class Vaccine {
    private String vaccine_name;
    private String taken_at;
    private String mark_as;

    public Vaccine(String vaccine_name, String taken_at, String mark_as) {
        this.vaccine_name = vaccine_name;
        this.taken_at = taken_at;
        this.mark_as = mark_as;
    }

    public String getMark_as() {
        return mark_as;
    }

    public void setMark_as(String mark_as) {
        this.mark_as = mark_as;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getTaken_at() {
        return taken_at;
    }

    public void setTaken_at(String taken_at) {
        this.taken_at = taken_at;
    }
}
