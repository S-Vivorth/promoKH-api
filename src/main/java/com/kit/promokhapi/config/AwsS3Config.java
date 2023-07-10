package com.kit.promokhapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKeyId}")
    private String awsAccessKeyId;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Bean
    public AmazonS3 getAmazonS3Client() {
        final BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretKey);
        // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials))
            .withRegion(awsRegion)
            .build();
    }
}
