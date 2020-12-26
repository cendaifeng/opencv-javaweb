package com.cdf.opencvweb.opencv;

import com.cdf.opencvweb.controller.UploadController;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;


/**
 * 图像滤波类 Demo
 * 调用 opencv 接口函数: blur, GaussianBlur, medianBlur, bilateralFilter
 */
public class OpencvProcess {

    public OpencvProcess() {}

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * 归一化块滤波器 (normalized box filter)
     */
    public static void normalizedBoxFilter(String img, double kernelSize) {
        try {
            String classPath = ResourceUtils.getURL("classpath:").getPath().substring(1);
            String filePath = classPath.concat("static").concat(img);
            String outputPath = classPath.concat("static/out/");
            Mat src = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_COLOR);
            if( src.empty() ) {
                LOGGER.warn("Error opening image");
                System.exit(-1);
            }
            Mat dst = src.clone();
            Size ksize = new Size(kernelSize, kernelSize);
            // 归一化块滤波
            Imgproc.blur(src, dst, ksize, new Point(-1, -1));  // borderType ignore
            Imgcodecs.imwrite( outputPath.concat(img.split("/")[2]), dst );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 均值滤波器 (medianBlur filter)
     */
    public static void medianBlurFilter(String img, double kernelSize) {
        try {
            String classPath = ResourceUtils.getURL("classpath:").getPath().substring(1);
            String filePath = classPath.concat("static").concat(img);
            String outputPath = classPath.concat("static/out/");
            Mat src = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_COLOR);
            if( src.empty() ) {
                LOGGER.warn("Error opening image");
                System.exit(-1);
            }
            Mat dst = src.clone();
            // 均值滤波
            Imgproc.medianBlur(src, dst, (int)kernelSize);  // 简单强转
            Imgcodecs.imwrite( outputPath.concat(img.split("/")[2]), dst );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 高斯滤波器 (Gaussian filter)
     */
    public static void GaussianFilter(String img, double kernelSize) {
        try {
            String classPath = ResourceUtils.getURL("classpath:").getPath().substring(1);
            String filePath = classPath.concat("static").concat(img);
            String outputPath = classPath.concat("static/out/");
            // 输入源图像
            Mat src = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_COLOR);
            if( src.empty() ) {
                LOGGER.warn("Error opening image");
//                System.exit(-1);
            }
            // 准备一个与输入图像size和type一样的副本
            Mat dst = src.clone();
            // 卷积核大小
            Size ksize = new Size(kernelSize, kernelSize);
            // 高斯滤波
            Imgproc.GaussianBlur(src, dst, ksize, 0);  // default sigma = 0.3* ( (ksize-1)* 0.5 - 1 ) + 0.8
            // 输入图像
            Imgcodecs.imwrite( outputPath.concat(img.split("/")[2]), dst );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
