package com.lomiguk.springapp_l.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private long id;
    @NotEmpty(message = "Name should be exist!")
    @Size(min = 2, max = 30, message = "Name size should be between 2 and 30")
    private String name;
    @NotEmpty(message = "Phone number should not be empty")
    private String phone;
    @Min(value = 16, message = "Age should be greater than 16")
    private int age;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "It's not a email")
    private String email;

    public Person(long id, String name, String phone, int age, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.email = email;
    }

    public Person() {

    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
