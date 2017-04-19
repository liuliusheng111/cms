package com.jflyfox;

/**
 * Created by admin on 17/4/18.
 */
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;

import java.io.IOException;
import java.util.UUID;

/**
 * 上传到七牛返回 hash 和 key
 */
public class QiniuUploadTest {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = "btdWf0hTxxlamaZeKKB3bWslQZzfTqvaJhTjepBe";
    private static final String SECRET_KEY = "8tpn4xlgdCYXpit3Qo7PcoASw82jmigzs9qTc6BG";
    //要上传的空间
    private static final String BUCKET_NAME = "ybsf-image";


    public static void main(String args[]) throws IOException {

        new QiniuUploadTest().upload();
    }


    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKET_NAME);
    }
    public void upload() throws IOException {
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);

        //上传到七牛后保存的文件名
        String key = "enter/test.png";
        //上传文件的路径
        String FilePath = "/tmp/test.jpg";
        try {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            //响应的文本信息
            System.out.println(r.bodyString());
        }
    }
}
