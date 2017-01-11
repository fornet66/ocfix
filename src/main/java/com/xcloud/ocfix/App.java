package com.xcloud.ocfix;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

public class App {

    public static void main(String[] args) {
        String url = "http://oss-cn-hangzhou-iaas-d01-a.iaas.zj.pcc";
        String access = "nTnleA2I56jebWma";
        String secret = "aBnknOTdJGIdBJwdEtLiQ5bLV4QqpP";
        String bucket = "oss-yunpan";
        OSSClient client = new OSSClient(url, access, secret, new ClientConfiguration().setSupportCname(false));
        client.listObjects(bucket, "config");
        client.doesObjectExist(bucket, "config");
        client.doesObjectExist(bucket, "config/");
    }
}
