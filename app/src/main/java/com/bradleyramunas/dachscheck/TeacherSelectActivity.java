package com.bradleyramunas.dachscheck;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Tasks.GetPeriods;
import com.bradleyramunas.dachscheck.Tasks.UpdateTeachers;
import com.bradleyramunas.dachscheck.Types.Period;
import com.bradleyramunas.dachscheck.Types.Teacher;

import java.util.ArrayList;

public class TeacherSelectActivity extends AppCompatActivity {

    public ListView listView;
    public ProgressBar progressBar;
    public boolean isInSearch = false;
    public boolean isInPeriod = false;
    public ArrayList<Teacher> teachers;
    public LayoutInflater layoutInflater;
    final public Activity activity = this;
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Select a teacher");
        listView = (ListView) findViewById(R.id.teacher_list_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        DBConnect db = new DBConnect(this);
        teachers = db.getTeachers();
        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        populateList(teachers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_teacher, menu);
        return true;
    }

    public void populateList(final ArrayList<Teacher> teacherList){
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return teacherList.size();
            }

            @Override
            public Object getItem(int i) {
                return teacherList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                final Teacher item = (Teacher) getItem(i);
                View cardView = layoutInflater.inflate(R.layout.teacher_select_card, viewGroup, false);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isInPeriod = true;
                        new GetPeriods(activity).execute(item);
                    }
                });
                TextView teacherName = (TextView) cardView.findViewById(R.id.teacherName);
                TextView teacherDesc = (TextView) cardView.findViewById(R.id.teacherDescription);
                teacherName.setText(item.getName());
                teacherDesc.setText(item.getCourseDescription());
                return cardView;
            }
        });
    }

    public void populateListPeriods(final ArrayList<Period> periods){
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return periods.size();
            }

            @Override
            public Object getItem(int i) {
                return periods.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                final Period item = (Period) getItem(i);
                View cardView = layoutInflater.inflate(R.layout.teacher_select_card, viewGroup, false);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.setResult(1);
                        activity.finish();
                    }
                });
                TextView teacherName = (TextView) cardView.findViewById(R.id.teacherName);
                TextView teacherDesc = (TextView) cardView.findViewById(R.id.teacherDescription);
                teacherName.setText(item.getName());
                teacherDesc.setText(item.getUrl());
                return cardView;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isInPeriod){
            isInPeriod = false;
            populateList(teachers);
            actionBar.setTitle("Select a teacher");
        }else if(isInSearch){
            isInSearch = false;
            populateList(teachers);
        }else{
            super.onBackPressed();
        }

    }

    public void searchList(final String search){
        DBConnect db = new DBConnect(this);
        final ArrayList<Teacher> searchTeachers = db.getTeachers(search);
        if(!teachers.isEmpty()){
            populateList(searchTeachers);
            isInSearch = true;
        }else{
            Toast.makeText(this, "No Results Found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

        if(id == R.id.refresh_list && !isInPeriod){
            new UpdateTeachers(this, this).execute();
        }
        if(id == R.id.search_list && !isInPeriod){
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

    public void startProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        listView.setAdapter(null);
    }

    public void endProgressBar(ArrayList<Period> periods){
        progressBar.setVisibility(View.GONE);
        actionBar.setTitle("Select a class period");
        populateListPeriods(periods);
    }

    public void endProgressBar(){
        progressBar.setVisibility(View.GONE);
    }


}
