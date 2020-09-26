package filter;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static utils.FileUtils.getFileName;

/**
 * 图像滤波类 Demo
 * 调用 opencv 接口函数: blur, GaussianBlur, medianBlur, bilateralFilter
 */
public class Filter2d {
    /* 默认输入 */
    private static String filePath = "./storage/";
    private static String outputPath = "./storage/output/";
    private String fileName = "img-02.jpg";
    private double kernelSize = 5;

    public Filter2d() {}

    /**
     * 归一化块滤波器 Normalized Box Filter
     */
    public void NormalizedBox(String fileName, double kernelSize) {
        if (fileName == null) {
            fileName = this.fileName;
        }
        if (kernelSize == -1) {
            kernelSize = this.kernelSize;
        }
        String uri_in = filePath.concat(fileName);
        // 输入源图像
        Mat src = Imgcodecs.imread(uri_in, Imgcodecs.IMREAD_COLOR);
        if( src.empty() ) {
            System.out.println("Error opening image");
            System.exit(-1);
        }
        // 准备一个与输入图像size和type一样的副本
        Mat dst = src.clone();
        // 卷积核大小
        Size ksize = new Size(kernelSize, kernelSize);
        // 归一化块滤波
        Imgproc.blur(src, dst, ksize, new Point(-1, -1));  // borderType ignore
        // 输入图像
        Imgcodecs.imwrite( getFileName(outputPath, fileName, "_blur_"), dst );
    }

    /**
     * 高斯滤波器 (Gaussian Filter)
     */
    public void GaussianFilter(String fileName, double kernelSize) {
        if (fileName == null) {
            fileName = this.fileName;
        }
        if (kernelSize == -1) {
            kernelSize = this.kernelSize;
        }
        String uri_in = filePath.concat(fileName);
        // 输入源图像
        Mat imread = Imgcodecs.imread(uri_in, Imgcodecs.IMREAD_COLOR);
        if( imread.empty() ) {
            System.out.println("Error opening image");
            System.exit(-1);
        }
        // 准备一个与输入图像size和type一样的副本
        Mat dst = imread.clone();
        // 卷积核大小
        Size ksize = new Size(kernelSize, kernelSize);
        // 归一化块滤波
        Imgproc.GaussianBlur(imread, dst, ksize, 0);  // default sigma = 0.3* ( (ksize-1)* 0.5 - 1 ) + 0.8
        // 输入图像
        Imgcodecs.imwrite( getFileName(outputPath, fileName, "_Gaussian_"), dst );
    }
}
