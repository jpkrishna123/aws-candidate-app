DROP SCHEMA IF EXISTS rds_candidate;
CREATE SCHEMA IF NOT EXISTS rds_candidate DEFAULT CHARACTER SET utf8;
USE rds_candidate; 
  
CREATE TABLE IF NOT EXISTS rds_candidate.candidate_detail (
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  email VARCHAR(50) NOT NULL,
  phone_number VARCHAR(15) NOT NULL,
  resume_s3_key VARCHAR(100) NOT NULL
  PRIMARY KEY (id));    