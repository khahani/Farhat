package com.khahani.app.farhat;

public class Person {
    private String name;
    private String lastName;
    private String code;
    private String phone;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Person(String name, String lastName, String code, String phone) {

        this.name = name;
        this.lastName = lastName;
        this.code = code;
        this.phone = phone;
    }
}
