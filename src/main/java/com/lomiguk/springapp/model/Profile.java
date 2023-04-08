package com.lomiguk.springapp.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private long id;
    @NotEmpty
    @Size(min = 5, max = 15, message = "Login size should be 5 – 15")
    private String login;
    @NotEmpty
    private String password;
    private boolean isAdmin;
}
