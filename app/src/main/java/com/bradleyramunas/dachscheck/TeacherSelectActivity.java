package com.bradleyramunas.dachscheck;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.Adapters.TeacherAdapter;
import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Database.DBHelper;
import com.bradleyramunas.dachscheck.Types.Teacher;
import com.bradleyramunas.dachscheck.WebScrape.GrabPeriods;

import java.util.ArrayList;


public class TeacherSelectActivity extends AppCompatActivity {

    public ListView listView;
    public ArrayList<Teacher> teachers;
    public boolean isInSearch = false;
    public RelativeLayout relativeLayout;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select a teacher");
        context = this;
        listView = (ListView) findViewById(R.id.teacher_list_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_teacher_select);
        populateList();
    }

    public void populateList(){
        DBConnect db = new DBConnect(this);
        teachers = db.getTeachers();
        TeacherAdapter teacherAdapter = new TeacherAdapter(this, teachers, relativeLayout);
        listView.setAdapter(teacherAdapter);

    }

    public void searchList(String search){
        DBConnect db = new DBConnect(this);
        ArrayList<Teacher> teachers = db.getTeachers(search);
        if(!teachers.isEmpty()){
            TeacherAdapter teacherAdapter = new TeacherAdapter(this, teachers, relativeLayout);
            listView.setAdapter(teacherAdapter);
            isInSearch = true;
        }else{
            Toast.makeText(this, "No Results Found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(isInSearch){
            isInSearch = false;
            TeacherAdapter teacherAdapter = new TeacherAdapter(this, teachers, relativeLayout);
            listView.setAdapter(teacherAdapter);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

        if(id == R.id.refresh_list){
            DBHelper.updateTeachers(this);
            populateList();
        }
        if(id == R.id.search_list){
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setMessage("Enter search term");
            build.setCancelable(true);
            final EditText searchBox = new EditText(this);
            build.setView(searchBox);
            build.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String search = searchBox.getText().toString();
                    searchList(search);
                }
            });
            build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            build.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
