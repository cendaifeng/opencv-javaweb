package com.cdf.opencvweb.service;

import com.cdf.opencvweb.controller.UploadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    public ArrayList<String> process(List<String> pathList, String method) {

        try {
            String classPath = ResourceUtils.getURL("classpath:").getPath();
            for (String p : pathList) {

            }





            File fp = new File(filePath);
            if (!fp.exists())
                fp.mkdir();

            int size = 0;
            for (MultipartFile f : files) {
                String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
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
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
