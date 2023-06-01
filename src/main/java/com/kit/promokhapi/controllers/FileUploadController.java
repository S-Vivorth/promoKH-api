package com.kit.promokhapi.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kit.promokhapi.dto.ResponseDTO;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final S3Client s3Client;
    private final String bucketName;
    private final long MAX_FILE_SIZE = DataSize.ofMegabytes(5).toBytes();

    public FileUploadController(S3Client s3Client, @Value("${aws.bucketName}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<String>> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        // Check if the uploaded file is an image
        // Check the file's content type to determine if it's an image
        String contentType = file.getContentType();
        Boolean isFile = contentType != null && contentType.startsWith("image/");
        if (!isFile) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "Only image file is allowed.", null));
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "File size exceeds the limit 5MB.", null));
        }

        String fileExtention = "";
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex >= 0 && dotIndex < originalFilename.length() - 1) {
                fileExtention = originalFilename.substring(dotIndex);
            }
        }

        // Generate a unique filename
        String filename = UUID.randomUUID().toString() + fileExtention;

        // Upload the file to the S3 bucket
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Generate the public URL for the uploaded file
        String publicUrl = "https://" + bucketName + ".s3.amazonaws.com/" + filename;

        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.CREATED.value(), "success", publicUrl));
    }
}
