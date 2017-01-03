package com.bradleyramunas.dachscheck.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bradleyramunas.dachscheck.PeriodSelectActivity;
import com.bradleyramunas.dachscheck.R;
import com.bradleyramunas.dachscheck.Types.Teacher;
import com.bradleyramunas.dachscheck.WebScrape.GrabPeriods;

import java.util.ArrayList;

/**
 * Created by Bradley on 12/26/2016.
 */

public class TeacherAdapter extends BaseAdapter{

    public Context context;
    public RelativeLayout relativeLayout;
    private LayoutInflater layoutInflater;
    private ArrayList<Teacher> teachers;


    public TeacherAdapter(Context context, ArrayList<Teacher> teachers, RelativeLayout relativeLayout) {
        this.context = context;
        this.teachers = teachers;
        this.relativeLayout = relativeLayout;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Object getItem(int i) {
        return teachers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View teacherView = layoutInflater.inflate(R.layout.teacher_select_card, viewGroup, false);
        final Teacher teacher = (Teacher) getItem(i);
        teacherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CAN", "YOU SEE ME");
                new GrabPeriods(context, relativeLayout).execute(teacher);
            }
        });
        TextView teacherName = (TextView) teacherView.findViewById(R.id.teacherName);
        TextView teacherDesc = (TextView) teacherView.findViewById(R.id.teacherDescription);
        teacherName.setText(teacher.getName());
        teacherDesc.setText(teacher.getCourseDescription());


        return teacherView;
    }
}
