package com.krishtech.candidate.apitest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.krishtech.candidate.base.BaseIntegrationTest;

public class CandidateControllerTest extends BaseIntegrationTest {

	
	
	/*
	 * Test All candidate retrieval
	 */
	@Test
	public void testGetCandidates() {
		
	
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List<?>> response = restTemplate.exchange(
				createURLWithPort("candidates"),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<?>>() {});
		
		assertTrue(response.getBody() == null || response.getBody().size() > 0);
	}	
	
	
	/*
	 * Test candidate retrieval for a given candidate id
	 */
	@Test
	public void testGetCandidate() {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("candidates/1"),
				HttpMethod.GET, entity, String.class);
		String expected = "{\"firstName\":\"krishna\",\"lastName\":\"periyasamy\",\"email\":\"jpkrishnap@yahoo.com\",\"phoneNumber\":\"650-123-4567\",\"resumeS3Key\":\"krishna_resume\"}";
		try {
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Test No candidate in DB for a given order Id
	 */
	@Test
	public void testCandidateNotFound() {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("candidates/-1000"),
				HttpMethod.GET, entity, String.class);
		String expected = null;
		try {
			assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
