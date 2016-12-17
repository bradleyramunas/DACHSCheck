package com.bradleyramunas.dachscheck.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.bradleyramunas.dachscheck.Types.Teacher;

/**
 * Created by Bradley on 12/17/2016.
 */

public class DBConnect extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TEACHERS.db";
    private static final String COLUMN_TEACHER_NAME = "teacherName";
    private static final String COLUMN_COURSE_DESCRIPTION = "courseDescription";
    private static final String COLUMN_COURSE_URL = "courseURL";

    public DBConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Implement later?
        //TODO: Implement onUpgrade
    }

    public void onFirstRun(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS 'TEACHER' (teacherName TEXT, courseDescription TEXT, courseURL TEXT)";
        db.execSQL(sql);
        db.close();
    }

    public void addTeacher(Teacher teacher){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO 'TEACHER' (teacherName, courseDescription, courseURL) VALUES (" + teacher.getName() + ", " + teacher.getCourseDescription()
                + ", " + teacher.getUrl() + ")";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.executeInsert();
        db.close();
    }
}
