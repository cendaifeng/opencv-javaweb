package com.cdf.opencvweb.service;

import com.cdf.opencvweb.controller.UploadController;
import com.cdf.opencvweb.opencv.OpencvProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * 函数列表：
     * NormalizedBox 均值滤波
     * GaussianFilter 高斯滤波
     * @param img 文件名，需要带上路径前缀
     * @param method
     * @param param
     * @return
     */
    public String process(String img, String method, double param) {
        try {
            String classPath = ResourceUtils.getURL("classpath:").getPath();
            String imgPath = classPath + "static" + img;
            // 创建输出文件夹
            String outFilePath = classPath + "static/out/";
            File fp = new File(outFilePath);
            if (!fp.exists())
                fp.mkdir();

            /* 利用反射按函数名调用方法 */
//            Method[] methods = OpencvProcess.class.getDeclaredMethods();
//            for (Method m : methods)
//                if (m.getName() == method)
//                    m.invoke(null, imgPath, param);
            long l = System.currentTimeMillis();
            Method mtd = OpencvProcess.class.getMethod(method, String.class, double.class);
            mtd.invoke(null, imgPath, param);
            long processTime = System.currentTimeMillis() - l;
            LOGGER.info("处理完成 " + method + " : " + processTime);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return img;
    }
}
