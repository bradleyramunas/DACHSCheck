package com.bradleyramunas.dachscheck.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bradleyramunas.dachscheck.R;
import com.bradleyramunas.dachscheck.Types.Period;

import java.util.ArrayList;

/**
 * Created by Bradley on 1/3/2017.
 */

public class PeriodAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Period> periods;
    private LayoutInflater layoutInflater;

    public PeriodAdapter(Context context, ArrayList<Period> periods) {
        this.context = context;
        this.periods = periods;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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
        View cardView = layoutInflater.inflate(R.layout.teacher_select_card, viewGroup, false);
        final Period period = periods.get(i);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView teacherName = (TextView) cardView.findViewById(R.id.teacherName);
        TextView teacherDesc = (TextView) cardView.findViewById(R.id.teacherDescription);
        teacherName.setText(period.getName());
        teacherDesc.setText(period.getUrl());
        return cardView;

    }
}
