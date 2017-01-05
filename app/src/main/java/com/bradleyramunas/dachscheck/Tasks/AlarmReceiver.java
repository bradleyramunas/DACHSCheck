package com.bradleyramunas.dachscheck.Tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Types.Period;

import java.util.ArrayList;

/**
 * Created by Bradley on 1/4/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("RECEIVER", "IVE BEEN RECEIVED");
        DBConnect db = new DBConnect(context);
        ArrayList<Period> periodList = db.getPeriods();
        Period[] periods = new Period[periodList.size()];
        for(int i = 0; i<periodList.size(); i++){
            periods[i] = periodList.get(i);
        }
        new CheckPeriods(context).execute(periods);
    }
}
