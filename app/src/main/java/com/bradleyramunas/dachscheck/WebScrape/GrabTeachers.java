package com.bradleyramunas.dachscheck.WebScrape;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.MainActivity;
import com.bradleyramunas.dachscheck.Types.Teacher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Bradley on 12/10/2016.
 */

public class GrabTeachers extends AsyncTask<Void, Integer, ArrayList<Teacher>>{

    private Context context;
    public GrabTeachers(Context context){
        super();
        this.context = context;

    }

    @Override
    protected ArrayList<Teacher> doInBackground(Void... voids) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try{
            Document document = Jsoup.connect("http://www.doralacademyprep.org/apps/staff/")
                    .userAgent("Chrome")
                    .get();
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

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(values[0] == 1){
            Toast.makeText(context, "Completed", Toast.LENGTH_LONG).show();
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Teacher> teachers) {

        super.onPostExecute(teachers);
    }
}
