package com.movieBookV2.model;

import com.movieBookV2.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import lombok.Data;



import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String custName;
    @Column(unique = true)
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
    @Length(max = 10)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
//    private String role;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonBackReference
    private Wallet wallet;
    

}
