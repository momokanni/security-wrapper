package com.security.web.controller;

import com.security.web.entity.FileInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * @description: 文件上传
 * @author: sca
 * @create: 2019-07-21 19:34
 **/
@Slf4j
@RestController
@Api(tags = "文件服务")
@RequestMapping(value ="/file")
public class DemoFileUpload {

    static final String FOLDER = "/Users/sunlin/Documents/securitySource";
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IllegalStateException, IOException {
        log.info("文件名称: {} , 原始文件名: {} , 文件大小: {}" , file.getName(),file.getOriginalFilename(),file.getSize());

        File localFile = new File(FOLDER,System.currentTimeMillis()+".txt");
        //将传入的file写入到程序本地文件中
        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping(value = "/{id}")
    public void downLoad(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (
                InputStream inStream = new FileInputStream(new File(FOLDER,id+".txt"));
                OutputStream outputStream = response.getOutputStream();
        ){
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename-test.txt");

            IOUtils.copy(inStream, outputStream);
            outputStream.flush();
        }
    }
}
