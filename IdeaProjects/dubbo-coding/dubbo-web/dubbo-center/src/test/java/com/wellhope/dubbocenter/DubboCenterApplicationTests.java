package com.wellhope.dubbocenter;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class DubboCenterApplicationTests {

    @Autowired
    private FastFileStorageClient client;

    /**
     * 测试文件上传
     * @throws FileNotFoundException
     */
    @Test
    public void uploadTest() throws FileNotFoundException {
        File file = new File("E:\\GaoJ\\projects\\IdeaProjects\\dubbo-coding\\dubbo-web\\dubbo-center\\cloud.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        StorePath storePath = client.uploadImageAndCrtThumbImage(fileInputStream, file.length(), "png", null);
        System.out.println(storePath.getFullPath());//ip+/+此路径可获得图片
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getPath());
    }

    /**
     * 测试文件删除
     */
    @Test
    public void deleteTest(){
//        client.deleteFile("group1/M00/00/00/wKjZgGBDfl-AQCCfAAAAEXnv-FY95.html");
        client.deleteFile("group1/M00/00/00/wKjZgGBDnH6ABKNnABu6e_Z_ygM130.png");
        System.out.println("ok");
    }
}
