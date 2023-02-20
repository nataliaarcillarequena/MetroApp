package com.user.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data   //generates all the getters and setters and toString and Equals etc (all the boilerplate code associated with POJOs)

@Entity //defines that a class can be mapped to a table (our user table in SQL) 
public class User {

	String firstName, surname;
	@Id
	String userName;
	String password;
	double balance; 
}
