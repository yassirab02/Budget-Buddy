package com.yassir.budgetbuddy.cloud;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/cloud")
public class MinioWs {
    @Autowired
    private MinIOService minIOService;

    @Autowired
    private MinioClient minioClient;

    @GetMapping("/bucket/{bucket}")
    public Boolean bucketExists(@PathVariable String bucket) {
        return minIOService.bucketExists(bucket);
    }
    @Operation(summary = "Upload a file to the bucket")
    @PostMapping("/upload/file/{bucket}")
    public MinIOInfos uploadToMinio(@RequestParam("file") MultipartFile file, @PathVariable String bucket) {
        return minIOService.uploadToMinio(file, bucket);
    }

    @Operation(summary = "Upload multiple files to the bucket")
    @PostMapping("/upload/bucket/{bucket}")
    public List<MinIOInfos> uploadMultipleToMinio(@RequestParam("files") List<MultipartFile> files, @PathVariable String bucket) {
        return minIOService.uploadMultipleToMinio(files, bucket);
    }

    @PostMapping("/bucket/{bucket}")
    public int saveBucket(@PathVariable String bucket) {
        return minIOService.saveBucket(bucket);
    }

    @GetMapping("/bucket/{bucket}/documents")
    public List<String> findAllDocuments(@PathVariable String bucket) throws MinioException {
        return minIOService.getAllDocumentsNames(bucket);
    }

    @GetMapping("/bucket/{bucket}/document/{document}")
    public byte[] loadDocument(@PathVariable String bucket, @PathVariable String document) throws MinioException {
        return minIOService.LoadDocument(bucket, document);
    }

    @PostMapping("/tags/bucket/{bucket}/document/{document}")
    public void setObjectTags(@PathVariable String bucket, @PathVariable String document, @RequestParam() Map<String, String> tags) throws MinioException {
        minIOService.setObjectTags(bucket, document, tags);
    }


   @GetMapping("/download/bucket/{bucket}/document/{document}")
    public void downloadFile(@PathVariable String bucket, @PathVariable String document, HttpServletResponse response) {
        try {
            // Download the object from MinIO
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(document)
                            .build()
            );
            // Determine the content type based on the file extension
            String contentType;
            if (document.endsWith(".pdf")) {
                contentType = "application/pdf";
            } else if (document.endsWith(".ppt") || document.endsWith(".pptx")) {
                contentType = "application/vnd.ms-powerpoint";
            } else if (document.endsWith(".doc") || document.endsWith(".docx")) {
                contentType = "application/msword";
            } else {
                // Default to binary data if file type is not recognized
                contentType = "application/octet-stream";
            }
            // Set the content type and attachment header
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + document + "\"");
            // Copy the file content to the response output stream
            ServletOutputStream out = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            out.close();
            stream.close();
            System.out.println("The object was downloaded successfully.");
        } catch (Exception e) {
            // Handle exceptions here
            System.err.println("An error occurred while downloading the object: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download-v2/bucket/{bucket}/document/{document}")
    public ResponseEntity<byte[]> downloadFile2(@PathVariable String bucket, @PathVariable String document) {
        try {
            // Download the object from MinIO
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(document)
                            .build()
            );
            // Read the file content into a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            byte[] fileBytes = outputStream.toByteArray();
            // Determine the content type based on the file extension
            String contentType;
            if (document.endsWith(".pdf")) {
                contentType = "application/pdf";
            } else if (document.endsWith(".ppt") || document.endsWith(".pptx")) {
                contentType = "application/vnd.ms-powerpoint";
            } else if (document.endsWith(".doc") || document.endsWith(".docx")) {
                contentType = "application/msword";
            } else {
                // Default to binary data if file type is not recognized
                contentType = "application/octet-stream";
            }
            // Return the file content along with filename and content type
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("attachment", document);
            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions here
            System.err.println("An error occurred while downloading the object: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
