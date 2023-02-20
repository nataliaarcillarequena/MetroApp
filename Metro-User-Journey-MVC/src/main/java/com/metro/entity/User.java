package com.metro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data   //generates all the getters and setters and toString and Equals etc (all the boilerplate code associated with POJOs) 
public class User {

	String firstName, surname;
	String userName;
	String password;
	double balance; 
}