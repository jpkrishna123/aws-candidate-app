package com.krishtech.candidate.test.pojo;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.krishtech.candidate.base.BaseIntegrationTest;
import com.krishtech.candidate.model.Candidate;

/*
 * Unit test Order domain object
 */
public class CandidateTest extends BaseIntegrationTest {
	
	 @Test
	 public void testOrder(){
		 
		 Candidate candidate = createCandidate("krishna", "periyasamy", "jpkrishnap@yahoo.com", 
				 "650-123-4567", "krishna_resume");
		

		 assertTrue("krishna".equals(candidate.getFirstName()));
		 assertTrue("periyasamy".equals(candidate.getLastName()));
		 assertTrue("jpkrishnap@yahoo.com".equals(candidate.getEmail()));
		 assertTrue("650-123-4567".equals(candidate.getPhoneNumber()));
		 assertTrue("krishna_resume".equals(candidate.getResumeS3Key()));
	 }

	 
	
}
