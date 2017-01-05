package com.bradleyramunas.dachscheck.Types;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Bradley on 12/10/2016.
 */

public class Period implements Parcelable {
    private String name;
    private String url;

    public Period(String name, String url) {

        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name.replace("'", "").replace("&amp;", "&");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPeriodURL(){
        return url.replace("_class", "_assignment");
    }

    @Override
    public String toString() {
        return "Period{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    protected Period(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Period> CREATOR = new Parcelable.Creator<Period>() {
        @Override
        public Period createFromParcel(Parcel in) {
            return new Period(in);
        }

        @Override
        public Period[] newArray(int size) {
            return new Period[size];
        }
    };
}