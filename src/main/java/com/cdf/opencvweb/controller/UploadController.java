package com.cdf.opencvweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Controller
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private static final ConcurrentSkipListMap<String, ArrayList<String>> userSessionMap = new ConcurrentSkipListMap<>();

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("imgs") List<MultipartFile> files, HttpSession session) {
        if (files.isEmpty())
            return "上传失败，请选择文件";

        String id = session.getId();
        LOGGER.info(id);

        try {
            String filePath = ResourceUtils.getURL("classpath:").getPath()+"/users/";
            File fp = new File(filePath);
            if(!fp.exists())
                fp.mkdir();

            int size = 0;
            for (MultipartFile f : files) {
                String uuid = UUID.randomUUID().toString().replace("-", "").substring(0,8);
                String fileName = f.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                fileName = uuid + suffix;
                File dest = new File(filePath, fileName);
                f.transferTo(dest);
                LOGGER.info("==upload success==");

                if (!userSessionMap.containsKey(id))
                    userSessionMap.put(id, new ArrayList<>()).add(fileName);
                else {
                    ArrayList<String> list = userSessionMap.get(id);
                    list.add(fileName);
                    size = list.size();
                }
            }
            return "上传成功,已上传"+size+"个文件";
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }

    @GetMapping
    @ResponseBody
    public String cleanUserMap(HttpSession session) {
        String id = session.getId();
        if (userSessionMap.containsKey(id))
            userSessionMap.remove(id);
        return "已清除用户图片映射";
    }

    @GetMapping("/process")
    public String processImgs(HttpSession session, Model model) {
        String id = session.getId();
        ArrayList<String> list = userSessionMap.get(id);
        if (list == null)
            return "process";
//
//        try {
//            String filePath = ResourceUtils.getURL("classpath:").getPath()+"/users/";
//            File fp = new File(filePath);
//            if(!fp.exists())
//                fp.mkdir();
//
//            for (String img : list) {
//                File file = new File(filePath, img);
//                model.addAttribute(file);
//            }
//
//        } catch (FileNotFoundException e) {
//            LOGGER.error(e.toString(), e);
//        }
        return "process";
    }

}