package com.bradleyramunas.dachscheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bradleyramunas.dachscheck.Adapters.TeacherAdapter;
import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Database.DBHelper;
import com.bradleyramunas.dachscheck.Types.Teacher;

import java.util.ArrayList;

import butterknife.BindView;

public class TeacherSelectActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select);
        listView = (ListView) findViewById(R.id.teacher_list_view);
        populateList();
    }

    public void populateList(){

        DBConnect db = new DBConnect(this);
        ArrayList<Teacher> teachers = db.getTeachers();
        TeacherAdapter teacherAdapter = new TeacherAdapter(this, teachers);
        listView.setAdapter(teacherAdapter);

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

        }

        return super.onOptionsItemSelected(item);
    }
}
