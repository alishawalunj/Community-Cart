package com.nzefler.community.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(@Value("${aws.accessKeyId}") String accessKey,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") String region) {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadFile(String folder, String fileName, byte[] fileData) throws IOException {
        String key = folder + "/" + fileName;

        File tempFile = File.createTempFile("upload-", fileName);
        Files.write(tempFile.toPath(), fileData);

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType("image/jpeg")
                        .build(),
                RequestBody.fromFile(tempFile)
        );

        tempFile.delete();

        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
}
