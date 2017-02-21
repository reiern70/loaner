package com.antilia.loan.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User of the system
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames={"email","role"})})
public class User extends EntityBase {

    public static String ANONYMOUS_USER = "anonymous@loans.com";
    @Column(nullable = false)
    private String name;
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private UserRole role;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!email.equals(user.email)) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
