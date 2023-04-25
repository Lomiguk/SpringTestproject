package com.lomiguk.springapp.model.priofile;

import jakarta.validation.constraints.Min;
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
    @Min(value = 5, message = "Password must be more then 8 characters long")
    private char[] password;
    private boolean admin;

    public boolean getIsAdmin() {
        return admin;
    }
}
