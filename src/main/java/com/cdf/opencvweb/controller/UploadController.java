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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

@Controller
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private static final ConcurrentSkipListMap<String, ArrayList<String>> userSessionMap = new ConcurrentSkipListMap<>();

    @GetMapping("/upload")
    public String upload(HttpServletRequest request) {
        // 设置session有效时间为12小时
        request.getSession().setMaxInactiveInterval(43200);
        return "upload";
    }

    /**
     * 将被上传的文件放在 target/static/users/ 下
     * 并为每一个文件另重名为一个8位随机字符串
     * 这里将文件（路径）与用户SessionID用全局跳表关联起来 方便后续以之作为索引去找到服务器磁盘中用户所上传的图片
     *
     * @param files 上传的MultipartFile数组
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("imgs") List<MultipartFile> files, HttpSession session) {
        if (files.isEmpty())
            return "上传失败，请选择文件";

        String id = session.getId();
        LOGGER.info(id);

        try {
            String filePath = ResourceUtils.getURL("classpath:").getPath()+"static/users/";
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

                if (!userSessionMap.containsKey(id)) {
                    userSessionMap.put(id, new ArrayList<>(Arrays.asList(fileName)));
                    size = 1;
                } else {
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

    @GetMapping("/clean")
    @ResponseBody
    public String cleanUserMap(HttpSession session) {
        String id = session.getId();
        if (userSessionMap.containsKey(id))
            userSessionMap.remove(id);
        return "已清除用户图片映射";
    }

    /**
     * 在request域中放入图片路径列表pathList 用以在客户端预览展示
     * @param model
     */
    @GetMapping("/preview")
    public String processImgs(HttpSession session, Model model) {
        String id = session.getId();
        ArrayList<String> list = userSessionMap.get(id);
        if (list == null)
            return "preview";

        ArrayList<String> pathList = new ArrayList<>();
        String filePath = "";
        for (String img : list) {
            filePath = "/users/"+img;
            pathList.add(filePath);
        }
        model.addAttribute("pathList", pathList);

        return "preview";
    }

}