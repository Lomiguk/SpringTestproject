package com.lomiguk.springapp.model.priofile.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProfileLoginDto {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
