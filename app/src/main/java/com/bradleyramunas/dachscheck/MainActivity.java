package com.bradleyramunas.dachscheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bradleyramunas.dachscheck.Database.DBConnect;
import com.bradleyramunas.dachscheck.Types.Period;
import com.bradleyramunas.dachscheck.Types.Teacher;
import com.bradleyramunas.dachscheck.WebScrape.GrabPeriods;
import com.bradleyramunas.dachscheck.WebScrape.GrabTeachers;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Drawer drawer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sp = this.getSharedPreferences(getString(R.string.app_data_key), this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        boolean firstStart = sp.getBoolean("firstRun", false);
        if(!firstStart){
            editor.putBoolean("firstRun", true);
            DBConnect db = new DBConnect(this);
            db.onFirstRun();
            editor.apply();
        }

        AccountHeader header = new AccountHeaderBuilder()
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
                .build();

    }

    public void populateDrawer(){
        DBConnect db = new DBConnect(this);
        ArrayList<Period> periods = db.getPeriods();
        for(final Period p : periods){
            drawer.addItem(new PrimaryDrawerItem().withName(p.getName().replace("&amp;", "&")).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    //Do something with this period.
                    Toast.makeText(getApplicationContext(), p.getUrl(), Toast.LENGTH_LONG).show();
                    return true;
                }
            }));
        }
        drawer.addStickyFooterItem(new PrimaryDrawerItem().withName("Add Class").withIcon(R.drawable.ic_add_circle_outline_black_24dp).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                //Do something
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
}
