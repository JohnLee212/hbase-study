package com.example.hbasestudy.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.hbasestudy.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description Excel导出
 * @date 2020/7/15 10:53
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        List<User> list = new ArrayList<>();
        User obj = new User();
        obj.setName("卡卡罗特");
        obj.setAge("25");
        obj.setBirthDay("0903");
        list.add(obj);
        list.add(new User());
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("birthDay", "生日");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(2, "申请人员信息");
        writer.write(list, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String name = "test";
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        IoUtil.close(out);

    }
}
