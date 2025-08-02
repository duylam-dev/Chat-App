package com.hust.chatappbackend.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MinioInitializer implements ApplicationRunner {

    private final MinioClient minioClient;
    public static final String COMMON_BUCKET_NAME = "avatar";

    @Override
    public void run(ApplicationArguments args) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(COMMON_BUCKET_NAME).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(COMMON_BUCKET_NAME).build());
                log.info("✅ Created bucket '{}'", COMMON_BUCKET_NAME);
            } else {
                log.info("✅ Bucket '{}' already exists", COMMON_BUCKET_NAME);
            }
        } catch (Exception e) {
            log.error("❌ Failed to check or create bucket '{}': {}", COMMON_BUCKET_NAME, e.getMessage(), e);
            throw new IllegalStateException("MinIO bucket setup failed", e);
        }
    }
}