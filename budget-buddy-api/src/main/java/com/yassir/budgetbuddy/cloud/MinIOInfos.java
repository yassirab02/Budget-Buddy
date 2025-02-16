package com.yassir.budgetbuddy.cloud;
import jakarta.persistence.*;
@Entity
public class MinIOInfos {
    @Id
    @SequenceGenerator(name = "minioinfos_seq", sequenceName = "minioinfos_seq", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "minioinfos_seq")
    private Long id;
    private String bucketName;
    private String link;
    private String originalFileName;
    private long fileSize;
    private byte[] bytes;
    private String etag;
    private int resultStatus;

    public MinIOInfos(String bucketName, String originalFileName, long fileSize, byte[] bytes, String etag, int resultStatus, String link) {
        this.bucketName = bucketName;
        this.originalFileName = originalFileName;
        this.fileSize = fileSize;
        this.bytes = bytes;
        this.etag = etag;
        this.resultStatus = resultStatus;
        this.link = link;
    }
    public MinIOInfos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
