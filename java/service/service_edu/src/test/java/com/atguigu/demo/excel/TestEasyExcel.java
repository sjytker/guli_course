package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestEasyExcel {

    public static void main(String[] args) {
        String fileName = "D:\\write.xlsx";
      //  EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }


    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }
        return list;
    }
}
