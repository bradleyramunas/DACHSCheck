package com.bradleyramunas.dachscheck;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.Adapters.TeacherAdapter;
import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Database.DBHelper;
import com.bradleyramunas.dachscheck.Types.Teacher;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;

public class TeacherSelectActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Teacher> teachers;
    boolean isInSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select);
        listView = (ListView) findViewById(R.id.teacher_list_view);
        populateList();
    }

    public void populateList(){

        DBConnect db = new DBConnect(this);
        teachers = db.getTeachers();
        TeacherAdapter teacherAdapter = new TeacherAdapter(this, teachers);
        listView.setAdapter(teacherAdapter);

    }

    public void searchList(String search){
        DBConnect db = new DBConnect(this);
        ArrayList<Teacher> teachers = db.getTeachers(search);
        if(!teachers.isEmpty()){
            TeacherAdapter teacherAdapter = new TeacherAdapter(this, teachers);
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
            TeacherAdapter teacherAdapter = new TeacherAdapter(this, teachers);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
