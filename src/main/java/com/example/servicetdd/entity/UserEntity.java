package com.example.servicetdd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "User data should not be null")
    @NotEmpty(message = "User data should not be empty")
    @NotBlank(message = "User data should not be blank")
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
