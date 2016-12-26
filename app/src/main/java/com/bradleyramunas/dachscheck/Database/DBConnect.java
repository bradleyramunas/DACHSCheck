package com.bradleyramunas.dachscheck.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.bradleyramunas.dachscheck.Types.Period;
import com.bradleyramunas.dachscheck.Types.Teacher;

import java.util.ArrayList;

/**
 * Created by Bradley on 12/17/2016.
 */

public class DBConnect extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "TEACHERS.db";
    private static final String COLUMN_TEACHER_NAME = "teacherName";
    private static final String COLUMN_COURSE_DESCRIPTION = "courseDescription";
    private static final String COLUMN_COURSE_URL = "courseURL";
    private static final String COLUMN_PERIOD_NAME = "periodName";
    private static final String COLUMN_PERIOD_URL = "periodURL";


    public DBConnect(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS 'TEACHER'");
        db.execSQL("DROP TABLE IF EXISTS 'PERIODS'");
        db.close();
        onFirstRun();
    }

    public void onFirstRun(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS 'TEACHER' (teacherName TEXT, courseDescription TEXT, courseURL TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS 'PERIODS' (periodName TEXT, periodURL TEXT)";
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

    public void addTeachers(ArrayList<Teacher> teachers){
        SQLiteDatabase db = getWritableDatabase();
        for(Teacher teacher : teachers){
            String sql = "INSERT INTO 'TEACHER' (teacherName, courseDescription, courseURL) VALUES (" + teacher.getName() + ", " + teacher.getCourseDescription()
                    + ", " + teacher.getUrl() + ")";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.executeInsert();
        }
        db.close();
    }

    public void deleteTeacher(Teacher teacher){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM 'TEACHER' WHERE " + COLUMN_TEACHER_NAME + "='" + teacher.getName() + "' AND " + COLUMN_COURSE_URL + "='" + teacher.getUrl() +
                "'";
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<Teacher> getTeachers(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM 'TEACHER' WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String teacherName = c.getString(c.getColumnIndex(COLUMN_TEACHER_NAME));
            String courseDesc = c.getString(c.getColumnIndex(COLUMN_COURSE_DESCRIPTION));
            String courseURL = c.getString(c.getColumnIndex(COLUMN_COURSE_URL));
            teachers.add(new Teacher(teacherName, courseDesc, courseURL));
            c.moveToNext();
        }
        c.close();
        db.close();
        return teachers;
    }

    public void addPeriod(Period p){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO 'PERIODS' (periodName, periodURL) VALUES (" + p.getName() + ", " + p.getUrl() + ")";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.executeInsert();
        db.close();
    }

    public void deletePeriod(Period p){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM 'PERIODS' WHERE " + COLUMN_PERIOD_URL + "='" + p.getUrl() + "' AND " + COLUMN_PERIOD_NAME + "='" + p.getName() + "'";
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<Period> getPeriods(){
        ArrayList<Period> periods = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM 'PERIODS' WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String periodName = c.getString(c.getColumnIndex(COLUMN_PERIOD_NAME));
            String periodURL = c.getString(c.getColumnIndex(COLUMN_PERIOD_URL));
            periods.add(new Period(periodName, periodURL));
            c.moveToNext();
        }
        return periods;
    }
}
