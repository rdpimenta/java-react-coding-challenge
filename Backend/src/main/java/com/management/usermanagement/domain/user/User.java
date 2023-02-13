package com.management.usermanagement.domain.user;

import jakarta.persistence.*;

@Table(name = "users")
@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;

    public User() {

    }

    public User(String email, String firstName, String lastName, Role role, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public void updateUser(UserDto userDto) {
        this.firstName = userDto.firstName();
        this.lastName = userDto.lastName();
        this.role = userDto.role();
    }
}
