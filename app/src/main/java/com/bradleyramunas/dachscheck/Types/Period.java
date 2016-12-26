package com.bradleyramunas.dachscheck.Types;

import java.net.URL;

/**
 * Created by Bradley on 12/10/2016.
 */

public class Period {
    private String name;
    private String url;

    public Period(String name, String url) {

        this.name = name;
        this.url = "http://www.doralacademyprep.org" + url;
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

    @Override
    public String toString() {
        return "Period{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
