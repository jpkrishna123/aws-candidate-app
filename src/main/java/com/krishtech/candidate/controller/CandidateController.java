package com.krishtech.candidate.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.krishtech.candidate.model.Candidate;
import com.krishtech.candidate.repository.CandidateRepository;
import com.krishtech.candidate.service.S3Service;

/**
 * Candidates Controller exposes a series of RESTful endpoints
 */
@RestController
public class CandidateController {

	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private S3Service s3Service; 
	
	private Logger logger = LoggerFactory.getLogger(CandidateController.class);
	 
	
	@RequestMapping(value = "/candidates", method = RequestMethod.POST)
    public ResponseEntity<?> createCandidate(            
            @RequestParam(value="firstName", required=true) String firstName,
            @RequestParam(value="lastName", required=true) String lastName,
            @RequestParam(value="email", required=true) String email,
            @RequestParam(value="phoneNumber", required=true) String phoneNumber,
            @RequestParam(value="resume", required=true) MultipartFile resume) {

			Candidate candidate = null;
			try {
				String key = s3Service.save(resume);        	
        		candidate = new Candidate(firstName, lastName, email, phoneNumber, key);
        		candidate = candidateRepository.save(candidate);
			}catch(Exception e) {
				logger.error("createCandidate(): Exception occurred : " + e.getMessage());
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
        	
            return ok(candidate);               
    }
	
	/**
	 * Get customer using id. Returns HTTP 404 if customer not found
	 * 
	 * @param customerId
	 * @return retrieved customer
	 */
	@RequestMapping(value = "/candidates/{candidateId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCandidate(@PathVariable("candidateId") Long candidateId) {
		
		// validate candidate id parameter
		if (candidateId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Candidate candidate = null;
		try {
			candidate = candidateRepository.findOne(candidateId);
		
			if(candidate == null){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			logger.error("getCandidate(): Exception occurred : " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ok(candidate);
	}
	
	/**
	 * Gets all customers.
	 *
	 * @return the customers
	 */
	@RequestMapping(value = "/candidates", method = RequestMethod.GET)
	public ResponseEntity<?> getCandidates() {
		
		List<Candidate> candidates = null;
		
		try {
			candidates = (List<Candidate>) candidateRepository.findAll();
			
		}catch(Exception e) {
			logger.error("getCandidates(): Exception occurred : " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ok(candidates);
	}
	
	/**
	 * Gets all customers.
	 *
	 * @return the customers
	 */
	@RequestMapping(value = "/resume/{resumeKey}", method = RequestMethod.GET)
	public ResponseEntity<?> getResumeContent(@PathVariable("resumeKey") String resumeKey) {
		
		// validate candidate id parameter
		if (resumeKey == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			 logger.info("Get the resume : " + resumeKey);
             HttpHeaders respHeaders = new HttpHeaders();
             respHeaders.setContentDispositionFormData("attachment", resumeKey);
             InputStreamResource isr = new InputStreamResource(s3Service.get(resumeKey));
             return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		 } catch (Exception e) {
        	 logger.error("getResumeContent(): Exception occurred : " + e.getMessage());
        	 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
	}
	
	

}