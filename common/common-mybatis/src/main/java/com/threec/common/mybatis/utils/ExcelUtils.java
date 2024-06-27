package com.threec.common.mybatis.utils;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class ExcelUtils {
    public ExcelUtils() {
    }

    public static void exportExcel(HttpServletResponse response, String fileName, Collection<?> list, Class<?> pojoClass) throws IOException {
        if (StringUtils.isBlank(fileName)) {
            fileName = DateUtils.format(new Date());
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, list);
        Sheet sheet1 = workbook.getSheetAt(0);
        sheet1.setDefaultColumnWidth(12800);
        sheet1.setDefaultRowHeight((short) 512);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    public static void exportExcelToTarget(HttpServletResponse response, String fileName, Collection<?> sourceList, Class<?> targetClass) throws Exception {
        List<Object> targetList = new ArrayList(sourceList.size());
        Iterator var5 = sourceList.iterator();

        while (var5.hasNext()) {
            Object source = var5.next();
            Object target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        }

        exportExcel(response, fileName, targetList, targetClass);
    }
}
