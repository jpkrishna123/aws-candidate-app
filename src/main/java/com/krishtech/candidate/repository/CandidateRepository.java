package com.krishtech.candidate.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.krishtech.candidate.model.Candidate;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {

    public List<Candidate> findByFirstName(String firstName); 
}