package com.cdf.opencvweb.controller;

import com.cdf.opencvweb.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProcessController {

    @Autowired
    ProcessService processService;

    @GetMapping("/process")
    public String process() {
        return "process";
    }

    /**
     * 在预览界面选定图片后点击“处理”按钮执行
     * 前端 Ajax 请求如果收到成功信息则跳转页面到 pResult
     * @param pathList
     * @param method
     * @param model
     * @return
     */
    @PostMapping("/process")
    @ResponseBody
    public String process(@RequestParam("method") String method,
                          ModelMap modelMap) {
        modelMap.getAttribute("pathList")
        if (pathList == null) {
            return "未有照片选中";
        }
        ArrayList<String> processList = processService.process(pathList, method);
        if (processList == null) {
            return "处理出错";
        }
        modelMap.remove("pathList")
        modelMap.Attribute("pathList", processList);
        return "成功";
    }

    @GetMapping("/pResult")
    public String processImgs(@RequestParam("pathList") List<String> pathList, Model model) {
        if (pathList == null)
            return "preview";

        String filePath = "";
        for (String img : pathList) {
            filePath = "user/"+img;
            pathList.add(filePath);
        }
        model.addAttribute("pathList",pathList);
        return "preview";
    }
}
