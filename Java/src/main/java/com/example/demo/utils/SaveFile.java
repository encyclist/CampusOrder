package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 保存文件工具类
 */
@Slf4j
public class SaveFile {

    /**
     * 保存文件到磁盘
     *
     * @param file 要保存的文件
     * @return 返回文件
     */
    public static File saveFile(MultipartFile file) {
        // 上传文件名
        String filename = "";
        filename += file.getOriginalFilename();
        log.info("filename:{}", filename);

        // 文件传输保存
        final File dir = new File(filename);

        try {
            // 将内存中的文件写入磁盘
            file.transferTo(dir);
        } catch (IOException e) {
            log.error("e:{}", e);
        }
        return dir;
    }
}
