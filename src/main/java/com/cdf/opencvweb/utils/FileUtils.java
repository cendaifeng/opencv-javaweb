package com.cdf.opencvweb.utils;

import java.util.UUID;

public class FileUtils {

    /**
     * 获取文件后缀
     * @param fileName
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     */
    public static String getFileName(String outputPath, String fileOriginName, String type){
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0,2);
        return outputPath
                .concat(fileOriginName.split("\\.")[0])
                .concat(type)
                .concat(uuid)
                .concat(".")
                .concat(fileOriginName.split("\\.")[1]);
    }
}
