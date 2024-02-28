package com.bankApp.bankApp.Utili.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginCred {
    // Getters and Setters
    @JsonProperty("email") // Map JSON field name to Java field name
    private String email;

    @JsonProperty("password") // Map JSON field name to Java field name
    private String password;

    // Constructors
    public LoginCred(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginCred() {
    }

}
