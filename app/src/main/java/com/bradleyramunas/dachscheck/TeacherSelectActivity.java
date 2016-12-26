package com.bradleyramunas.dachscheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Database.DBHelper;
import com.bradleyramunas.dachscheck.Types.Teacher;

import java.util.ArrayList;

import butterknife.BindView;

public class TeacherSelectActivity extends AppCompatActivity {

    @BindView(R.id.teacher_scroll_view)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_select);
        populateList();
    }

    public void populateList(){
        scrollView.removeAllViews();
        DBConnect db = new DBConnect(this);
        ArrayList<Teacher> teachers = db.getTeachers();
        for(Teacher t : teachers){
            TextView textView = new TextView(this);
            textView.setText(t.getName() + "\n" + t.getCourseDescription());
            scrollView.addView(textView);
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
            DBHelper.updateTeachers(getApplicationContext());
            populateList();
        }

        return super.onOptionsItemSelected(item);
    }
}
