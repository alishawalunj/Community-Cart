package com.nzefler.user_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.ToString;



@AllArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    @Column(name="username", nullable=false)
    private String userName;
    @Column(name="email", nullable=false)
    private String email;
    @Column(name="password", nullable=false)
    private String password;

    public User() {

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
