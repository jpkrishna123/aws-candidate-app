package com.krishtech.candidate.base;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.krishtech.candidate.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest extends BaseTest {

		
	@LocalServerPort
	protected int port;

	protected TestRestTemplate restTemplate = new TestRestTemplate();

	protected HttpHeaders headers = new HttpHeaders();

	
	protected String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	@Test
	public void testPort() {
		 assertTrue(!"".equals(createURLWithPort("/candidates")));
	}
}
