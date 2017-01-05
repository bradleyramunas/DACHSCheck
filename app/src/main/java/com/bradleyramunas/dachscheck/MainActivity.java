package com.bradleyramunas.dachscheck;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Tasks.AlarmReceiver;
import com.bradleyramunas.dachscheck.Types.Period;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Drawer drawer = null;
    private Toolbar toolbar;
    private AccountHeader header;
    private Period currentlySelected = null;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;


    public void setRepeatingAlarm(int hour, int minute){
        alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Log.e("ALARM", ""+ hour + ":" + minute);
        Log.e("ALARM", calendar.toString());
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    public void removeAlarm(){
        if(alarmManager != null){
            alarmManager.cancel(alarmIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sp = this.getSharedPreferences(getString(R.string.app_data_key), this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        boolean firstStart = sp.getBoolean("firstRun", false);
        if(true){
            editor.putBoolean("firstRun", true);
            DBConnect db = new DBConnect(this);
            db.onFirstRun();
            editor.apply();
        }


        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(true)
                .build();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getTag() != null){
                            Period period = (Period) drawerItem.getTag();
                            currentlySelected = period;
                            MainActivity.this.registerForContextMenu(view);
                            openContextMenu(view);
                        }
                        return true;
                    }
                })
                .build();
        populateDrawer();

    }


    public void populateDrawer(){
        drawer.removeAllItems();
        drawer.removeAllStickyFooterItems();
        DBConnect db = new DBConnect(this);
        ArrayList<Period> periods = db.getPeriods();
        for(final Period p : periods){
            drawer.addItem(new PrimaryDrawerItem().withTag(p).withSelectable(false).withName(p.getName().replace("&amp;", "&")).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    //Do something with this period.
                    Toast.makeText(getApplicationContext(), p.getUrl(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            }));
        }
        drawer.addStickyFooterItem(new PrimaryDrawerItem().withTag(null).withName("Add Class").withIcon(R.drawable.ic_add_circle_outline_black_24dp).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent getNewTeacher = new Intent(getApplicationContext(), TeacherSelectActivity.class);
                startActivityForResult(getNewTeacher, 1);
                return true;
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.navdrawer_context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.deletePeriod){
            if(currentlySelected != null){
                DBConnect dbConnect = new DBConnect(this);
                dbConnect.deletePeriod(currentlySelected);
                populateDrawer();
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.set_check) {
            SharedPreferences sp = getBaseContext().getSharedPreferences(getString(R.string.app_data_key), getBaseContext().MODE_PRIVATE);
            final SharedPreferences.Editor editor = sp.edit();
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    editor.putInt("timeHour", i);
                    editor.putInt("timeMinute", i1);
                    editor.apply();
                    setRepeatingAlarm(i, i1);
                }
            }, sp.getInt("timeHour", 8), sp.getInt("timeMinute", 0), true);
            timePickerDialog.setTitle("Homework Check Time");
            timePickerDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen()){
            drawer.closeDrawer();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == 1){
                Period period = data.getParcelableExtra("selectedPeriod");
                DBConnect db = new DBConnect(this);
                db.addPeriod(period);
                populateDrawer();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
