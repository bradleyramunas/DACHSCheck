package com.bradleyramunas.dachscheck.WebScrape;

import android.util.Log;

import com.bradleyramunas.dachscheck.Types.Period;
import com.bradleyramunas.dachscheck.Types.Teacher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Bradley on 12/10/2016.
 */

//This class is basically only for use when threading/asynch tasks are not needed. Otherwise... DO NOT USE

public class WebScraper {
    public static ArrayList<Teacher> grabAllTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try{
            Document document = Jsoup.connect("http://www.doralacademyprep.org/apps/staff/").get();
            Element teacherDiv = document.select(".staff-category").get(1);
            Elements teacherData = teacherDiv.select("li");
            for(Element e : teacherData){
                String url = e.select("a").attr("href");
                String teacherName = e.select("dt").html();
                String courseDescription = e.select("dd").html();
                teachers.add(new Teacher(teacherName, courseDescription, url));
            }
        }catch (Exception e){
            Log.e("JSOUP", "Error retrieving teacher list. Check if website is down.");
            Log.e("JSOUP", e.getMessage());
        }
        return teachers;

    }

    public static ArrayList<Period> getPeriods(Teacher teacher){
        ArrayList<Period> periods = new ArrayList<>();
        try{
            Document document = Jsoup.connect(teacher.getUrl()).get();
            Elements elements = document.select("#pageContentWrapper").select("a");
            for(Element e : elements){
                String url = e.attr("href");
                String name = e.html();
                periods.add(new Period(name, url));
            }

        }catch (Exception e){
            Log.e("JSOUP", "Error retrieving teacher's periods. Check if site is down.");
            Log.e("JSOUP", e.getMessage());
        }
        return periods;
    }
}
