package com.tysong.bird.admin.api.controller;

import com.google.common.collect.Lists;
import com.tysong.bird.admin.api.common.ApiResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${bird.file.upload.path}")
    public String uploadPath;

    @Value("${bird.file.upload.host:localhost}")
    public String uploadHost;

    @Value("${server.port}")
    public String serverPort;

    @RequestMapping("uploadBatch")
    public ApiResponse uploadBatch(@RequestParam("file") List<MultipartFile> file) throws IOException {
        List<String> returnList= Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(file)){
            for (int i=0;i<file.size();i++){
                MultipartFile multipartFile = file.get(i);
                String filename = multipartFile.getOriginalFilename();
                InputStream inputStream = multipartFile.getInputStream();
                FileUtils.copyToFile(inputStream,new File(uploadPath+filename));
                String returnUrl="http://"+uploadHost+":"+serverPort+"/"+filename;
                returnList.add(returnUrl);
            }
        }
        return ApiResponse.ok(returnList);
    }

    @RequestMapping("upload")
    public ApiResponse upload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        FileUtils.copyToFile(inputStream,new File(uploadPath+filename));
        String returnUrl="http://"+uploadHost+":"+serverPort+"/"+filename;
        return ApiResponse.ok(returnUrl);
    }
}
