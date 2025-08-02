package com.hust.chatappbackend.util;

import com.hust.chatappbackend.config.GlobalConfig;
import com.hust.chatappbackend.config.MinioInitializer;
import com.hust.chatappbackend.exception.AppException;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MinioUtil {

    private final MinioClient minioClient;
    private final GlobalConfig globalConfig;

    /**
     * Lưu file lên MinIO với tên là UUID, truyền bucket.
     */
    public String save(String bucketName, MultipartFile file, UUID uuid) {
        try {
            // Tạo bucket nếu chưa tồn tại (tuỳ chọn)
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uuid.toString())
                            .stream(file.getInputStream(), file.getSize(), globalConfig.getPutObjectPartSize())
                            .contentType(file.getContentType())
                            .build()
            );

            return uuid.toString();
        } catch (Exception e) {
            throw new AppException("❌ Failed to save file to MinIO: " + e.getMessage());
        }
    }

    public String save(MultipartFile file, UUID uuid) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(MinioInitializer.COMMON_BUCKET_NAME)
                            .object(uuid.toString())
                            .stream(file.getInputStream(), file.getSize(), globalConfig.getPutObjectPartSize())
                            .build()
            );

            // Trả về endpoint backend để truy cập file này
            return uuid.toString();
        } catch (Exception e) {
            throw new AppException("❌ Failed to save file to MinIO: " + e.getMessage());
        }
    }

    /**
     * Lấy InputStream từ object trong MinIO.
     */
    public InputStream getInputStream(String bucketName, UUID uuid, long offset, long length) {
        try {
            GetObjectArgs.Builder builder = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(uuid.toString());

            if (offset > 0) builder.offset(offset);
            if (length > 0) builder.length(length);

            return minioClient.getObject(builder.build());
        } catch (Exception e) {
            throw new AppException("❌ Failed to get file from MinIO: " + e.getMessage());
        }
    }

    /**
     * Xoá file khỏi MinIO.
     */
    public void delete(String bucketName, UUID uuid) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uuid.toString())
                            .build()
            );
        } catch (Exception e) {
            throw new AppException("❌ Failed to delete file from MinIO: " + e.getMessage());
        }
    }
}
