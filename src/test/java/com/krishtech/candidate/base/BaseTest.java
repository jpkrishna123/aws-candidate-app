package com.krishtech.candidate.base;

import com.krishtech.candidate.model.Candidate;

public class BaseTest {

	protected Candidate createCandidate(final String firstName, final String lastName, final String email, 
			final String phoneNumber, final String resumeS3Key) {
		return new Candidate(firstName, lastName, email, phoneNumber, resumeS3Key);
	}
	
}
