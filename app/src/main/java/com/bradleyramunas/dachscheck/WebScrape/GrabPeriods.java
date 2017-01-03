package com.bradleyramunas.dachscheck.WebScrape;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bradleyramunas.dachscheck.Adapters.PeriodAdapter;
import com.bradleyramunas.dachscheck.PeriodSelectActivity;
import com.bradleyramunas.dachscheck.R;
import com.bradleyramunas.dachscheck.Types.Period;
import com.bradleyramunas.dachscheck.Types.Teacher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bradley on 12/10/2016.
 */

public class GrabPeriods extends AsyncTask<Teacher, Integer, ArrayList<Period>>{

    RelativeLayout relativeLayout;
    Context context;
    ListView listView;
    ProgressBar progressBar;

    public GrabPeriods(Context context, RelativeLayout relativeLayout){
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.listView = (ListView) relativeLayout.findViewById(R.id.teacher_list_view);
        this.progressBar = (ProgressBar) relativeLayout.findViewById(R.id.progressBar);
    }

    @Override
    protected void onPreExecute() {

        listView.setAdapter(null);
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Period> doInBackground(Teacher... teachers) {
        ArrayList<Period> periods = new ArrayList<>();
        try{
            Document document = Jsoup.connect("http://www.doralacademyprep.org" + teachers[0].getUrl())
                    .userAgent("Chrome")
                    .get();
            Elements elements = document.select("#pageContentWrapper").select("a");
            for(Element e : elements){
                String url = e.attr("href");
                String name = e.html();
                periods.add(new Period(name, url));
            }

        }catch (Exception e){
            Log.e("JSOUP", "Error retrieving teacher's periods. Check if site is down.");
            for(StackTraceElement x : e.getStackTrace()){
                Log.e("STACKTRACE", x.toString());
            }
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

        progressBar.setVisibility(View.GONE);
        PeriodAdapter periodAdapter = new PeriodAdapter(context, periods);
        listView.setAdapter(periodAdapter);



        super.onPostExecute(periods);

    }
}
