package com.doogle.flyway.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "REGISTRATION_USERS")
public class user {

    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
