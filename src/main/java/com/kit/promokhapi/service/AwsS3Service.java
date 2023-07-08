package com.kit.promokhapi.service;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class AwsS3Service {

    @Autowired
    AmazonS3 amazonS3;

    public URL generatePreSignedUrl(String filePath, String bucketName) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10); // validity of 10 minutes
        return amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), com.amazonaws.HttpMethod.PUT);
    }
}