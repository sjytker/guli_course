package com.atguigu.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName += uuid;
        String dataPath = new DateTime().toString("yyyy/MM/dd");
        fileName = dataPath + "/" + fileName;

        ossClient.putObject(bucketName, fileName, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
        //https://guli-eduweb.oss-cn-hongkong.aliyuncs.com/01.jpg
        String url = "https://" + bucketName + "." + endpoint + '/' + fileName;
        return url;
    }
}
