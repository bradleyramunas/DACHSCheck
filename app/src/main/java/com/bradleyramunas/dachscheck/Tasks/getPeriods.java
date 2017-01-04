package com.bradleyramunas.dachscheck.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.TeacherSelectActivity;
import com.bradleyramunas.dachscheck.Types.Period;
import com.bradleyramunas.dachscheck.Types.Teacher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 1/3/2017.
 */

public class GetPeriods extends AsyncTask<Teacher, Integer, ArrayList<Period>>{

    private WeakReference<Activity> weakReference;
    public GetPeriods(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected void onCancelled() {
        TeacherSelectActivity weakReferencedActivity = (TeacherSelectActivity) weakReference.get();
        weakReferencedActivity.endProgressBar();
        weakReferencedActivity.populateList(weakReferencedActivity.teachers);
        Toast.makeText(weakReferencedActivity, "Could not connect to Doral Academy\nCheck your internet connection.", Toast.LENGTH_LONG).show();
        weakReferencedActivity.isInPeriod = false;
        super.onCancelled();
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
                if(url.contains("classes")){
                    periods.add(new Period(name, url));
                }
            }

        }catch (Exception e){
            Log.e("JSOUP", "Error retrieving teacher's periods. Check if site is down.");
            for(StackTraceElement x : e.getStackTrace()){
                Log.e("STACKTRACE", x.toString());
            }
            Log.e("JSOUP", ""+e.getMessage());
            this.cancel(true);
            return null;
        }
        return periods;

    }

    @Override
    protected void onPreExecute() {
        TeacherSelectActivity weakReferencedActivity = (TeacherSelectActivity) weakReference.get();
        weakReferencedActivity.startProgressBar();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Period> periods) {
        TeacherSelectActivity weakReferencedActivity = (TeacherSelectActivity) weakReference.get();
        weakReferencedActivity.endProgressBar(periods);
        super.onPostExecute(periods);
    }
}
