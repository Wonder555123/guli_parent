package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

//    上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try {

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            // 生成为唯一的随机的值
            String uuid = UUID.randomUUID().toString().replace("-","");
            String fileName = file.getOriginalFilename();
            fileName = uuid+fileName;
//            获取当前时间,为了得到时间便于在OSS中进行分文件夹存储
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath+"/"+fileName;
            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
