package com.anosh.christmasgiftmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;

import com.anosh.christmasgiftmanager.Classes.Gift_Class;
import com.anosh.christmasgiftmanager.Classes.Group_Class;
import com.anosh.christmasgiftmanager.Classes.Person_Class;
import com.anosh.christmasgiftmanager.Classes.Store_Class;
import com.anosh.christmasgiftmanager.Database.DataSource;
import com.anosh.christmasgiftmanager.Tabs.PeopleFragment;
import com.anosh.christmasgiftmanager.Tabs.ProgressFragment;
import com.anosh.christmasgiftmanager.Tabs.StoreFragment;
import com.anosh.christmasgiftmanager.Tabs.TabListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static DataSource datasource;
    public static Integer[] imageId = {
            R.drawable.images1,
            R.drawable.images2,
            R.drawable.images3,
            R.drawable.images4,
            R.drawable.images
    };
    public static ArrayList<Group_Class> group = new ArrayList<Group_Class>();
    public static ArrayList<Person_Class> person = new ArrayList<Person_Class>();
    public static ArrayList<Gift_Class> gift = new ArrayList<Gift_Class>();
    public static ArrayList<Store_Class> store = new ArrayList<Store_Class>();
    android.support.v7.app.ActionBar.Tab tab1, tab2, tab3;
    Fragment fragmentTab1 = new PeopleFragment();
    Fragment fragmentTab2 = new StoreFragment();
    Fragment fragmentTab3 = new ProgressFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DataSource(this);
        datasource.open();

        group = datasource.getAllGroups();
        person = datasource.getAllPersons();
        gift = datasource.getAllGifts();
        store = datasource.getAllStores();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
//
        tab1 = actionBar.newTab().setText("People");
        tab2 = actionBar.newTab().setText("Store");
        tab3 = actionBar.newTab().setText("Progress");
//
        tab1.setTabListener(new TabListener(fragmentTab1));
        tab2.setTabListener(new TabListener(fragmentTab2));
        tab3.setTabListener(new TabListener(fragmentTab3));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
        android.support.v7.app.ActionBar actionBar = new ActionBar();
        getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
//
        tab1 = actionBar.newTab().setText("People");
        tab2 = actionBar.newTab().setText("Store");
        tab3 = actionBar.newTab().setText("Progress");
//
        tab1.setTabListener(new TabListener(fragmentTab1));
        tab2.setTabListener(new TabListener(fragmentTab2));
        tab3.setTabListener(new TabListener(fragmentTab3));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_addPerson:
                if (group.size() != 0) {
                    Spinner s = (Spinner) findViewById(R.id.groupSpinner);
                    Intent pers = new Intent(this, AddPerson.class);
                    if (s != null) {
                        pers.putExtra("group", "" + s.getSelectedItemId());
                    }
                    startActivity(pers);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("No Groups")
                            .setMessage("You should add group First.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return true;

            case R.id.action_addGroup:
                Intent gro = new Intent(this, AddGroup.class);
                startActivity(gro);
                return true;

            case R.id.action_addStore:
                Intent sto = new Intent(this, AddStore.class);
                startActivity(sto);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
