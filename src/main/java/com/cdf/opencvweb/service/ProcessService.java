package com.cdf.opencvweb.service;

import com.cdf.opencvweb.controller.UploadController;
import com.cdf.opencvweb.opencv.OpencvProcess;
import com.cdf.opencvweb.utils.DownloadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessService.class);

    /**
     * 函数列表：
     * NormalizedBoxFilter 均值滤波
     * medianBlurFilter 中值滤波
     * GaussianFilter 高斯滤波
     * @param img 文件名，需要带上路径前缀
     * @param method
     * @param param
     * @return
     */
    public boolean process(String img, String method, double param) {
        boolean success = false;
        try {
            String classPath = ResourceUtils.getURL("classpath:").getPath();
            // 创建输出文件夹
            String outFilePath = classPath + "static/out/";
            File fp = new File(outFilePath);
            if (!fp.exists())
                fp.mkdir();

//            Method[] methods = OpencvProcess.class.getDeclaredMethods();
//            for (Method m : methods)
//                if (m.getName() == method)
//                    m.invoke(null, imgPath, param);
            /* 利用反射按函数名调用方法 */
            long l = System.currentTimeMillis();
            Method mtd = OpencvProcess.class.getMethod(method, String.class, double.class);
            success = (boolean)mtd.invoke(null, img, param);
            long processTime = System.currentTimeMillis() - l;
            LOGGER.info("处理完成 " + method + " : " + processTime);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return success;
    }

    /**
     * 下载压缩包业务
     * @param imgsList
     * @return
     */
    public boolean download(List<String> imgsList, HttpServletResponse response) {
        if (imgsList == null) return false;

        try {
            String classPath = ResourceUtils.getURL("classpath:").getPath().substring(1).concat("static");

            // 创建输出文件夹
            String zipPath = classPath + "/zip/";
            File fp = new File(zipPath);
            if (!fp.exists())
                fp.mkdir();

            List<File> fileList = new ArrayList<>();
            for (String p : imgsList) {
                String s = classPath + p;
                File f = new File(s);
                if(!f.exists()) continue;
                fileList.add(f);
            }
            if(fileList.size() <= 0) return false;

            //保存的文件名为 当前日期 + 随机数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = sdf.format(new Date());
            String rand = 2+(int)(Math. random()*(102-2))+"";
            //创建压缩文件
            File zipFile = new File(zipPath + date + rand +".rar");
            if (!zipFile.exists()){
                zipFile.createNewFile();
            }

            DownloadUtils.downloads(fileList, zipFile, response);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return true;
    }
}
