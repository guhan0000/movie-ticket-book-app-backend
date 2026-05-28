package com.moviebookapp.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 10)
    private String phone;
    private String role;

}
