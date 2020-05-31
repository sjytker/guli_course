package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OssService {


    String uploadFileAvatar(MultipartFile file) throws IOException;
}
