package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String filename = "C:\\Users\\17574\\Desktop\\test1.xlsx";
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
//        实现excel的读操作
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    public static List<DemoData> getData(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("lucy"+i);
            list.add(demoData);
        }
        return list;
    }
}
