package com.cdf.opencvweb.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DownloadUtils {

    public void downloads(String path, HttpServletRequest request, HttpServletResponse response) {
        if (path == null || path == "") return;
        List<File> fileList = new ArrayList<>();
        //根目录
        String rootPath = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext().getRealPath("")+"upload\\";
        //文件地址数组
        String[] paths = path.split(";");
        for (String p : paths) {
            String s = rootPath + p;
            File f = new File(s);
            if(!f.exists())continue;
            fileList.add(f);
        }
        if(fileList.size()<=0)return;
        try {
            //保存的文件名为 当前日期 +随机数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String date = sdf.format(new Date());
            String ran = 2+(int)(Math. random()*(102-2))+"";
            //判断文件夹是否存在
            File f = new File(rootPath);
            if (!f.exists()) f.mkdirs();
            //保存文件
            File file = new File(rootPath +date + ran+".rar");
            if (!file.exists()){
                file.createNewFile();
            }
            response.reset();
            //创建文件输出流
            FileOutputStream fous = new FileOutputStream(file);
            /**打包的方法我们会用到ZipOutputStream这样一个输出流, 所以这里我们把输出流转换一下*/
            ZipOutputStream zipOut= new ZipOutputStream(fous);
            zipFiles(fileList, zipOut);
            zipOut.close();
            fous.close();

            download(file, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 把接受的全部文件打成压缩包
     * @param org.apache.tools.zip.ZipOutputStream
     */
    public static void zipFiles(List<File> files,ZipOutputStream outputStream) {
        for(int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            zipFile(file, outputStream);
        }
    }
    /**
     * 根据输入的文件与输出流对文件进行打包
     * @param org.apache.tools.zip.ZipOutputStream
     */
    public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    try (FileInputStream fis = new FileInputStream(inputFile);
                         BufferedInputStream bis = new BufferedInputStream(fis, 1024);) {
                        // org.apache.tools.zip.ZipEntry
                        ZipEntry entry = new ZipEntry(inputFile.getName());
                        ouputStream.putNextEntry(entry);
                        // 向压缩文件中输出数据
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = bis.read(buffer)) != -1) {
                            ouputStream.write(buffer, 0, len);
                        }
                    }
                } else {
                    // 如果是目录则递归目录中的文件
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download(File f, HttpServletResponse response) {
        if (!f.exists()) return;
        try {
            String filename=f.getName();
            // 当文件名不是英文名的时候，最好使用url解码器去编码一下，
            filename = URLEncoder.encode(filename, "UTF-8");
            // 将响应的类型设置为图片
            //response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            // 现在通过IO流来传送数据
            InputStream is = new FileInputStream(f);
            // getServletContext().getResourceAsStream("/testImage.jpg");
            OutputStream os = response.getOutputStream();
            byte[] buff = new byte[1024 * 10];
            int len;
            while ((len = is.read(buff)) > -1) {
                os.write(buff, 0, len);
            }
            is.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
