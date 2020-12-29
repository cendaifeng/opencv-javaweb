package com.cdf.opencvweb.utils;

import org.apache.tools.zip.ZipEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import org.apache.tools.zip.ZipOutputStream;

public class DownloadUtils {

    public DownloadUtils() {}

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadUtils.class);

    public static void downloads(List<File> fileList, File zipFile, HttpServletResponse response) {
        try {
            long l = System.currentTimeMillis();
            response.reset();
            //创建文件输出流
            FileOutputStream fops = new FileOutputStream(zipFile);
            /** 打包的方法我们会用到ZipOutputStream这样一个输出流, 所以这里我们把输出流转换一下 */
            ZipOutputStream zipOut= new ZipOutputStream(fops);
            zipFiles(fileList, zipOut);
            zipOut.close();
            fops.close();
            long processTime = System.currentTimeMillis() - l;
            LOGGER.info("压缩完成 : " + processTime);

            download(zipFile, response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void download(File f, HttpServletResponse response) {
        if (!f.exists()) return;
        try {
            String filename=f.getName();
            // 当文件名不是英文名的时候，最好使用url解码器去编码一下，
            filename = URLEncoder.encode(filename, "UTF-8");
            // 将响应的类型设置为图片
            //response.setContentType("application/octet-stream");
            response.setContentType("multipart/form-data");
            //response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            // 现在通过IO流来传送数据
            InputStream is = new FileInputStream(f);
            OutputStream os = response.getOutputStream();
            byte[] buff = new byte[1024 * 10];
            int len;
            while ((len = is.read(buff)) > -1) {
                os.write(buff, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    /**
     * 把接受的全部文件打成压缩包
     * @param org.apache.tools.zip.ZipOutputStream zops
     */
    public static void zipFiles(List<File> files,ZipOutputStream zops) {
        for(int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            zipFile(file, zops);
        }
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     * @param org.apache.tools.zip.ZipOutputStream zops
     */
    public static void zipFile(File inputFile, ZipOutputStream zops) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    try (FileInputStream fis = new FileInputStream(inputFile);
                         BufferedInputStream bis = new BufferedInputStream(fis, 1024);) {
                        // org.apache.tools.zip.ZipEntry
                        ZipEntry entry = new ZipEntry(inputFile.getName());
                        zops.putNextEntry(entry);
                        // 向压缩文件中输出数据
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = bis.read(buffer)) != -1) {
                            zops.write(buffer, 0, len);
                        }
                    }
                } else {
                    // 如果是目录则递归目录中的文件
                    File[] files = inputFile.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        zipFile(files[i], zops);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
