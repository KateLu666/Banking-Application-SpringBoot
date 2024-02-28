package com.bankApp.bankApp.Utili.DTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginCreds {
    // Getters and Setters
    @JsonProperty("email") // Map JSON field name to Java field name
    private String email;

    @JsonProperty("password") // Map JSON field name to Java field name
    private String password;

    // Constructors
    public LoginCreds(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginCreds() {
    }

}
