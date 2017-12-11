package com.krishtech.candidate.model;

import java.util.Date;

/*import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;*/

import lombok.Getter;
import lombok.Setter;

//@Entity(name="candidate_detail")
public class Candidate{

	public Candidate(){}
	
	public Candidate(final String firstName, final String lastName, final String email, 
			final String phoneNumber, final String resumeS3Key) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.resumeS3Key = resumeS3Key;
	}

	//@Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	

	//@Column(name="first_name", nullable = false, length = 30)
	private String firstName;

	//@Column(name="first_name", nullable = false, length = 30)
	private String lastName;

	//@Column(name="email", nullable = false)
	private String email;
	
	//@Column(name="phone_number", nullable = false)
	private String phoneNumber;
	
	//@Column(name="resume_s3_key", nullable = false)
	private String resumeS3Key;
	

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getResumeS3Key() {
		return resumeS3Key;
	}

	public void setResumeS3Key(final String resumeS3Key) {
		this.resumeS3Key = resumeS3Key;
	}
	
}