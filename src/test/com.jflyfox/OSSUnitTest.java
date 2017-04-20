package com.jflyfox;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;

public class OSSUnitTest {

    //log
    private static final Logger LOG = Logger.getLogger(OSSUnitTest.class);

    private OSSUnit ossunit = null;
    private OSSClient client = null;
    private String bucketName = "ybsf-image";
    @Before
    public void initUnit(){
        ossunit = new OSSUnit();
        client = OSSUnit.getOSSClient();
    }

    @Test
    public void testCreateBucket() {
        String bucketName = "oss-test-my";
        //创建bucket
        assertEquals(true, OSSUnit.createBucket(client, bucketName));
    }

    @Test
    public void testDeleteBucket(){
        String bucketName = "oss-test-my";
        //删除bucket
        OSSUnit.deleteBucket(client, bucketName);
        //console log :删除oss-test-myBucket成功
    }

    @Test
    public void testUploadObject2OSS(){
        //上传文件
        String flilePathName = "C:/Users/Tony_tian/Desktop/csdnimg/preossimg.jpg";
        File file = new File(flilePathName);
        String diskName = "datas/image/";
        String md5key = OSSUnit.uploadObject2OSS(client, file, bucketName, diskName);
        LOG.info("上传后的文件MD5数字唯一签名:" + md5key);  //上传后的文件MD5数字唯一签名:A30B046A34EB326C4A3BBD784333B017
    }

    @Test
    public void testGetOSS2InputStream(){
        //获取文件
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    OSSUnit.getOSS2InputStream(client, bucketName, "enter/", "cuphigh.png")
            );
            String resfile = "/tmp/test.jpg";
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(resfile)));
            int itemp = 0;
            while((itemp = bis.read()) != -1){
                bos.write(itemp);
            }
            LOG.info("文件获取成功"); //console log :文件获取成功
            bis.close();
            bos.close();
        } catch (Exception e) {
            LOG.error("从OSS获取文件失败:" + e.getMessage(), e);
        }
    }

    @Test
    public void testDeleteFile(){
        //注：文件夹下只有一个文件，则文件夹也会一起删除；如果多个文件，只会删除指定文件名的文件
        //删除文件
        OSSUnit.deleteFile(client, bucketName, "datas/image/", "preossimg.jpg");
        //console log : 删除oss-test-my下的文件datas/image/preossimg.jpg成功
    }

}

