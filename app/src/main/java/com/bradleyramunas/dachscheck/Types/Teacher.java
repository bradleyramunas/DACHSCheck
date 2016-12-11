package com.bradleyramunas.dachscheck.Types;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bradley on 12/10/2016.
 */

public class Teacher {
    private String name;
    private String courseDescription;
    private ArrayList<Period> periods;
    private String url;

    public Teacher(String name, String courseDescription, String url) {

        this.name = name;
        this.courseDescription = courseDescription;
        this.periods = new ArrayList<>();
        this.url = "http://www.doralacademyprep.org" + url;
    }

    public void addPeriod(Period p){
        periods.add(p);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<Period> periods) {
        this.periods = periods;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
