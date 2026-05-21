package com.moviebookapp.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String custName;
    @Column(unique = true)
    @Email
    private String email;
    private String password;
    private String role;

}
