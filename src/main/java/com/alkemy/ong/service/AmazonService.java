package com.alkemy.ong.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonService {

    String uploadFile(MultipartFile multipartFile);

    String deleteFileFromS3Bucket(String fileUrl);

}
