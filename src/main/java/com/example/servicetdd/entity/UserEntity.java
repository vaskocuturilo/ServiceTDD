package com.example.servicetdd.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "User data should not be null")
    @NotEmpty(message = "User data should not be empty")
    @NotBlank(message = "User data should not be blank")
    @Column(unique = true)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String username;
    @NotNull(message = "User data should not be null")
    @NotEmpty(message = "User data should not be empty")
    @NotBlank(message = "User data should not be blank")
    private String password;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
