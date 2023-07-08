package com.kit.promokhapi.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kit.promokhapi.dto.ResponseDTO;
import com.kit.promokhapi.service.AwsS3Service;

import java.net.URL;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/promo_kh")
public class FileUploadController {

    private final AwsS3Service awsS3Service;
    private final String bucketName;

    public FileUploadController(AwsS3Service awsS3Service,
            @Value("${aws.bucketName}") String bucketName) {
        this.awsS3Service = awsS3Service;
        this.bucketName = bucketName;
    }

    @GetMapping("/generate-upload-url")
    public ResponseEntity<ResponseDTO<URL>> generateUploadUrl() {
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.CREATED.value(), "success",
                awsS3Service.generatePreSignedUrl(UUID.randomUUID().toString(), bucketName)));
    }
}
