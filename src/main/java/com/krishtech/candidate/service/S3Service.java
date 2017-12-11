package com.krishtech.candidate.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.krishtech.candidate.exception.FileArchiveServiceException;

@Service
public class S3Service {
	
	private Logger logger = LoggerFactory.getLogger(S3Service.class);
	
	//@Autowired
	private AmazonS3Client s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;

	/**
	 * Save resume to S3 and return resume key
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public String save(MultipartFile multipartFile) throws FileArchiveServiceException {

		try{
			File fileToUpload = convertFromMultiPart(multipartFile);
			String key = Instant.now().getEpochSecond() + "_" + fileToUpload.getName();
			logger.info("Uploading content : " + key);
			s3Client.putObject(new PutObjectRequest(bucketName, key, fileToUpload));
			logger.info("Upload File - Done.");
			return key;
		}
		catch(Exception ex){			
			throw new FileArchiveServiceException("An error occurred saving file to S3", ex);
		}		
	}
	
	/**
	 * get resume to S3 and return as Inputstream
	 * 
	 * @param key as String
	 * @return as InputStream
	 * @throws IOException
	 */
	public InputStream get(final String key) throws Exception {
		InputStream stream = null;
		try {
			System.out.println("Downloading an object : " + key);
            S3Object s3object = s3Client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: "  + s3object.getObjectMetadata().getContentType());
            logger.info("Import File - Done. ");
            stream = s3object.getObjectContent();
        }catch (Exception ex) {
        	throw new FileArchiveServiceException("An error occurred while downloading file to S3", ex);
        }
		return stream;
	}

	/**
	 * Delete resume from S3 using specified key
	 * 
	 * @param customerImage
	 */
	public void deleteImageFromS3(String key){
		s3Client.deleteObject(new DeleteObjectRequest(bucketName, key));	
	}

	/**
	 * Convert MultiPartFile to ordinary File
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	private File convertFromMultiPart(MultipartFile multipartFile) throws IOException {

		File file = new File(multipartFile.getOriginalFilename());
		file.createNewFile(); 
		FileOutputStream fos = new FileOutputStream(file); 
		fos.write(multipartFile.getBytes());
		fos.close(); 

		return file;
	}
}