package com.lomiguk.springapp.model.priofile.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileLoginDto {
    @NotEmpty
    @Size(min = 5, max = 15, message = "Login size should be 5 – 15")
    private String login;
    @NotEmpty
    private String password;
}
