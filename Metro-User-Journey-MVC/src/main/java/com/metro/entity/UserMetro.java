package com.metro.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserMetro {

	@Id
	private String userName;
	private double balance;
	private String swipeIn, swipeOut;
	private double totalFare;
	
	
}
