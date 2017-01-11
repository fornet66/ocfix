package com.xcloud.ocfix;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

public class OssUtils {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public boolean findObject(String url) {
        OSSClient client = new OSSClient(endpoint, accessKey, secretKey, new ClientConfiguration().setSupportCname(false));
        return client.doesObjectExist(bucketName, url);
    }

}
