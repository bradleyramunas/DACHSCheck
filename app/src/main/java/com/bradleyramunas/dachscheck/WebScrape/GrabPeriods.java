package com.bradleyramunas.dachscheck.WebScrape;

import android.os.AsyncTask;
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

public class GrabPeriods extends AsyncTask<Teacher, Integer, ArrayList<Period>>{
    @Override
    protected ArrayList<Period> doInBackground(Teacher... teachers) {
        ArrayList<Period> periods = new ArrayList<>();
        try{
            Document document = Jsoup.connect(teachers[0].getUrl()).get();
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

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Period> periods) {
        super.onPostExecute(periods);
    }
}
