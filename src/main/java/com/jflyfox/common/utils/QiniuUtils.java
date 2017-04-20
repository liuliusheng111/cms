package com.jflyfox.common.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.FileItem;

/**
 * 上传到七牛服务器的接口
 * Created by admin on 17/4/20.
 */
public class QiniuUtils {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = "btdWf0hTxxlamaZeKKB3bWslQZzfTqvaJhTjepBe";
    private static final String SECRET_KEY = "8tpn4xlgdCYXpit3Qo7PcoASw82jmigzs9qTc6BG";
    //要上传的空间
    private static final String BUCKET_NAME = "ybsf-image";


    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 上传到七牛
     * @param bytes 上传对象
     * @param name 名称
     */
    public static void upload2Qiniu(byte[] bytes,String name) {
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        try {
            String key = "enter/"+name;
            //调用put方法上传
            uploadManager.put(bytes, key, getUpToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
