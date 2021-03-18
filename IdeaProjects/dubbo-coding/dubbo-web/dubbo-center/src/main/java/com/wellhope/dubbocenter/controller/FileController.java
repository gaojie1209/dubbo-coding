package com.wellhope.dubbocenter.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wellhope.dubbocenter.pojo.WangEditorResultBean;
import com.wellhope.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 处理文件相关操作
 * @author GaoJ
 * @create 2021-03-07 0:03
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Value("${image.server}")
    private String imageServer;

    @Autowired
    private FastFileStorageClient client;

    /**
     * 添加商品图片
     * @param file
     * @return
     */
    @PostMapping("upload")
    public ResultBean<String> upload(MultipartFile file){
        //1获取文件的后缀 **。**
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //2使用client上传图片到fastdfs
        try {
            StorePath storePath = client.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), extName, null);
            StringBuilder stringBuilder = new StringBuilder(imageServer).append(storePath.getFullPath());
            return new ResultBean<>("200",stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //TODO 把日志框架整合进来
            return new ResultBean<>("500","当前服务器繁忙，请稍后再试");
        }

    }

    /**
     * 富文本编辑器文件上传
     * @param files
     * @return
     */
    @PostMapping("batchUpload")
    public WangEditorResultBean batchUpload(MultipartFile[] files){
        String[] data = new String[files.length];
        try {
            for (int i=0;i<files.length;i++) {
                String originalFilename = files[i].getOriginalFilename();
                String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                StorePath storePath = client.uploadImageAndCrtThumbImage(files[i].getInputStream(), files[i].getSize(), extName, null);
                StringBuilder stringBuilder = new StringBuilder(imageServer).append(storePath.getFullPath());
                data[i] = stringBuilder.toString();
            }
            return new WangEditorResultBean("0",data);
        }catch (IOException e) {
            e.printStackTrace();
            //TODO 把日志框架整合进来
            return new WangEditorResultBean("1",null);
        }

    }
}
