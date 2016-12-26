package com.bradleyramunas.dachscheck.Database;

import android.content.Context;

import com.bradleyramunas.dachscheck.Types.Teacher;
import com.bradleyramunas.dachscheck.WebScrape.GrabTeachers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Bradley on 12/25/2016.
 */

public class DBHelper {
    public static void updateTeachers(Context context){
        try {
            ArrayList<Teacher> teachers = new GrabTeachers(context).execute().get();
            DBConnect db = new DBConnect(context);
            db.addTeachers(teachers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
