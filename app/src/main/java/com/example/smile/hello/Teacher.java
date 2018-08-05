package com.example.smile.hello;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Teacher {
    public static List<String> getAllTeachers() {
        List<String> teachers = new ArrayList<String>();
//        teachers.add("张海霞...");
//        teachers.add("陈江");
//        teachers.add("叶蔚");

        return teachers;

    }

    public static List<String> getResp(String resp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        String today = sdf.format(new Date());
        List<String> teachers = new ArrayList<String>();
        if(resp!=null&&resp.indexOf(today)>-1){
            result = resp.substring(resp.indexOf(today),resp.indexOf(today)+19);
            result += "\n"+sdf2.format(new Date());
        }else{
//            result = "查询无结果！";
            result =resp;
        }
        teachers.add(result);
        return teachers;
    }

    public static String getResult(String resp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        String today = sdf.format(new Date());
        if(resp!=null&&resp.indexOf(today)>-1){
            result = resp.substring(resp.indexOf(today),resp.indexOf(today)+19);
            //result += "\n"+sdf2.format(new Date());
        }else{
            result = today;
        }
        return result;
    }

}
