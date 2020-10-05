package com.wiremock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserDetails //extends ApiResponse 
{
	private String id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String city;
	private String country;
}
