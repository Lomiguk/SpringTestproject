package com.lomiguk.springapp.model.priofile.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileRegisterDto {
    @NotNull
    @Size(min = 5, max = 15, message = "Login size should be 5 – 15")
    private String login;
    @NotNull
    @Min(value = 5, message = "Password must be more then 8 characters long")
    private String passwordF;
    @NotNull
    private String passwordS;
}
