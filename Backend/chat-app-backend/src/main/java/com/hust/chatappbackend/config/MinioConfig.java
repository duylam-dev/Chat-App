package com.hust.chatappbackend.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {
    private final GlobalConfig globalConfig;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(globalConfig.getMinioUrl())
                .credentials(globalConfig.getMinioUsername(), globalConfig.getMinioPassword())
                .build();
    }
}