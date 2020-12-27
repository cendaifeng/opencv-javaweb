package com.cdf.opencvweb.controller;

import com.cdf.opencvweb.service.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ProcessController {

    @Autowired
    ProcessService processService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private static final ConcurrentSkipListMap<String, ArrayList<String>> processMap = new ConcurrentSkipListMap<>();

    @PutMapping("/process")
    public String process(@RequestParam("imgs") List<String> imgsList, Model model) {
        ArrayList<String> list = new ArrayList<>();
        String s = "";
        for (String img : imgsList) {
            s = img.split("/")[2];
            list.add(s);
        }
        model.addAttribute("processList", list);

        return "process";
    }

    /**
     * 在图片处理工作界面选定图片后点击“处理”按钮执行
     * 前端 Ajax 请求
     * @param imgsList 文件名列表，需要带上路径前缀
     * @return
     */
    @PostMapping("/process")
    @ResponseBody
    public String process(@RequestParam("imgs") List<String> imgsList,
                          @RequestParam("method") String method,
                          @RequestParam("param") Double param) {

        if (imgsList == null)
            return "未有照片选中";
        if (method == null || param == null)
            return "处理参数错误";

        List<String> collect = imgsList.parallelStream().map(x -> {
            return processService.process(x, method, param);
        }).collect(Collectors.toList());

        return "处理成功";
    }

    @GetMapping("/zip")
    @ResponseBody
    public String zipImg(@RequestParam("img") String img){

        return "";
    }
}
