package com.bradleyramunas.dachscheck.Tasks;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bradleyramunas.dachscheck.MainActivity;
import com.bradleyramunas.dachscheck.R;
import com.bradleyramunas.dachscheck.Types.Period;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by Bradley on 1/4/2017.
 */

public class CheckPeriods extends AsyncTask<Period, Void, Boolean> {

    private Context context;

    public CheckPeriods(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Period... periods) {
        for(Period p : periods){
            Log.e("TEST", p.getName() + "");
            String workingURL = "http://www.doralacademyprep.org"+ p.getPeriodURL();
            try{
                Document document = Jsoup.connect(workingURL).userAgent("Chrome").get();
                Elements elements = document.select(".tr");
                if(elements.size() != 1){
                    return true;
                }

            } catch (Exception e){
                this.cancel(true);
            }
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean hasHomework) {
        if(hasHomework){
            Intent startActivity = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startActivity, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_homework_icon)
                    .setContentTitle("Homework Reminder")
                    .setContentText("One or more of your classes has assigned homework due this week!")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            int notificationID = 1337;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID, notificationBuilder.build());
        }
        super.onPostExecute(hasHomework);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
