package com.bradleyramunas.dachscheck;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bradleyramunas.dachscheck.Types.Period;
import com.bradleyramunas.dachscheck.Types.Teacher;
import com.bradleyramunas.dachscheck.WebScrape.GrabPeriods;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PeriodSelectActivity extends AppCompatActivity {

    private Teacher teacher;
    private TextView teacherName;
    private TextView teacherDescription;
    private LinearLayout contentHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_select);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select a period");
        teacher = getIntent().getParcelableExtra("teacher");

        teacherName = (TextView) findViewById(R.id.teacherNameText);
        teacherDescription = (TextView) findViewById(R.id.courseDescriptionText);

        teacherName.setText(teacher.getName());
        teacherDescription.setText(teacher.getCourseDescription());
        Log.e("TEST", "" + teacher.getUrl());

        contentHolder = (LinearLayout) findViewById(R.id.contentHolder);

        populateList();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void populateList(){
//        try {
//            ArrayList<Period> periods = new GrabPeriods(this).execute(teacher).get();
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(0,0,0,20);
//            if(periods.isEmpty()){
//                TextView message = new TextView(this);
//                message.setText("No periods found");
//                message.setLayoutParams(layoutParams);
//                contentHolder.addView(message);
//            }else{
//                for(final Period p : periods){
//                    if(p.getUrl().contains("/classes/")){
//                        TextView link = new TextView(this);
//                        link.setText(p.getName());
//                        link.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Uri address = Uri.parse("http://www.doralacademyprep.org" + p.getUrl());
//                                Intent browser = new Intent(Intent.ACTION_VIEW, address);
//                                startActivity(browser);
//                            }
//                        });
//                        link.setLayoutParams(layoutParams);
//                        contentHolder.addView(link);
//                    }
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}

