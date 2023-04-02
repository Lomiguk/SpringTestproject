package com.lomiguk.springapp_l.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Profile {
    private long id;
    @NotEmpty
    @Size(min = 5, max = 15, message = "Login size should be 5 – 15")
    private String login;
    @NotEmpty
    private String password;
    private boolean isAdmin;
}
