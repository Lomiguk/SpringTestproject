package com.lomiguk.springapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
