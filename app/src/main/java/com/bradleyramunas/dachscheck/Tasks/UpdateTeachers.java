package com.bradleyramunas.dachscheck.Tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.TeacherSelectActivity;
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

public class UpdateTeachers extends AsyncTask<Void, Void, ArrayList<Teacher>>{

    private Context context;
    private DBConnect dbConnect;
    private WeakReference<Activity> weakReference;

    @Override
    protected void onPreExecute() {
        TeacherSelectActivity weakReferencedActivity = (TeacherSelectActivity) weakReference.get();
        weakReferencedActivity.startProgressBar();

        super.onPreExecute();
    }

    public UpdateTeachers(Context context, Activity activity) {
        this.context = context;
        weakReference = new WeakReference<Activity>(activity);

    }

    @Override
    protected void onCancelled() {
        TeacherSelectActivity weakReferencedActivity = (TeacherSelectActivity) weakReference.get();
        weakReferencedActivity.endProgressBar();
        weakReferencedActivity.populateList(weakReferencedActivity.teachers);
        Toast.makeText(context, "Could not connect to Doral Academy\nCheck your internet connection.", Toast.LENGTH_LONG).show();
        super.onCancelled();
    }

    @Override
    protected ArrayList<Teacher> doInBackground(Void... voids) {
        try{
            dbConnect = new DBConnect(context);
        } catch (Exception e){
            Toast.makeText(context, "Cannot access database at the moment.\nTry again later.", Toast.LENGTH_LONG).show();
            this.cancel(true);
            return null;
        }
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
            Log.e("JSOUP", ""+e.getMessage());
            this.cancel(true);
            return null;
        }
        return teachers;
    }

    @Override
    protected void onPostExecute(ArrayList<Teacher> teachers) {
        dbConnect.refreshTable(teachers);
        TeacherSelectActivity weakReferencedActivity = (TeacherSelectActivity) weakReference.get();
        weakReferencedActivity.endProgressBar();
        weakReferencedActivity.populateList(teachers);
        Toast.makeText(context, "Teacher list updated.", Toast.LENGTH_LONG).show();
        super.onPostExecute(teachers);
    }

}
