package com.bradleyramunas.dachscheck.Types;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bradley on 12/10/2016.
 */

public class Teacher implements Parcelable {
    private String name;
    private String courseDescription;
    private ArrayList<Period> periods;
    private String url;

    @Override
    public String toString() {
        return getName() + "\n" + getCourseDescription();
    }

    public Teacher(String name, String courseDescription, String url) {

        this.name = name;
        this.courseDescription = courseDescription;
        this.periods = new ArrayList<>();
        this.url = url;
    }

    public void addPeriod(Period p){
        periods.add(p);
    }

    public String getName() {
        return name.replace("'", "").replace("&amp;", "&");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseDescription() {
        return courseDescription.replace("'", "").replace("&amp;", "&");
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



    protected Teacher(Parcel in) {
        name = in.readString();
        courseDescription = in.readString();
        if (in.readByte() == 0x01) {
            periods = new ArrayList<Period>();
            in.readList(periods, Period.class.getClassLoader());
        } else {
            periods = null;
        }
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(courseDescription);
        if (periods == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(periods);
        }
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Teacher> CREATOR = new Parcelable.Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
}